package com.example.myesiapplication.players

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myesiapplication.RetrofitClient
import com.example.myesiapplication.data.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class PlayerDetailsScreenViewModel : ViewModel() {
    private val _playerDetail = MutableStateFlow<Player?>(null)
    val playerDetail: StateFlow<Player?> = _playerDetail
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite
    private val _errorMessages = MutableStateFlow<String?>(null)

    fun fetchPlayerDetails(playerId: Int) = viewModelScope.launch {
        try {
            val response = RetrofitClient.nbaService.getPlayersById(playerId)
            if (response.isSuccessful && response.body() != null) {
                _playerDetail.value = response.body()
                setupFavoriteListener(playerId)
            } else {
                _errorMessages.value = "Failed to load player details: ${response.errorBody()?.string()}"
            }
        } catch (e: Exception) {
            _errorMessages.value = "Exception in fetchPlayerDetails: ${e.message}"
        }
    }

    private fun setupFavoriteListener(playerId: Int) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val favDocId = "${it.uid}_$playerId"
            FirebaseFirestore.getInstance().collection("favorites").document(favDocId)
                .addSnapshotListener { documentSnapshot, e ->
                    e?.let {
                        _errorMessages.value = "Error listening to favorite changes: ${it.message}"
                        return@addSnapshotListener
                    }
                    _isFavorite.value = documentSnapshot != null && documentSnapshot.exists()
                }
        }
    }

    fun toggleFavorite() = viewModelScope.launch {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { currentUser ->
            val playerId = _playerDetail.value?.PlayerID ?: return@launch
            val favDocId = "${currentUser.uid}_$playerId"
            val favRef = FirebaseFirestore.getInstance().collection("favorites").document(favDocId)

            favRef.get().addOnSuccessListener { document ->
                if (!document.exists()) {
                    val newFavorite = hashMapOf(
                        "userId" to currentUser.uid,
                        "playerId" to playerId,
                        "createdAt" to FieldValue.serverTimestamp(),
                        "nbaDotComId" to _playerDetail.value?.NbaDotComPlayerID,
                        "firstname" to _playerDetail.value?.FirstName,
                        "lastname" to _playerDetail.value?.LastName,
                    )
                    favRef.set(newFavorite)
                    _isFavorite.value = true
                } else {
                    favRef.delete()
                    _isFavorite.value = false
                }
            }.addOnFailureListener { e ->
                _errorMessages.value = "Error accessing favorites: ${e.message}"
            }
        }
    }
}

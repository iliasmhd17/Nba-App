package com.example.myesiapplication.players


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myesiapplication.RetrofitClient
import com.example.myesiapplication.data.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players
    private val _favorites = MutableStateFlow<List<Player>>(emptyList())
    val favorites: StateFlow<List<Player>> = _favorites
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    init {
        fetchPlayers()
        fetchFavorites()
    }

    private fun fetchPlayers() = viewModelScope.launch {
        val response = RetrofitClient.nbaService.getPlayers()
        if (response.isSuccessful) {
            _players.value = response.body() ?: emptyList()
        }
    }

    private fun fetchFavorites() = viewModelScope.launch {
        FirebaseAuth.getInstance().currentUser?.uid?.let { userId ->
            firebaseFirestore.collection("favorites")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener { documents ->
                    val favorites = documents.mapNotNull { doc ->
                        doc.toObject(Player::class.java).apply {
                            NbaDotComPlayerID = (doc.get("nbaDotComId") as? Number)?.toInt() ?: 0
                        }
                    }
                    _favorites.value = favorites
                }
        }
    }
}

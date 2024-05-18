package com.example.myesiapplication.informations

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InfoScreenViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _userEmail = MutableStateFlow(auth.currentUser?.email)
    val userEmail: StateFlow<String?> = _userEmail.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _userEmail.value = firebaseAuth.currentUser?.email
        }
    }

    fun logout() {
        auth.signOut()
    }
}

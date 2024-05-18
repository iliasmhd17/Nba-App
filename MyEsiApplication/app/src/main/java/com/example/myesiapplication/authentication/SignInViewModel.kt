package com.example.myesiapplication.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

class SigninViewModel : ViewModel() {
    private val _signinState = MutableStateFlow<SigninState>(SigninState.Idle)
    val signinState: MutableStateFlow<SigninState> = _signinState
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signin(email: String, password: String) {
        auth.signOut()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _signinState.value = SigninState.Success
                } else {
                    _signinState.value = SigninState.Error(task.exception?.message ?: "Unknown error")
                }
            }
    }
}

sealed class SigninState {
    data object Idle : SigninState()
    data object Success : SigninState()
    data class Error(val message: String) : SigninState()
}

package com.example.myesiapplication.authentication

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow

class SignupViewModel : ViewModel() {
    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: MutableStateFlow<SignupState> = _signupState
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signup(email: String, password: String) {
        auth.signOut()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    user?.let {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .build()

                        it.updateProfile(profileUpdates).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _signupState.value = SignupState.Success
                            } else {
                                _signupState.value = SignupState.Error(updateTask.exception?.message ?: "Unknown error")
                            }
                        }
                    }
                } else {
                    _signupState.value = SignupState.Error(task.exception?.message ?: "Unknown error")
                }
            }
        }
    fun logout() {
        auth.signOut()
    }
    }

sealed class SignupState {
    data object Idle : SignupState()
    data object Success : SignupState()
    data class Error(val message: String) : SignupState()
}

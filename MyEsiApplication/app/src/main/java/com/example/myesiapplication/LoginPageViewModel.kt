package com.example.myesiapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginPageViewModel : ViewModel() {
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun setEmail(value: String) {
        _email.value = value
        _errorMessage.value = null // Reset error message on email change
    }

    fun validateEmailAndNavigate(navController: NavHostController) {
        viewModelScope.launch {
            if (isEmailValid(_email.value)) {
                navController.navigate("imagePage")
            } else {
                _errorMessage.value = "Erreur, l'adresse e-mail n'est pas valide"
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}

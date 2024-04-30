//package com.example.myesiapplication
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class LoginPageViewModel : ViewModel() {
//    private val _email = MutableStateFlow("")
//    val email: StateFlow<String> = _email
//
//    private val _password = MutableStateFlow("")
//    val password: StateFlow<String> = _password
//
//    private val _errorMessage = MutableStateFlow<String?>(null)
//    val errorMessage: StateFlow<String?> = _errorMessage
//
//    fun setEmail(value: String) {
//        _email.value = value
//        _errorMessage.value = null
//    }
//
//    fun setPassword(value: String) {
//        _password.value = value
//        _errorMessage.value = null
//    }
//
//    private fun isEmailValid(email: String): Boolean {
//        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
//    }
//
//    fun authenticateAndNavigate(navController: NavHostController) {
//        if (!isEmailValid(email.value)) {
//            _errorMessage.value = "Erreur, l'adresse e-mail n'est pas valide"
//            return
//        }
//        viewModelScope.launch {
//            try {
//                val response = RetrofitInstance.api.authenticate(LoginRequest(email.value, password.value))
//                if (response.isSuccessful && response.body() != null) {
//                    val authResponse = response.body()!!
//                    if (authResponse.access_token.isNotEmpty()) {
//                        navController.navigate(AppScreen.ImagePage.route) {
//                            popUpTo("loginPage") { inclusive = true }
//                        }
//                    } else {
//                        _errorMessage.value = "Le token d'accès est manquant dans la réponse."
//                    }
//                } else {
//                    _errorMessage.value = "Wrong login or password"
//                }
//            } catch (e: Exception) {
//                _errorMessage.value = e.message ?: "Une erreur est survenue"
//            }
//        }
//    }
//
//}

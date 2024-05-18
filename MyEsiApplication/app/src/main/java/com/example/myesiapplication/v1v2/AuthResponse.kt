package com.example.myesiapplication.v1v2

data class AuthResponse(val access_token: String,
                        val token_type: String,
                        val expires_in: Int,
                        val expires_at: Long,
                        val refresh_token: String,
                        val user: User
)
data class User(val id: String, val email: String)

package com.example.myesiapplication
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
interface AuthService {
    @POST("auth/v1/token?grant_type=password")
    @Headers("Content-Type: application/json", "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRuc3Jpdm54bGVlcWR0YnloZnR2Iiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTY5NzE0MDI2MSwiZXhwIjoyMDEyNzE2MjYxfQ.jgJ49-c9Z8iPQnLVTnPlfRZpKwyBKht-OY8wMTceSiM")
    suspend fun authenticate(@Body loginRequest: LoginRequest): Response<AuthResponse>
}
package com.example.myesiapplication
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: AuthService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dnsrivnxleeqdtbyhftv.supabase.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }
}
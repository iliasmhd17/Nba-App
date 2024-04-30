package com.example.myesiapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: NbaService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sportsdata.io/v3/nba/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbaService::class.java)
    }
}

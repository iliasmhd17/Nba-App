package com.example.myesiapplication

import com.example.myesiapplication.data.Game
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NbaService {
    @GET("scores/json/ScoresBasic/{date}?key=befe3ddc273245c0a3969270d38638ac")
    @Headers("Content-Type: application/json")
    suspend fun getGamesByDate(@Path("date") date: String): Response<List<Game>>
}

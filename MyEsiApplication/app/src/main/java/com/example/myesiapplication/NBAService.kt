package com.example.myesiapplication
import com.example.myesiapplication.data.Game
import com.example.myesiapplication.data.News
import com.example.myesiapplication.data.Player
import com.example.myesiapplication.data.TeamDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NbaService {

    @GET("ScoresBasic/{date}")
    suspend fun getGamesByDate(@Path("date") date: String): Response<List<Game>>

    @GET("teams")
    suspend fun getTeamDetails(): Response<List<TeamDetail>>

    @GET("Players")
    suspend fun getPlayers(): Response<List<Player>>

    @GET("Player/{playerId}")
    suspend fun getPlayersById(@Path("playerId") playerId: Int): Response<Player>

    @GET("NewsByDate/{date}")
    suspend fun getNews(@Path("date") date: String): Response<List<News>>
}



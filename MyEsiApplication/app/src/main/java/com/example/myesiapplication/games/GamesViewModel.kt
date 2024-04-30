package com.example.myesiapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myesiapplication.data.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class GameViewModel : ViewModel() {
    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games

    private val nbaService: NbaService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.sportsdata.io/v3/nba/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbaService::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    var selectedDate = MutableStateFlow(LocalDate.now())

    fun fetchGames(date: String) = viewModelScope.launch {
        val response = nbaService.getGamesByDate(date)
        if (response.isSuccessful) {
            _games.value = response.body() ?: emptyList()
        } else {
            // Log error or handle it as necessary
        }
    }
    fun setSelectedDate(newDate: LocalDate) {
        selectedDate.value = newDate
        fetchGames(newDate.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
    init {
        fetchGames(selectedDate.value.format(DateTimeFormatter.ISO_LOCAL_DATE))
    }
}



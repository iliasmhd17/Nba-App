package com.example.myesiapplication.games

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myesiapplication.RetrofitClient
import com.example.myesiapplication.data.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class GameViewModel : ViewModel() {
    private val _games = MutableStateFlow<List<Game>>(emptyList())
    val games: StateFlow<List<Game>> = _games


    @RequiresApi(Build.VERSION_CODES.O)
    var selectedDate = MutableStateFlow(LocalDate.now())

    private fun fetchGames(date: String) = viewModelScope.launch {
        val response = RetrofitClient.nbaService.getGamesByDate(date)
        if (response.isSuccessful) {
            _games.value = response.body() ?: emptyList()
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

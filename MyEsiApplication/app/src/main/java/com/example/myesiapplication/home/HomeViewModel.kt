package com.example.myesiapplication.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myesiapplication.RetrofitClient
import com.example.myesiapplication.data.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel : ViewModel() {
    val latestNews = MutableStateFlow<News?>(null)
    private val _scrollPosition = MutableStateFlow(0)
    val scrollPosition = _scrollPosition.asStateFlow()
    private val _drawerState = MutableStateFlow(false)
    val drawerState = _drawerState.asStateFlow()

    init {
        fetchLatestNews()
        simulateAutoScroll()
    }

    private fun fetchLatestNews() {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            try {
                val response = RetrofitClient.nbaService.getNews(currentDate)
                if (response.isSuccessful) {
                    latestNews.value = response.body()?.firstOrNull()
                } else {
                    latestNews.value = null
                }
            } catch (e: Exception) {
                latestNews.value = null
            }
        }
    }

    private fun simulateAutoScroll() {
        viewModelScope.launch {
            var increment = true
            while (true) {
                val currentPos = _scrollPosition.value

                _scrollPosition.value = if (increment) currentPos + 1 else currentPos - 1

                if (currentPos >= 1000) increment = false
                if (currentPos <= 0) increment = true

                delay(50)
            }
        }
    }

    fun openDrawer() {
        _drawerState.value = true
    }

    fun closeDrawer() {
        _drawerState.value = false
    }
}

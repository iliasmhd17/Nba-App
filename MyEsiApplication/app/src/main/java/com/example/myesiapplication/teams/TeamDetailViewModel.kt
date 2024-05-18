package com.example.myesiapplication.teams

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myesiapplication.RetrofitClient
import com.example.myesiapplication.data.TeamDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class TeamDetailViewModel : ViewModel() {
    private val _teamDetails = MutableStateFlow<List<TeamDetail>?>(null)
    val teamDetails: StateFlow<List<TeamDetail>?> = _teamDetails

    private val _teamDetail = MutableStateFlow<TeamDetail?>(null)
    val teamDetail: StateFlow<TeamDetail?> = _teamDetail

    init {
        fetchTeamDetails()
    }

    fun fetchTeamDetails(teamId: Int? = null) = viewModelScope.launch {
        val response = RetrofitClient.nbaService.getTeamDetails()
        if (response.isSuccessful && response.body() != null) {
            val teams = response.body()
            _teamDetails.value = teams
            if (teamId != null) {
                val team = teams?.firstOrNull { it.TeamID == teamId }
                _teamDetail.value = team
            }
        } else {
            _teamDetails.value = emptyList()
            if (teamId != null) _teamDetail.value = null
        }
    }
}

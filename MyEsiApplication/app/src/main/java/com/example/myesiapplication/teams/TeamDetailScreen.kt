package com.example.myesiapplication.teams

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myesiapplication.R
import com.example.myesiapplication.data.TeamDetail
import com.example.myesiapplication.games.PageTopBar
import com.example.myesiapplication.games.getTeamLogo

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeamDetailBackground(teamId: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dar),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        TeamDetailScreen(teamId)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeamDetailScreen(teamId: Int, viewModel: TeamDetailViewModel = viewModel()) {
    LaunchedEffect(teamId) {
        viewModel.fetchTeamDetails(teamId)
    }
    val teamDetail by viewModel.teamDetail.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PageTopBar()
        TeamDetailContent(teamDetail)
    }
}

@Composable
fun TeamDetailContent(teamDetail: TeamDetail?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        teamDetail?.let { team ->
            Spacer(Modifier.height(16.dp))
            Image(
                painter = painterResource(getTeamLogo(team.Key)),
                contentDescription = "Team Logo",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Team Name: ${team.Name}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = "City: ${team.City}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = "Conference: ${team.Conference}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = "Division: ${team.Division}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = "Head Coach: ${team.HeadCoach}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
        } ?: Text(
            text = "Loading team details...",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

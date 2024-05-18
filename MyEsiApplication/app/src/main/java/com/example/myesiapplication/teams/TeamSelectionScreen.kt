package com.example.myesiapplication.teams
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myesiapplication.R
import com.example.myesiapplication.data.TeamLogo
import com.example.myesiapplication.games.PageTopBar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeamSelectionBackground(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dar),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        TeamSelectionScreen(navController)
    }
}

@OptIn(ExperimentalCoilApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TeamSelectionScreen(navController: NavController, viewModel: TeamDetailViewModel = viewModel()) {
    val teamDetails by viewModel.teamDetails.collectAsState()
    val teamDetailsMap = teamDetails?.associateBy { it.Key } ?: mapOf()
    val scrollState = rememberLazyGridState()

    Column(modifier = Modifier.fillMaxSize()) {
        PageTopBar()
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = scrollState,
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(TeamLogo.entries.filter { it != TeamLogo.UNKNOWN }) { teamLogo ->
                val teamDetail = teamDetailsMap[teamLogo.name]
                Image(
                    painter = rememberImagePainter(data = teamLogo.logoId),
                    contentDescription = "Logo of ${teamLogo.name}",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            teamDetail?.let {
                                navController.navigate("team_detail_screen/${it.TeamID}")
                            }
                        }
                        .size(100.dp)
                )
            }
        }
    }
}

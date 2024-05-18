package com.example.myesiapplication.players
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myesiapplication.R
import com.example.myesiapplication.games.PageTopBar


@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun PlayerDetailsBackground(playerId: Int) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dar),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE1E1E1))
            ) {
                PageTopBar()
            }
            PlayerDetailsScreen(playerId)
        }
    }
}
@OptIn(ExperimentalCoilApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PlayerDetailsScreen(playerId: Int, viewModel: PlayerDetailsScreenViewModel = viewModel()) {
    LaunchedEffect(playerId) {
        viewModel.fetchPlayerDetails(playerId)
    }
    val player = viewModel.playerDetail.collectAsState().value

    if (player != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    FavoriteIcon(viewModel = viewModel)
                }
                val imageUrl = "https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/${player.NbaDotComPlayerID}.png"
                val imagePainter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                        placeholder(R.drawable.default_avatar)
                        error(R.drawable.default_avatar)
                    }
                )
                Image(
                    painter = imagePainter,
                    contentDescription = "Player Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
                PlayerDetailCard("Name", "${player.FirstName} ${player.LastName}")
                PlayerDetailCard("Team", player.Team)
                PlayerDetailCard("Position", player.Position)
                PlayerDetailCard("Jersey Number", player.Jersey.toString())
                PlayerDetailCard("Height", "${player.HeightInM} cm")
                PlayerDetailCard("Weight", "${player.WeightInKg} kg")
                PlayerDetailCard("Birth Date", player.BirthDateWithoutTime)
                PlayerDetailCard("Birth City", player.BirthCity)
                PlayerDetailCard("Birth Country", player.BirthCountry)
                PlayerDetailCard("College", player.College)
                PlayerDetailCard("Salary", "\$${player.Salary}")
                PlayerDetailCard("Experience", "${player.Experience} years")
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loading player details...", color = Color.Gray, fontSize = 20.sp)
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FavoriteIcon(viewModel: PlayerDetailsScreenViewModel) {
    val isFavorite by viewModel.isFavorite.collectAsState()
    IconButton(onClick = { viewModel.toggleFavorite() }) {
        Icon(
            painter = if (isFavorite) painterResource(id = R.drawable.coeur_false) else painterResource(id = R.drawable.favorite_true),
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color.Red else Color.Gray
        )
    }
}
@Composable
fun PlayerDetailCard(label: String, value: String, shape: Shape = RoundedCornerShape(8.dp)) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = shape,
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

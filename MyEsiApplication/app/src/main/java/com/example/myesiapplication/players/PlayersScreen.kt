package com.example.myesiapplication.players


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.myesiapplication.R
import com.example.myesiapplication.data.Player
import com.example.myesiapplication.games.PageTopBar
val nbaRed = Color(0xFFC8102E)

@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun PlayerSelectionBackground(navController: NavController) {
    var searchText by remember { mutableStateOf("") }
    var showFavoritesOnly by remember { mutableStateOf(false) }
    val viewModel: PlayerViewModel = viewModel()

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
            SearchBar(searchText = searchText, onSearchTextChange = { searchText = it })
            Button(
                onClick = { showFavoritesOnly = !showFavoritesOnly },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = nbaRed,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Text(if (showFavoritesOnly) "Show All" else "Show Favorites")
            }

            if (showFavoritesOnly) {
                PlayersScreen(navController, searchText, viewModel.favorites.collectAsState().value)
            } else {
                PlayersScreen(navController, searchText, viewModel.players.collectAsState().value)
            }
        }
    }
}

@Composable
fun PlayersScreen(navController: NavController,searchText: String,players: List<Player>) {
    val filteredPlayers = players.filter {
        it.FirstName.contains(searchText, ignoreCase = true) ||
                it.LastName.contains(searchText, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (filteredPlayers.isEmpty()) {
            Text(
                "None players found",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp),
                color = Color.Gray
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredPlayers) { player ->
                    PlayerGridItem(player, navController)
                }
            }
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var lastInput by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = {
            lastInput = it
            onSearchTextChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        textStyle = TextStyle(fontSize = 16.sp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        placeholder = { Text("Search for a player", color = Color.Gray) },
        singleLine = true,
        shape = RoundedCornerShape(20.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            if (lastInput != searchText) {
                onSearchTextChange(lastInput)
            }
            keyboardController?.hide()
        })
    )
}



@OptIn(ExperimentalCoilApi::class)
@Composable
fun PlayerGridItem(player: Player, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .width(120.dp)
            .height(180.dp)
            .clickable {
                navController.navigate("player_details_screen/${player.PlayerID}")
            }
    ) {

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
                .size(115.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${player.FirstName} ${player.LastName}",
            style = MaterialTheme.typography.body2.copy(
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.Serif,
                color = Color.White
            ),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}



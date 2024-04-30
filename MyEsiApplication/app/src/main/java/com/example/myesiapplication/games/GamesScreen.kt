package com.example.myesiapplication.games

import android.app.DatePickerDialog
import androidx.compose.ui.graphics.Color
import androidx.compose.material.TextFieldDefaults
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myesiapplication.GameViewModel
import com.example.myesiapplication.R
import com.example.myesiapplication.data.Game
import com.example.myesiapplication.data.TeamLogo
import com.example.myesiapplication.home.HomeTopBar
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameScreenBackground(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        GamesScreen(navController)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GamesScreen(navController: NavController, gameViewModel: GameViewModel = viewModel()) {
    val games by gameViewModel.games.collectAsState()
    val selectedDate by gameViewModel.selectedDate.collectAsState()
    val context = LocalContext.current

    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val year = selectedDate.year
        val month = selectedDate.monthValue - 1
        val day = selectedDate.dayOfMonth

        DatePickerDialog(context, { _, newYear, newMonth, newDay ->
            val newDate = LocalDate.of(newYear, newMonth + 1, newDay)
            gameViewModel.setSelectedDate(newDate)
            showDatePicker = false
        }, year, month, day).show()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HomeTopBar(onLoginClick = {
            navController.navigateUp()  // pour l'instant le menu principale.
        })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier
                    .clickable { showDatePicker = true }
                    .padding(8.dp),
                tint = Color.Red

            )
            OutlinedTextField(
                value = selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .weight(1f),
                label = { Text("Selected Date") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White
                )
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(games) { game ->
                GameItem(game)
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameItem(game: Game) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { /* Handle click here */ },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val homeTeamLogo = getTeamLogo(game.HomeTeam)
            val awayTeamLogo = getTeamLogo(game.AwayTeam)
            val isPast = try {
                game.DateTime.let {
                    LocalDateTime.parse(it).isBefore(LocalDateTime.now())
                }
            } catch (e: Exception) {
                false
            }
            if (isPast && game.HomeTeamScore != null && game.AwayTeamScore != null) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${game.AwayTeam} : ${game.AwayTeamScore}",
                        color = Color.Black,
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "${game.HomeTeam} : ${game.HomeTeamScore}",
                        color = Color.Black,
                        style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = awayTeamLogo),
                    contentDescription = "Logo ${game.AwayTeam}",
                    modifier = Modifier
                        .size(70.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = homeTeamLogo),
                    contentDescription = "Logo ${game.HomeTeam}",
                    modifier = Modifier
                        .size(70.dp)
                )
            }

            Text(
                text = formatGameTime(game.DateTime),
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatGameTime(dateTime: String?): String {
    if (dateTime.isNullOrBlank()) {
        return "Not determined"
    }

    return try {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val utcDateTime = LocalDateTime.parse(dateTime, formatter)
        val zonedUtcDateTime = utcDateTime.atZone(ZoneId.of("UTC"))
        val brusselsDateTime = zonedUtcDateTime.withZoneSameInstant(ZoneId.of("Europe/Brussels"))
        brusselsDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT))
    } catch (e: Exception) {
        e.printStackTrace()
        "Invalid date format"
    }
}


fun getTeamLogo(teamAbbreviation: String): Int {
    return TeamLogo.entries.find { it.name == teamAbbreviation.uppercase(Locale.ROOT) }?.logoId ?: TeamLogo.UNKNOWN.logoId
}

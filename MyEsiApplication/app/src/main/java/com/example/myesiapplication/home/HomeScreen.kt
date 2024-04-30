package com.example.myesiapplication.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myesiapplication.R

@Composable
fun HomeScreenBackground(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        HomeScreen(navController)
    }
}
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = { HomeTopBar(onLoginClick = {}) },
        content = { paddingValues ->
            HomeContent(navController, paddingValues)
        },
        backgroundColor = Color.Transparent
    )
}
@Composable
fun HomeTopBar(onLoginClick: () -> Unit) {
    TopAppBar(
        title = {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(start = 0.dp), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.topbar_logo),
                    contentDescription = "NBA Logo",
                    modifier = Modifier.height(40.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onLoginClick() }) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Login")
            }
        },
        actions = {
            Spacer(Modifier.size(48.dp))
        },
        backgroundColor = Color.Red,
        contentColor = Color.White
    )
}




@Composable
fun HomeContent(navController: NavController, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                    start = Offset(0f, 1f),
                    end = Offset(0f, 0f)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.nba_logo),
            contentDescription = "NBA Logo",
            modifier = Modifier
                .height(200.dp)
                .offset(y = (-100).dp),
        )

        Text(
            text = "About what you want to learn today?",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Cursive,
            letterSpacing = 0.15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(32.dp))
        HomeButtons(navController)
    }
}



@Composable
fun HomeButtons(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { navController.navigate("TeamSelectionScreen") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("TEAMS")
        }
        Button(
            onClick = { navController.navigate("players") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("PLAYERS")
        }
        Button(
            onClick = { navController.navigate("gamesScreen") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text("MATCH DAY!")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenBackground(rememberNavController())
}

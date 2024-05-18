package com.example.myesiapplication.informations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myesiapplication.R
import com.example.myesiapplication.games.PageTopBar
import com.example.myesiapplication.ui.theme.NBA_RED

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AboutScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dar),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        AboutContent(navController)
    }
}

@Composable
fun AboutContent(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
    PageTopBar()
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "About us",
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
            Text(
                text = "Version",
                fontSize = 24.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "1.0.0",
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("feedback-screen")
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = NBA_RED),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Feedback",
                    tint = Color.White
                )
                Text(
                    text = "Send Feedback",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}


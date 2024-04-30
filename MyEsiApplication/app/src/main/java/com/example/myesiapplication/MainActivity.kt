//package com.example.myesiapplication
//
//import LogoImageWithBottomBar
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import com.example.myesiapplication.ui.theme.EsiPageTheme
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            EsiPageTheme {
//                val navController = rememberNavController()
//                NavHost(navController = navController, startDestination = "loginPage") {
//                    composable(AppScreen.LoginPage.route) { LoginPage(navController) }
//                    composable(AppScreen.ImagePage.route) { LogoImageWithBottomBar(navController) }
//                    composable(AppScreen.AboutPage.route) { AboutPage(navController)}
//                }
//            }
//        }
//    }

package com.example.myesiapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.myesiapplication.ui.theme.EsiPageTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myesiapplication.games.GameScreenBackground
import com.example.myesiapplication.home.HomeScreenBackground
import com.example.myesiapplication.teams.TeamSelectionScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsiPageTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "homeScreen") {
                    composable("homeScreen") { HomeScreenBackground(navController) }
                    composable("gamesScreen") { GameScreenBackground(navController) }
                    composable("TeamSelectionScreen"){ TeamSelectionScreen(navController)}
                }
            }
        }
    }
}

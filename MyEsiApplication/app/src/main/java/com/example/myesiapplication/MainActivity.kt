package com.example.myesiapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myesiapplication.ui.theme.EsiPageTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsiPageTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "loginPage") {
                    composable(AppScreen.LoginPage.route) { LoginPage(navController) }
                    composable(AppScreen.ImagePage.route) { LogoImage() }
                }
            }
        }
    }
}
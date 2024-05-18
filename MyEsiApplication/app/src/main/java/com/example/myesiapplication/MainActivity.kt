package com.example.myesiapplication
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import com.example.myesiapplication.ui.theme.EsiPageTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myesiapplication.authentication.SigninScreenBackground
import com.example.myesiapplication.authentication.SignupScreenBackground
import com.example.myesiapplication.games.GameScreenBackground
import com.example.myesiapplication.home.HomeScreenBackground
import com.example.myesiapplication.informations.AboutScreen
import com.example.myesiapplication.informations.FeedBackScreenBackground
import com.example.myesiapplication.informations.InfoScreenBackground
import com.example.myesiapplication.players.PlayerDetailsBackground
import com.example.myesiapplication.players.PlayerSelectionBackground
import com.example.myesiapplication.teams.TeamDetailBackground
import com.example.myesiapplication.teams.TeamSelectionBackground

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EsiPageTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "signup-screen") {
                    composable("signup-screen") { SignupScreenBackground(navController) }
                    composable("about-screen") { AboutScreen(navController) }
                    composable("feedback-screen") { FeedBackScreenBackground(navController) }
                    composable("signin-screen") { SigninScreenBackground(navController) }
                    composable("homeScreen") { HomeScreenBackground(navController) }
                    composable("info-screen") { InfoScreenBackground(navController) }
                    composable("gamesScreen") { GameScreenBackground() }
                    composable("TeamSelectionScreen"){ TeamSelectionBackground(
                        navController) }
                    composable("team_detail_screen/{teamId}",
                        arguments = listOf(navArgument("teamId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val teamId = backStackEntry.arguments?.getInt("teamId") ?: return@composable
                        TeamDetailBackground(teamId)
                    }
                    composable("players") { PlayerSelectionBackground(navController) }
                    composable(
                        route = "player_details_screen/{playerId}",
                        arguments = listOf(navArgument("playerId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val playerId = backStackEntry.arguments?.getInt("playerId") ?: return@composable
                        PlayerDetailsBackground(playerId = playerId)
                    }


                }
            }
        }
    }
}

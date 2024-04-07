package com.example.myesiapplication
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myesiapplication.ui.theme.EsiPageTheme

@Composable
fun AboutPage(navController: NavController, viewModel: AboutPageViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Meh D. Ilias",
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Id: 55727",
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "F12",
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Gray
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                selected = false,
                onClick = { viewModel.selectItem(0)
                    navController.navigate(AppScreen.ImagePage.route) {
                        launchSingleTop = true
                    } }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Info, contentDescription = "About") },
                selected = true,
                onClick = { viewModel.selectItem(1)
                    navController.navigate(AppScreen.AboutPage.route) {
                        launchSingleTop = true
                    }}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutPagePreview() {
    val navController = rememberNavController()
    EsiPageTheme {
        AboutPage(navController)
    }
}

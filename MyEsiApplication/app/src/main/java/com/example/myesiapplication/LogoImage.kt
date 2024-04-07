import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myesiapplication.LogoImageViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myesiapplication.AppScreen
import com.example.myesiapplication.R
import com.example.myesiapplication.ui.theme.EsiPageTheme

@Composable
fun LogoImageWithBottomBar(navController: NavController, viewModel: LogoImageViewModel = viewModel()) {
    val selectedIndex by viewModel.selectedIndex.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.weight(1f)
        ) {
            val image = painterResource(id = R.drawable.logoesi)
            Image(
                painter = image,
                contentDescription = "Logo"
            )
        }
        BottomNavigation(
            backgroundColor = Color.White,
            contentColor = Color.Gray
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                selected = selectedIndex == 0,
                onClick = {
                    viewModel.selectItem(0)
                    navController.navigate(AppScreen.ImagePage.route) {
                        launchSingleTop = true
                    }
                }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Info, contentDescription = "About") },
                selected = selectedIndex == 1,
                onClick = {
                    viewModel.selectItem(1)
                    navController.navigate(AppScreen.AboutPage.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EsiPreviewWithBottomBar() {
    val navController = rememberNavController()
    EsiPageTheme {
        LogoImageWithBottomBar(navController)
    }
}

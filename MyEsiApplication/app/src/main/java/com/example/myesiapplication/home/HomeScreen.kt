package com.example.myesiapplication.home
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myesiapplication.R
import com.example.myesiapplication.data.News

val NBA_RED = Color(0xFFC8102E)

@Composable
fun HomeScreenBackground(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dar),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        HomeScreen(navController)
    }
}

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    val scaffoldState = rememberScaffoldState()
    val drawerState by viewModel.drawerState.collectAsState()

    LaunchedEffect(drawerState) {
        if (drawerState) {
            scaffoldState.drawerState.open()
        } else {
            scaffoldState.drawerState.close()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopBar(viewModel) },
        drawerContent = { DrawerContent(navController, viewModel) },
        content = { paddingValues -> HomeContent(navController, paddingValues) },
        backgroundColor = Color.Transparent
    )
}

@Composable
fun HomeTopBar(viewModel: HomeViewModel) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = (-28).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.topbar_logo),
                    contentDescription = "NBA Logo",
                    modifier = Modifier.height(40.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { viewModel.openDrawer() }) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        backgroundColor = NBA_RED,
        contentColor = Color.White
    )
}

@Composable
fun DrawerContent(navController: NavController, viewModel: HomeViewModel) {
    Column(
        modifier = Modifier
            .background(NBA_RED)
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        DrawerButton("My ids", onClick = {
            navController.navigate("info-screen")
            viewModel.closeDrawer()
        })
        DrawerButton("About", onClick = {
            navController.navigate("about-screen")
            viewModel.closeDrawer()
        })
    }
}

@Composable
fun DrawerButton(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
        style = MaterialTheme.typography.h6,
        color = Color.White
    )
}

@Composable
fun HomeContent(navController: NavController, paddingValues: PaddingValues, viewModel: HomeViewModel = viewModel()) {
    val news by viewModel.latestNews.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                    start = Offset(0f, 1f),
                    end = Offset(0f, 0f)
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "NBA Logo",
            modifier = Modifier
                .height(300.dp)
                .width(400.dp)
                .offset(y = (-10).dp)
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
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        HomeButtons(navController)
        Spacer(Modifier.height(32.dp))

        news?.let { NewsCard(it) }
    }
}

@Composable
fun NewsCard(news: News, viewModel: HomeViewModel = viewModel()) {
    val darkerBlue = Color(0xFF131E57)
    val scrollState = rememberScrollState()
    val scrollPosition by viewModel.scrollPosition.collectAsState()

    LaunchedEffect(scrollPosition) {
        scrollState.animateScrollTo(scrollPosition)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(NBA_RED)
            .padding(4.dp)
    ) {
        Text(
            text = "BREAKING NEWS",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.button
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .heightIn(max = 100.dp),
        elevation = 4.dp,
        backgroundColor = darkerBlue
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = news.Title,
                style = MaterialTheme.typography.h6.copy(
                    color = Color.White,
                    lineHeight = 22.sp
                )
            )
            Text(
                text = news.Content,
                style = MaterialTheme.typography.body2.copy(
                    color = Color.White,
                    lineHeight = 20.sp
                )
            )
        }
    }
}

@Composable
fun HomeButtons(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            onClick = { navController.navigate("TeamSelectionScreen") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = NBA_RED,
                contentColor = Color.White
            )
        ) {
            Text("TEAMS")
        }
        Button(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            onClick = { navController.navigate("players") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = NBA_RED,
                contentColor = Color.White
            )
        ) {
            Text("PLAYERS")
        }
        Button(
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            onClick = { navController.navigate("gamesScreen") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = NBA_RED,
                contentColor = Color.White
            )
        ) {
            Text("SCHEDULE")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenBackground(rememberNavController())
}

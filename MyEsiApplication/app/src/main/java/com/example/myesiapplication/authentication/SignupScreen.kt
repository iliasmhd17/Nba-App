package com.example.myesiapplication.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.CircularProgressIndicator
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.MaterialTheme
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myesiapplication.R
import com.example.myesiapplication.ui.theme.NBA_RED

val button = Color(0xFF1D60DE)

@Composable
fun SignupScreenBackground(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dar),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        SignupScreen(navController)
    }
}
@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel = viewModel()) {
    val signupState by viewModel.signupState.collectAsState(SignupState.Idle)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.h4,
            color = Color.White

        )

        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Just a few quick things to get started",
            modifier = Modifier.height(20.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(55.dp)
                .padding(horizontal = 16.dp),
            label = { Text("Email") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(55.dp)
                .padding(horizontal = 16.dp),
            label = { Text("Password") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                textColor = Color.Black,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.logout()
                navController.navigate("homeScreen")
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = button),
        ) {
            Text("I want to be a guest", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate("signin-screen")
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = button),
        ) {
            Text("Already Registered?", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Email and password must not be empty."
                    loading = false
                } else {
                    errorMessage = null
                    loading = true
                    viewModel.signup(email, password)
                }
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = button),
        ) {
            if (loading) {
                CircularProgressIndicator(color = Color.Blue)
            } else {
                Text("Sign Up", color = Color.White)
            }
        }
        errorMessage?.let {
            Text(
                text = it,
                color = NBA_RED,
                modifier = Modifier.padding(16.dp)
            )
        }
        when (signupState) {
            is SignupState.Success -> {
                LaunchedEffect(Unit) {
                    loading = false
                }
                Text(
                    "Signup successful",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
                navController.navigate("homescreen")
            }
            is SignupState.Error -> {
                Text(
                    "Error: ${(signupState as SignupState.Error).message}",
                    color = NBA_RED,
                    modifier = Modifier.padding(top = 8.dp)
                )
                loading = false
            }
            else -> Unit
        }
    }
}
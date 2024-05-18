package com.example.myesiapplication.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myesiapplication.R
import com.example.myesiapplication.ui.theme.NBA_RED

@Composable
fun SigninScreenBackground(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.dar),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        SigninScreen(navController)
    }
}

@Composable
fun SigninScreen(navController: NavController, viewModel: SigninViewModel = viewModel()) {
    val signinState by viewModel.signinState.collectAsState(SigninState.Idle)
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
            text = "Sign In",
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Hello, welcome back!",
            modifier = Modifier.height(20.dp),
            color = Color.White
        )
        Spacer(modifier = Modifier.height(15.dp))
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
            shape = RoundedCornerShape(30.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Email and password must not be empty."
                    loading = false
                } else {
                    errorMessage = null
                    loading = true
                    viewModel.signin(email, password)
                }
            },
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF1D60DE)),
        ) {
            if (loading) {
                CircularProgressIndicator(color = Color.Blue)
            } else {
                Text("Sign In", color = Color.White)
            }
        }
        errorMessage?.let {
            Text(
                text = it,
                color = NBA_RED,
                modifier = Modifier.padding(16.dp)
            )
        }
        when (signinState) {
            is SigninState.Success -> {
                LaunchedEffect(Unit) {
                    loading = false
                }
                Text(
                    "Signin successful",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
                navController.navigate("homescreen")
            }
            is SigninState.Error -> {
                Text(
                    "Error: ${(signinState as SigninState.Error).message}",
                    color = NBA_RED,
                    modifier = Modifier.padding(top = 8.dp)
                )
                loading = false
            }
            else -> Unit
        }
    }
}

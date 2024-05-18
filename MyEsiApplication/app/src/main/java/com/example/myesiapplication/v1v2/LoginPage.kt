//package com.example.myesiapplication
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.CornerSize
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusDirection
//import androidx.compose.ui.platform.LocalFocusManager
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import androidx.lifecycle.viewmodel.compose.viewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LoginPage(navController: NavHostController) {
//    val loginPageViewModel: LoginPageViewModel = viewModel()
//    val email by loginPageViewModel.email.collectAsState()
//    val errorMessage by loginPageViewModel.errorMessage.collectAsState()
//    val focusManager = LocalFocusManager.current
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text(text = stringResource(id = R.string.login_title)) },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
//            )
//        },
//        content = { innerPadding ->
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(MaterialTheme.colorScheme.background)
//                    .padding(innerPadding)
//                    .padding(16.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//                    shape = MaterialTheme.shapes.medium.copy(CornerSize(8.dp))
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .padding(16.dp)
//                    ) {
//                        OutlinedTextField(
//                            value = email,
//                            onValueChange = loginPageViewModel::setEmail,
//                            label = { Text(stringResource(id = R.string.email_address)) },
//                            singleLine = true,
//                            isError = errorMessage != null,
//                            modifier = Modifier.fillMaxWidth(),
//                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next), // Ici pour gérer l'action Suivant
//                            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(
//                                FocusDirection.Down) })
//                        )
//                        val password by loginPageViewModel.password.collectAsState()
//                        OutlinedTextField(
//                            value = password,
//                            onValueChange = loginPageViewModel::setPassword,
//                            label = { Text(stringResource(id = R.string.password)) },
//                            singleLine = true,
//                            isError = errorMessage != null,
//                            modifier = Modifier.fillMaxWidth(),
//                            visualTransformation = PasswordVisualTransformation(),
//                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
//                        )
//                        if (errorMessage != null) {
//                            Text(
//                                text = errorMessage ?: "",
//                                color = MaterialTheme.colorScheme.error,
//                                modifier = Modifier.padding(top = 8.dp)
//                            )
//                        }
//                        Spacer(modifier = Modifier.height(8.dp))
//                        Button(
//                            onClick = { loginPageViewModel.authenticateAndNavigate(navController) },
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Text(stringResource(id = R.string.login_button))
//                        }
//                    }
//                }
//            }
//        }
//    )
//}

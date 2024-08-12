package com.vishu.chatapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vishu.chatapplication.AuthState
import com.vishu.chatapplication.AuthViewModel
import com.vishu.chatapplication.R
import com.vishu.chatapplication.navigation.LOGIN_SCREEN
import com.vishu.chatapplication.ui.theme.sky

@Composable
fun SignupScreen(navHostController: NavHostController, authViewModel: AuthViewModel) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    val authState by authViewModel.authState.observeAsState() // Observing authState

    Box(
        modifier = Modifier
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "Chat App",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = customFontFamily
                    )
                )
                Spacer(modifier = Modifier.height(25.dp))

                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .heightIn(min = 56.dp),
                    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .heightIn(min = 56.dp),
                    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .heightIn(min = 56.dp),
                    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 12.dp),
                    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            val icon = if (passwordVisibility) {
                                painterResource(id = R.drawable.eye)
                            } else {
                                painterResource(id = R.drawable.hide)
                            }
                            Image(
                                painter = icon,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = { authViewModel.signup(email, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Sign Up", color = Color.White, fontFamily = customFontFamily)
                }

                // Displaying the current state (loading, error, success)
                when (authState) {
                    is AuthState.Loading -> {
                        CircularProgressIndicator(color = Color.White)
                    }
                    is AuthState.Error -> {
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = Color.Red,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    is AuthState.Authenticated -> {
                        // Navigate to another screen if sign-up is successful
                        LaunchedEffect(Unit) {
                            navHostController.navigate("NextScreen")
                        }
                    }
                    else -> { /* No other states to handle */ }
                }
            }
        }

        // Fixed bottom bar with line above the text
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 0.dp)
                .offset(y = (-40).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp, vertical = 12.dp)
            )
            TextButton(onClick = { navHostController.navigate(LOGIN_SCREEN) }) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Already have an account? ")
                        }
                        withStyle(style = SpanStyle(color = sky)) {
                            append("Log in")
                        }
                    },
                    fontFamily = customFontFamily,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

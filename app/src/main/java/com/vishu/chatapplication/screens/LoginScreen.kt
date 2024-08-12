package com.vishu.chatapplication.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import com.vishu.chatapplication.navigation.SIGNUP_SCREEN
import com.vishu.chatapplication.ui.theme.sky

val customFontFamily = FontFamily(
    Font(R.font.brlnsdb)
)

@Composable
fun LoginScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel
) {
    // Observe the authentication state
    val authState by authViewModel.authState.observeAsState()

    // Handle state for loading and error messages
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Manage username, password, and password visibility
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    // Update error message based on auth state
    LaunchedEffect(authState) {
        Log.d("LoginScreen", "AuthState: $authState")
        when (authState) {
            is AuthState.Loading -> {
                isLoading = true
                errorMessage = null
            }
            is AuthState.Authenticated -> {
                isLoading = false
                // Navigate to home screen on successful login
                navHostController.navigate("home_screen") {
                    popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                }
            }
            is AuthState.Error -> {
                isLoading = false
                errorMessage = (authState as AuthState.Error).message
            }
            else -> {
                isLoading = false
            }
        }
    }

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
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Email or Phone") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 12.dp)
                        .heightIn(min = 56.dp),
                    textStyle = TextStyle(color = Color.Black, fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                    Spacer(modifier = Modifier.height(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { /* Handle forgotten password */ }) {
                            Text(text = "Forgotten password?", color = sky, fontFamily = customFontFamily)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = {
                        isLoading = true // Show loading indicator
                        errorMessage = null // Clear previous errors
                        authViewModel.login(username, password) // Call the login function
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(text = "Log In", color = Color.White, fontFamily = customFontFamily)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "OR", color = Color.Gray, fontSize = 16.sp, fontFamily = customFontFamily)

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .clickable { /* Handle Facebook login */ }
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Facebook logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Log in with Facebook",
                        color = sky,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

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
            TextButton(onClick = { navHostController.navigate(SIGNUP_SCREEN) }) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Don't have an account? ")
                        }
                        withStyle(style = SpanStyle(color = sky)) {
                            append("Sign up")
                        }
                    },
                    fontFamily = customFontFamily,
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }

        // Show loading state if the authentication is in progress
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        // Handle error state
        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

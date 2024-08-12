package com.vishu.chatapplication.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishu.chatapplication.AuthViewModel
import com.vishu.chatapplication.screens.ChatScreen
import com.vishu.chatapplication.screens.HomeScreen
import com.vishu.chatapplication.screens.LoginScreen
import com.vishu.chatapplication.screens.SignupScreen
import com.vishu.chatapplication.screens.StartScreen

const val LOGIN_SCREEN = "login_screen"
const val START_SCREEN = "start_screen"
const val SIGNUP_SCREEN = "signup_screen"
const val HOME_SCREEN = "home_screen"
const val CHAT_SCREEN = "chat_screen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation(authViewModel: AuthViewModel) {
    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = START_SCREEN) {
        composable(START_SCREEN) {
            StartScreen(navHostController, authViewModel)
        }
        composable(LOGIN_SCREEN) {
            LoginScreen(navHostController, authViewModel)
        }
        composable(SIGNUP_SCREEN) {
            SignupScreen(navHostController, authViewModel)
        }
        composable(HOME_SCREEN) {
            HomeScreen(navHostController, authViewModel)
        }
        composable(CHAT_SCREEN) {
            ChatScreen(navHostController, authViewModel)
        }
    }
}

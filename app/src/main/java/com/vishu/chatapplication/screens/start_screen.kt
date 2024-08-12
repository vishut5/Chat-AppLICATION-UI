package com.vishu.chatapplication.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.vishu.chatapplication.AuthViewModel
import com.vishu.chatapplication.R
import com.vishu.chatapplication.components.ButtonComponent
import com.vishu.chatapplication.components.IconComponentImageVector
import com.vishu.chatapplication.components.SpacerHeight
import com.vishu.chatapplication.components.SpacerWidth
import com.vishu.chatapplication.navigation.LOGIN_SCREEN
import com.vishu.chatapplication.navigation.SIGNUP_SCREEN
import com.vishu.chatapplication.ui.theme.Aqua

@Composable
fun StartScreen(
    navHostController: NavHostController,authViewModel: AuthViewModel
) {
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable { showBottomSheet = false } // Dismiss bottom sheet on outside click
    ) {
        Image(
            painter = painterResource(id = R.drawable.chatappback),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 220.dp)
                .align(Alignment.Center)
                .background(Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 40.dp)
            ) {
                Text(
                    text = stringResource(R.string.stay_connected_with_your_friends_and_family),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                SpacerHeight(15.dp)
                CustomCheckBox()
            }
        }

        ButtonComponent(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-60).dp)
        ) {
            showBottomSheet = true
        }

        if (showBottomSheet) {
            BottomSheet(
                navHostController = navHostController,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clickable { } // Prevent click events from dismissing the bottom sheet
            )
        }
    }
}

@Composable
fun CustomCheckBox() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp,
                        bottomEnd = 80.dp,
                        bottomStart = 80.dp
                    )
                )
                .background(Aqua)
                .size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            IconComponentImageVector(icon = Icons.Default.Check, size = 15.dp, tint = Color.Black)
        }

        SpacerWidth()
        Text(
            text = "Secure, private messaging",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun BottomSheet(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color.White, RoundedCornerShape(30.dp))
            .padding(horizontal = 18.dp, vertical = 25.dp) // Adjust padding as needed
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Add Account",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally) // Center the text horizontally
                    .padding(bottom = 16.dp) // Add space below the text
            )

            CustomOutlinedButton(
                text = "Login to Existing Account",
                selected = true,
                border = false,
                modifier = Modifier.fillMaxWidth()
            ) {
                navHostController.navigate(LOGIN_SCREEN) {
                    // Clear the back stack to ensure no previous screen is kept
                    popUpTo(LOGIN_SCREEN) { inclusive = true }
                }


            }
            SpacerHeight(8.dp)
            CustomOutlinedButton(
                text = "Create New Account",
                selected = false,
                border = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Log.d("BottomSheet", "Create New Account clicked")
                navHostController.navigate(SIGNUP_SCREEN)
            }

        }
    }
}

@Composable
fun CustomOutlinedButton(
    text: String,
    selected: Boolean,
    border: Boolean = false, // New parameter to determine if border should be applied
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        border = if (border) BorderStroke(1.dp, colorResource(id = R.color.black)) else null, // Apply border if needed
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = if (selected) Color.White else Color.Black,
            containerColor = if (selected) colorResource(id = R.color.bluebank) else Color.Transparent
        )
    ) {
        Text(text = text)
    }
}

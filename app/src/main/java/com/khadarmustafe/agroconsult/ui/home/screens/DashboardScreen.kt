package com.khadarmustafe.agroconsult.ui.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.khadarmustafe.agroconsult.R
import com.khadarmustafe.agroconsult.components.DashboardCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Hello Username",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { /* Handle main menu click */ }) {
                        Icon(Icons.Default.Menu, contentDescription = "Main Menu")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Welcome to AgroConsult", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DashboardCard("Image", navController, "image", color = Color.Blue, imageResId = R.drawable.ic_image)
                    DashboardCard("Video", navController, "video", color = Color.Red, imageResId =  R.drawable.ic_video)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    DashboardCard("Voice", navController, "voice", color = Color.Magenta, imageResId = R.drawable.ic_voice)
                    DashboardCard("Text", navController, "text", color = Color.DarkGray, imageResId = R.drawable.ic_text)
                }
            }
        }
    )
}
package com.khadarmustafe.agroconsult.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khadarmustafe.agroconsult.ui.home.screens.DashboardScreen
import com.khadarmustafe.agroconsult.ui.home.screens.ImageScreen
import com.khadarmustafe.agroconsult.ui.home.screens.TextScreen
import com.khadarmustafe.agroconsult.ui.home.screens.VideoScreen
import com.khadarmustafe.agroconsult.ui.home.screens.VoiceScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("image") { ImageScreen(title = "Image Screen", navController = navController) }
        composable("video") { VideoScreen(title = "Video Screen", navController = navController) }
        composable("voice") { VoiceScreen(title = "Voice Screen", navController = navController) }
        composable("text") { TextScreen(title = "Text Screen", navController = navController) }
    }
}
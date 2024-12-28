package com.khadarmustafe.agroconsult.navigation

import android.graphics.Bitmap
import androidx.camera.view.LifecycleCameraController
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.khadarmustafe.agroconsult.ui.auth.screens.AlreadyFarmerScreen
import com.khadarmustafe.agroconsult.ui.auth.screens.IntroScreen
import com.khadarmustafe.agroconsult.ui.auth.screens.NewFarmerScreen
import com.khadarmustafe.agroconsult.ui.auth.screens.RegionSelectionScreen
import com.khadarmustafe.agroconsult.ui.auth.screens.SignInScreen
import com.khadarmustafe.agroconsult.ui.auth.screens.SignUpScreen
import com.khadarmustafe.agroconsult.ui.auth.screens.UserTypeScreen
import com.khadarmustafe.agroconsult.ui.home.screens.CameraScreen
import com.khadarmustafe.agroconsult.ui.home.screens.DashboardScreen
import com.khadarmustafe.agroconsult.ui.home.screens.ImageScreen
import com.khadarmustafe.agroconsult.ui.home.screens.SendSuccessfullyScreen
import com.khadarmustafe.agroconsult.ui.home.screens.TextScreen
import com.khadarmustafe.agroconsult.ui.home.screens.VideoRecorderScreen
import com.khadarmustafe.agroconsult.ui.home.screens.VideoScreen
import com.khadarmustafe.agroconsult.ui.home.screens.VoiceScreen
import com.khadarmustafe.agroconsult.ui.home.viewmodel.CameraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
    controller: LifecycleCameraController,
    recordingVideo: (LifecycleCameraController) -> Unit,
    takePhoto: (LifecycleCameraController, (Bitmap) -> Unit) -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPlayRecording: () -> Unit,
    onStopPlayback: () -> Unit,
    onSendRecording: (String?) -> Unit,
    recordingTime: Long,
    recordingLength: Long
) {
    val navController = rememberNavController()
    val viewModel = viewModel<CameraViewModel>()
    val bitmaps by viewModel.bitmaps.collectAsState()
    val currentBitmap by viewModel.currentBitmap.collectAsState()

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    NavHost(navController, startDestination = "intro") {
        composable("intro") { IntroScreen(navController) }
        composable("sign_in") { SignInScreen(navController) }
        composable("sign_up") { SignUpScreen(navController) }
        composable("region_selection") { RegionSelectionScreen(navController) }
        composable("user_type") { UserTypeScreen(navController) }
        composable("new_farmer") { NewFarmerScreen(navController) }
        composable("already_farmer") { AlreadyFarmerScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("send_successfully") { SendSuccessfullyScreen() }
        composable("image") { ImageScreen(title = "Image Screen", navController = navController, bitmap = currentBitmap) }
        composable("video") { VideoScreen(title = "Video Screen", navController = navController) }
        composable("voice") {
            VoiceScreen(
                title = "Voice Screen",
                navController = navController,
                onStartRecording = onStartRecording,
                onStopRecording = onStopRecording,
                onPlayRecording = onPlayRecording,
                onStopPlayback = onStopPlayback,
                onSendRecording = onSendRecording,
                recordingTime = recordingTime,
                recordingLength = recordingLength
            )
        }
        composable("text") { TextScreen(title = "Text Screen", navController = navController) }

        composable("camera") {
            CameraScreen(
                scope = scope,
                scaffoldState = scaffoldState,
                controller = controller,
                takePhoto = {
                    takePhoto(
                        controller,
                        viewModel::onTakePhoto
                    )
                },
                navController = navController,
                bitmaps = bitmaps
            )
        }
        composable("videoRecorder") {
            var isRecording by remember { mutableStateOf(false) }
            VideoRecorderScreen(
                controller = controller,
                recording = isRecording,
                recordingVideo = {
                    isRecording = !isRecording
                    recordingVideo(controller)
                },
                navController = navController
            )
        }
    }
}
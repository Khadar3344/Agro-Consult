package com.khadarmustafe.agroconsult.ui.home.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.khadarmustafe.agroconsult.components.CustomDefaultBtn


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceScreen(
    title: String,
    navController: NavController,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onPlayRecording: () -> Unit,
    onStopPlayback: () -> Unit,
    onSendRecording: (String?) -> Unit,
    recordingTime: Long,
    recordingLength: Long
) {
    val context = LocalContext.current

    var isRecording by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentRecordingTime by remember { mutableLongStateOf(0L) }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            while (isRecording) {
                kotlinx.coroutines.delay(1000L) // Update every second
                currentRecordingTime += 1000L
            }
        } else {
            currentRecordingTime = 0L // Reset when recording stops
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }

                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isRecording) {
                    "Recording Time: ${formatTime(currentRecordingTime)}"
                } else {
                    "Recording Length: ${formatTime(recordingLength)}"
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )

            // Start/Stop Recording Button
            Button(
                onClick = {
                    if (isRecording) {
                        onStopRecording()
                    } else {
                        onStartRecording()
                    }
                    isRecording = !isRecording
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (isRecording) "Stop Recording" else "Start Recording")
            }

            // Play/Stop Playback Button
            Button(
                onClick = {
                    if (isPlaying) {
                        onStopPlayback()
                    } else {
                        onPlayRecording()
                    }
                    isPlaying = !isPlaying
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(if (isPlaying) "Stop Playback" else "Play Recording")
            }

            // Send Recording Button
            CustomDefaultBtn(
                shapeSize = 50f,
                btnText = "Send"
            ) {
                Toast.makeText(context, "Data Send successfully", Toast.LENGTH_SHORT).show()
                navController.navigate("send_successfully")
                onSendRecording(null)
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun formatTime(milliseconds: Long): String {
    val seconds = (milliseconds / 1000) % 60
    val minutes = (milliseconds / 1000) / 60
    return String.format("%02d:%02d", minutes, seconds)
}
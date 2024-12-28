package com.khadarmustafe.agroconsult

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Recording
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.video.AudioConfig
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.khadarmustafe.agroconsult.navigation.AppNavigation
import com.khadarmustafe.agroconsult.ui.home.viewmodel.CameraViewModel
import com.khadarmustafe.agroconsult.ui.theme.AgroConsultTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class MainActivity : ComponentActivity() {

    private var recording: Recording? = null

    private val cameraViewModel: CameraViewModel by viewModels()

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var audioFile: File? = null

    private var recordingStartTime = 0L
    private var recordingLength = 0L
    private val recordingTime = mutableLongStateOf(0L)

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, CAMERAX_PERMISSIONS, 0
            )
        }
        enableEdgeToEdge()
        setContent {
            AgroConsultTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE or
                            CameraController.VIDEO_CAPTURE
                        )
                    }
                }
                    AppNavigation(
                        controller = controller,
                        takePhoto = { _, onPhotoTaken->
                            takePhoto(controller, onPhotoTaken)
                        },
                        recordingVideo = {
                            recordVideo(controller)
                        },
                        onStartRecording = {
                            Toast.makeText(this, "Voice recording started", Toast.LENGTH_SHORT).show()
                            startRecording()
                        },
                        onStopRecording = {
                            Toast.makeText(this, "Voice recording stopped", Toast.LENGTH_SHORT).show()
                            stopRecording()
                        },
                        onPlayRecording = {
                            playRecording()
                        },
                        onStopPlayback = {
                            stopPlayback()
                        },
                        onSendRecording = {
                            sendRecording(it)
                        },
                        recordingTime = recordingTime.longValue,
                        recordingLength = recordingLength
                    )
                }

                /*val scope = rememberCoroutineScope()
                val scaffoldState = rememberBottomSheetScaffoldState()
                val controller = remember {
                    LifecycleCameraController(applicationContext).apply {
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE or
                            CameraController.VIDEO_CAPTURE
                        )
                    }
                }
                val viewModel = viewModel<CameraViewModel>()
                val bitmaps by viewModel.bitmaps.collectAsState()

                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 0.dp,
                    sheetContent = {
                        PhotoBottomSheetContent(
                            bitmaps = bitmaps,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                ) { padding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        CameraPreview(
                            controller = controller,
                            modifier = Modifier
                                .fillMaxSize()
                        )

                        IconButton(
                            onClick = {
                                controller.cameraSelector =
                                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    } else CameraSelector.DEFAULT_BACK_CAMERA
                            },
                            modifier = Modifier
                                .offset(16.dp, 16.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_cameraswitch),
                                contentDescription = "Switch camera"
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        scaffoldState.bottomSheetState.expand()
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_image),
                                    contentDescription = "Open gallery"
                                )
                            }
                            IconButton(
                                onClick = {
                                    takePhoto(
                                        controller = controller,
                                        onPhotoTaken = viewModel::onTakePhoto
                                    )
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_photo_camera),
                                    contentDescription = "Take photo"
                                )
                            }
                        }
                    }
                }*/
            }
        }
    }

    private fun startRecording() {
        audioFile = File.createTempFile("audio_", ".3gp", cacheDir)
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(audioFile?.absolutePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
                start()
                recordingStartTime = System.currentTimeMillis()
                lifecycleScope.launch(Dispatchers.Default) {
                    while (mediaRecorder != null) {
                        recordingTime.value = System.currentTimeMillis() - recordingStartTime
                        kotlinx.coroutines.delay(100)
                    }
                }
                Toast.makeText(this@MainActivity, "Recording started", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@MainActivity, "Recording failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun stopRecording() {
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
        recordingLength = System.currentTimeMillis() - recordingStartTime
        recordingTime.value = 0L
        Toast.makeText(this@MainActivity, "Recording saved: ${audioFile?.absolutePath}", Toast.LENGTH_SHORT).show()
    }


    private fun playRecording() {
        if (audioFile?.exists() == true) {
            mediaPlayer = MediaPlayer().apply {
                try {
                    setDataSource(audioFile?.absolutePath)
                    prepare()
                    start()
                    Toast.makeText(this@MainActivity, "Playing recording", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    Toast.makeText(this@MainActivity, "Playback failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun stopPlayback() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null
    }

    private fun sendRecording(filePath: String?) {
        if (filePath == null) {
            Toast.makeText(this, "No recording available to send", Toast.LENGTH_SHORT).show()
            return
        }
        // Logic to send the recording (e.g., share intent or upload to server)
        Toast.makeText(this, "Sending recording: $filePath", Toast.LENGTH_SHORT).show()
    }


    private fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    try {
                        // Convert ImageProxy to Bitmap
                        val originalBitmap = image.toBitmap()

                        // Validate the dimensions
                        if (originalBitmap == null ||originalBitmap.width == 0 || originalBitmap.height == 0) {
                            Log.e("Camera", "Invalid bitmap dimensions: width=${originalBitmap?.width}, height=${originalBitmap?.height}")
                            return
                        }
                        // Prepare rotation matrix
                        val matrix = Matrix().apply {
                            postRotate(image.imageInfo.rotationDegrees.toFloat())
                        }
                        // Safely calculate rotated bitmap
                        val rotatedBitmap = Bitmap.createBitmap(
                            originalBitmap,
                            0,
                            0,
                            originalBitmap.width,
                            originalBitmap.height,
                            matrix,
                            true
                        )

                        // Close the ImageProxy
                        image.close()

                        cameraViewModel.setSinglePhoto(rotatedBitmap)
                        // Return the rotated Bitmap
                        onPhotoTaken(rotatedBitmap)

                    } catch (e: Exception) {
                        Log.e("Camera", "Error processing image: ", e)
                    }

                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo: ", exception)
                }
            }
        )
    }

    @SuppressLint("MissingPermission")
    private fun recordVideo(controller: LifecycleCameraController) {
        if(recording != null) {
            recording?.stop()
            recording = null
            return
        }
        if(!hasRequiredPermissions()) {
            return
        }
        val outputFile = File(filesDir, "my-recording.mp4")
        recording = controller.startRecording(
            FileOutputOptions.Builder(outputFile).build(),
            AudioConfig.create(true),
            ContextCompat.getMainExecutor(applicationContext),
        ) { event ->
            when(event) {
                is VideoRecordEvent.Finalize -> {
                    if(event.hasError()) {
                        recording?.close()
                        recording = null
                        Toast.makeText(
                            applicationContext,
                            "Video capture failed",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Video capture succeeded",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    companion object {
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    }
}

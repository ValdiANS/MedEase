package com.myapplication.medease.ui.screens.camera

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.myapplication.medease.R
import com.myapplication.medease.ui.theme.MedEaseTheme
import com.myapplication.medease.ui.theme.montserratFamily
import com.myapplication.medease.utils.rotateBitmap
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    onPermissionDenied: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        Log.d("test123", uri.toString())
    }

    var lastCapturedPhoto: Bitmap? by rememberSaveable { mutableStateOf(null) }

    /*TODO("Send image to back-end")*/
    val photoCapturedHandler = { newPhoto: Bitmap ->
        lastCapturedPhoto = newPhoto
    }

    /*TODO("Open gallery")*/
    val openGalleryHandler = {
        launcher.launch("image/*")
    }

    CameraContent(
        hasPermission = cameraPermissionState.status.isGranted,
        onRequestPermission = cameraPermissionState::launchPermissionRequest,
        onPermissionDenied = onPermissionDenied,
        onNavigateBack = onNavigateBack,
        onPhotoCaptured = photoCapturedHandler,
        onOpenGallery = openGalleryHandler,

        lastCapturedPhoto = lastCapturedPhoto,

        modifier = modifier
    )
}

@Composable
fun CameraContent(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    onPermissionDenied: () -> Unit,
    onNavigateBack: () -> Unit,
    onPhotoCaptured: (Bitmap) -> Unit,
    onOpenGallery: () -> Unit,

    lastCapturedPhoto: Bitmap?,

    modifier: Modifier = Modifier,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        CameraContentGranted(
            hasPermission = hasPermission,
            onRequestPermission = onRequestPermission,
            onPermissionDenied = onPermissionDenied,
            onNavigateBack = onNavigateBack,
            onPhotoCaptured = onPhotoCaptured,
            onOpenGallery = onOpenGallery,
            modifier = modifier
        )

        if (lastCapturedPhoto != null) {
            LastPhotoPreview(
                modifier = Modifier.align(alignment = Alignment.BottomStart),
                lastCapturedPhoto = lastCapturedPhoto
            )
        }
    }
}

@Composable
fun CameraContentGranted(
    hasPermission: Boolean,
    onRequestPermission: () -> Unit,
    onPermissionDenied: () -> Unit,
    onNavigateBack: () -> Unit,
    onPhotoCaptured: (Bitmap) -> Unit,
    onOpenGallery: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController: LifecycleCameraController =
        remember { LifecycleCameraController(context) }

    var isTorchOn by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(!hasPermission) {
        onRequestPermission()
    }

    LaunchedEffect(isTorchOn) {
        cameraController.enableTorch(isTorchOn)
    }

    Box(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PreviewView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(Color.Black.toArgb())
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    scaleType = PreviewView.ScaleType.FILL_START
                }.also { previewView ->
                    previewView.controller = cameraController
                    cameraController.bindToLifecycle(lifecycleOwner)
                }
            }
        )

        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                val rectPath = Path().apply {
                    addRect(
                        Rect(
                            center = Offset(size.width / 2, 250.dp.toPx()),
                            radius = 150.dp.toPx()
                        )
                    )
                }

                clipPath(
                    rectPath,
                    clipOp = ClipOp.Difference
                ) {
                    drawRect(SolidColor(Color.Black.copy(alpha = 0.5f)))
                }
            }
        )

        Text(
            text = "Arahkan kemasan obat ke kamera",
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 68.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent
                ),
                onClick = onNavigateBack
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(
                topStart = 20.dp, topEnd = 20.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White
                    ),
                    onClick = onOpenGallery
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_grid),
                        contentDescription = null
                    )
                }

                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White
                    ),
                    onClick = {
                        capturePhoto(context, cameraController, onPhotoCaptured)
                    },
                    modifier = Modifier.size(64.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_camera),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                }

                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.White
                    ),
                    onClick = {
                        isTorchOn = !isTorchOn
                    }
                ) {
                    Icon(
                        painter = if (isTorchOn) painterResource(R.drawable.ic_flash_on) else painterResource(
                            R.drawable.ic_flash_off
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun CameraContentNotGranted(
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            Text(stringResource(R.string.permission_denied_message))

            Button(onClick = onRequestPermission) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Text(stringResource(R.string.grant_camera_permission))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CameraScreenPreview() {
    MedEaseTheme {
        CameraScreen(
            onPermissionDenied = {},
            onNavigateBack = {}
        )
    }
}

private fun capturePhoto(
    context: Context,
    cameraController: LifecycleCameraController,
    onPhotoCaptured: (Bitmap) -> Unit,
) {
    val mainExecutor: Executor = ContextCompat.getMainExecutor(context)
//    val mainExecutor: Executor = Executors.newSingleThreadExecutor()

    cameraController.takePicture(mainExecutor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            val correctedBitmap: Bitmap = image
                .toBitmap()
                .rotateBitmap(image.imageInfo.rotationDegrees)

            onPhotoCaptured(correctedBitmap)
            image.close()
        }

        override fun onError(exception: ImageCaptureException) {
            Log.e("CameraContent", "Error capturing image", exception)
        }
    })
}

@Composable
private fun LastPhotoPreview(
    modifier: Modifier = Modifier,
    lastCapturedPhoto: Bitmap,
) {

    val capturedPhoto: ImageBitmap =
        remember(lastCapturedPhoto.hashCode()) { lastCapturedPhoto.asImageBitmap() }

    Card(
        modifier = modifier
            .size(128.dp)
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Image(
            bitmap = capturedPhoto,
            contentDescription = "Last captured photo",
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
    }
}
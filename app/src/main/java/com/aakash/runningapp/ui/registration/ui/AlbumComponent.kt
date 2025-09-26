package com.aakash.runningapp.ui.registration.ui

import android.Manifest
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aakash.runningapp.R
import com.aakash.runningapp.ui.registration.AlbumIntent
import com.aakash.runningapp.ui.registration.RegistrationViewModel
import com.aakash.runningapp.ui.theme.LocalDimens
import com.aakash.runningapp.ui.theme.borderColor
import com.aakash.runningapp.ui.theme.inputBackground
import com.aakash.runningapp.ui.theme.onPrimary
import com.aakash.runningapp.ui.theme.surfaceDark
import java.io.File

@Composable
fun AlbumComponent(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel,
    onImageSelected: (Uri) -> Unit,
    ) {

    val albumState = viewModel.albumState.collectAsStateWithLifecycle()

    val currentContext = LocalContext.current

    val pickImageFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { url ->
            url?.let {
                viewModel.onReceiveIntent(
                    AlbumIntent.OnFinishPickingImageWith(
                        currentContext,
                        it
                    )
                )
                onImageSelected(it)
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isCaptured ->
            if (isCaptured) {
                viewModel.onReceiveIntent(AlbumIntent.OnImageSavedWith(currentContext))
            } else {
                viewModel.onReceiveIntent(AlbumIntent.OnImageSavingCancelled)
            }
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionGranted ->
            if (isPermissionGranted) {
                viewModel.onReceiveIntent(AlbumIntent.OnPermissionGrantedWith(currentContext))
            } else {
                viewModel.onReceiveIntent(AlbumIntent.OnPermissionDenied)
            }
        }

    LaunchedEffect(key1 = albumState.value.tempFileUrl) {
        albumState.value.tempFileUrl?.let {
            cameraLauncher.launch(it)
        }
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .border(
                    BorderStroke(1.dp, borderColor),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(inputBackground, shape = RoundedCornerShape(12.dp))
                .clickable {
                    val mediaRequest =
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    pickImageFromAlbumLauncher.launch(mediaRequest)
                },
            contentAlignment = Alignment.Center
        ) {
            if (albumState.value.selectedPicture != null) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    bitmap = albumState.value.selectedPicture!!,
                    contentDescription = "Selected Image",
                    contentScale = ContentScale.FillWidth
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(R.drawable.ic_upload),
                        contentDescription = "Upload",
                        tint = onPrimary,
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(LocalDimens.current.paddingRegular))
                    Text(
                        text = "Click to upload",
                        color = onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            },
            colors = ButtonDefaults.buttonColors(containerColor = surfaceDark),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_camera),
                contentDescription = "Camera",
                tint = onPrimary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(LocalDimens.current.paddingRegular))
            Text(
                stringResource(R.string.take_a_photo),
                color = onPrimary
            )
        }

    }
}
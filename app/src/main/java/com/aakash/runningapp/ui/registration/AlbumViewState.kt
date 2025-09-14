package com.aakash.runningapp.ui.registration

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

data class AlbumViewState(
    val tempFileUrl: Uri? = null,

    val selectedPicture: ImageBitmap? = null
)
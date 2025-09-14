package com.aakash.runningapp.ui.registration

import android.content.Context
import android.net.Uri

sealed class AlbumIntent {
    data class OnPermissionGrantedWith(val context: Context) : AlbumIntent()

    data object OnPermissionDenied : AlbumIntent()

    data class OnImageSavedWith(val context: Context) : AlbumIntent()

    data object OnImageSavingCancelled : AlbumIntent()

    data class OnFinishPickingImageWith(
        val context: Context,
        val imageUrl: Uri
    ) : AlbumIntent()
}
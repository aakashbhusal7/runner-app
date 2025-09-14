package com.aakash.runningapp.ui.registration

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.FileProvider
import androidx.lifecycle.viewModelScope
import com.aakash.runningapp.BuildConfig
import com.aakash.runningapp.ui.appcomponent.BaseViewModel
import com.aakash.runningapp.util.common.ErrorState
import com.aakash.runningapp.util.common.FormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.util.Locale

class RegistrationViewModel() : BaseViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationState())
    val registrationState = _registrationState.asStateFlow()

    private val _albumState = MutableStateFlow(AlbumViewState())
    val albumState = _albumState.asStateFlow()

    private val _validationErrorState =
        MutableStateFlow(RegistrationFieldValidationState(errorMessageState = ErrorMessageState()))
    val validationErrorState = _validationErrorState.asStateFlow()

    fun onReceiveIntent(intent: AlbumIntent) = viewModelScope.launch {
        when (intent) {
            is AlbumIntent.OnPermissionGrantedWith -> {
                val tempFile = File.createTempFile(
                    "temp_image_file_",
                    ".jpg",
                    intent.context.cacheDir
                )
                val uri = FileProvider.getUriForFile(
                    intent.context,
                    "${BuildConfig.APPLICATION_ID}.provider",
                    tempFile
                )
                _albumState.update { it.copy(tempFileUrl = uri) }
            }

            is AlbumIntent.OnPermissionDenied -> {

            }

            is AlbumIntent.OnFinishPickingImageWith -> {
                val bitmap =
                    intent.context.contentResolver.openInputStream(intent.imageUrl)?.use { input ->
                        val bytes = input.readBytes()
                        BitmapFactory.decodeByteArray(
                            bytes,
                            0,
                            bytes.size,
                            BitmapFactory.Options().apply {
                                inMutable = true
                            }
                        )
                    }
                if (bitmap != null) {
                    _albumState.update {
                        it.copy(
                            selectedPicture = bitmap.asImageBitmap(),
                            tempFileUrl = null
                        )
                    }
                }
            }

            is AlbumIntent.OnImageSavedWith -> {
                val tempImageUrl = _albumState.value.tempFileUrl
                if (tempImageUrl != null) {
                    val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source = ImageDecoder.createSource(
                            intent.context.contentResolver,
                            tempImageUrl
                        )
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        // Fallback for API 26 & 27
                        @Suppress("DEPRECATION")
                        MediaStore.Images.Media.getBitmap(
                            intent.context.contentResolver,
                            tempImageUrl
                        )
                    }
                    _albumState.update {
                        it.copy(
                            selectedPicture = bitmap?.asImageBitmap(),
                            tempFileUrl = null
                        )
                    }
                }
            }

            is AlbumIntent.OnImageSavingCancelled -> {
                _albumState.update {
                    it.copy(tempFileUrl = null)
                }
            }


        }
    }

    fun handleRegistrationUiEvent(registrationUiEvent: RegistrationUiEvent) {
        when (registrationUiEvent) {
            is RegistrationUiEvent.FirstNameChanged -> {
                _registrationState.value =
                    registrationState.value.copy(firstName = registrationUiEvent.firstName)
            }

            is RegistrationUiEvent.LastNameChanged -> {
                _registrationState.value =
                    registrationState.value.copy(lastName = registrationUiEvent.lastName)
            }

            is RegistrationUiEvent.DobChanged -> {
                val selectedDate = String.format(
                    Locale.getDefault(),
                    "%04d-%02d-%02d",
                    registrationUiEvent.year,
                    registrationUiEvent.month + 1,
                    registrationUiEvent.day
                )
                _registrationState.value =
                    registrationState.value.copy(dob = selectedDate)
            }

            is RegistrationUiEvent.ProfilePictureChanged -> {
                _registrationState.value =
                    registrationState.value.copy(profilePicture = registrationUiEvent.image)
            }

            is RegistrationUiEvent.OnSubmit -> checkValidation()
        }
    }

    private fun checkValidation() {
        val isFirstNameValid = FormValidator.validateNameFields(_registrationState.value.firstName)
        val isLastNameValid = FormValidator.validateNameFields(_registrationState.value.lastName)
        val isDobValid =
            FormValidator.validateNameFields(_registrationState.value.dob)
        _validationErrorState.value = validationErrorState.value.copy(
            firstNameValidationStatus = !isFirstNameValid
        )
        _validationErrorState.value = validationErrorState.value.copy(
            lastNameValidationStatus = !isLastNameValid
        )
        _validationErrorState.value = validationErrorState.value.copy(
            dobValidationStatus = !isDobValid
        )
        if (!isLastNameValid) validationErrorState.value.copy(
            errorMessageState = ErrorMessageState(lastNameErrorMessage = ErrorMessageType.LAST_NAME_ERROR)
        )
        if (!isFirstNameValid) validationErrorState.value.copy(
            errorMessageState = ErrorMessageState(firstNameErrorMessage = ErrorMessageType.FIRST_NAME_ERROR)
        )

        if (!isDobValid) validationErrorState.value.copy(
            errorMessageState = ErrorMessageState(dobErrorMessage = ErrorMessageType.DOB_ERROR)
        )
        if (isFirstNameValid && isLastNameValid && isDobValid) {
            proceedWithRegistration()
        }
    }

    private fun proceedWithRegistration() {
        _registrationState.value =
            _registrationState.value.copy(
                isLoading = true,
                isRegistrationSuccessful = false
            )
        _registrationState.value =
            _registrationState.value.copy(
                registrationErrorState = RegistrationErrorState(
                    serverErrorState = ErrorState(
                        hasError = false,
                        serverErrorMsg = ""
                    )
                )
            )
        performRegistrationWork()
    }

    private fun performRegistrationWork() {

    }
}
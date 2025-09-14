package com.aakash.runningapp.ui.registration

import com.aakash.runningapp.R
import com.aakash.runningapp.util.common.ErrorState

data class RegistrationState(
    val firstName: String = "",
    val lastName: String = "",
    val dob: String = "",
    val profilePicture: String = "",
    val isLoading: Boolean = false,
    val registrationFieldValidationState: RegistrationFieldValidationState = RegistrationFieldValidationState(),
    val registrationErrorState: RegistrationErrorState = RegistrationErrorState(),
    val isRegistrationSuccessful: Boolean = false,

    )

data class RegistrationFieldValidationState(
    val firstNameValidationStatus: Boolean = false,
    val lastNameValidationStatus: Boolean = false,
    val dobValidationStatus: Boolean = false,
    val errorMessageState: ErrorMessageState = ErrorMessageState()
)

data class ErrorMessageState(
    val firstNameErrorMessage: ErrorMessageType = ErrorMessageType.FIRST_NAME_ERROR,
    val lastNameErrorMessage: ErrorMessageType = ErrorMessageType.LAST_NAME_ERROR,
    val dobErrorMessage: ErrorMessageType = ErrorMessageType.DOB_ERROR
)

enum class ErrorMessageType(val message: Int) {
    FIRST_NAME_ERROR(R.string.first_name_error),
    LAST_NAME_ERROR(R.string.last_name_error),

    DOB_ERROR(R.string.dob_error)
}

data class RegistrationErrorState(
    val serverErrorState: ErrorState = ErrorState(),
    val invalidFirstNameErrorState: ErrorState = ErrorState(),
    val invalidLastNameErrorState: ErrorState = ErrorState(),
    val invalidProjectCodeErrorState: ErrorState = ErrorState()
)
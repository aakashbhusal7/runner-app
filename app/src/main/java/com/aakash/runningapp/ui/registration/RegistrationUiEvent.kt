package com.aakash.runningapp.ui.registration

sealed class RegistrationUiEvent {
    data class FirstNameChanged(val firstName: String) : RegistrationUiEvent()
    data class LastNameChanged(val lastName: String) : RegistrationUiEvent()
    data class DobChanged( val year:Int, val month:Int, val day:Int) : RegistrationUiEvent()
    data class ProfilePictureChanged(val image: String) : RegistrationUiEvent()

    object OnSubmit : RegistrationUiEvent()
}
package com.aakash.runningapp.util.common

object FormValidator {

    fun validateUserEmail(email: String): Boolean {
        return email.isNotEmpty() && isEmailValid(email)
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 6
    }

    fun validateConfirmationPassword(password: String, confirmationPassword: String): Boolean {
        return confirmationPassword.isNotEmpty() && password == confirmationPassword
    }

    fun validateNameFields(name: String): Boolean {
        return name.isNotEmpty() && name.length > 2
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
        return emailRegex.matches(email)
    }
}
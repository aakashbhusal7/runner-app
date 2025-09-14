package com.aakash.runningapp.util.exception

sealed interface ExceptionType {
    object Snack : ExceptionType

    object Toast : ExceptionType

    object Inline : ExceptionType

    object AlertDialog : ExceptionType

    object Redirect : ExceptionType

    object OnPage : ExceptionType
}
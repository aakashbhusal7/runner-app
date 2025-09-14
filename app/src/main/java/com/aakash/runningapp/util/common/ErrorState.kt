package com.aakash.runningapp.util.common

data class ErrorState(
    val hasError: Boolean = false, val serverErrorMsg: String = ""
)
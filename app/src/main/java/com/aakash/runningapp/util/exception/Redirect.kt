package com.aakash.runningapp.util.exception

data class Redirect(
    val redirect: RedirectType,
    val redirectObject: Any? = null,
)

enum class RedirectType
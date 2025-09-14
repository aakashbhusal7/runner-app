package com.aakash.runningapp.util.exception


sealed class RunnerAppException(
    open val code: Int,
    val type: ExceptionType,
    override val message: String?,
) : Throwable(message) {

    data class AlertException(
        override val code: Int,
        val alertDialog: AlertDialog,
    ) : RunnerAppException(code, ExceptionType.AlertDialog, null)

    data class InlineException(
        override val code: Int,
        val tags: List<Tag>,
    ) : RunnerAppException(code, ExceptionType.Inline, null)

    data class RedirectException(
        override val code: Int,
        val redirect: RedirectType,
    ) : RunnerAppException(code, ExceptionType.Redirect, null)

    data class SnackBarException(
        override val code: Int = -1,
        override val message: String,
    ) : RunnerAppException(code, ExceptionType.Snack, message)

    data class ToastException(
        override val code: Int,
        override val message: String,
    ) : RunnerAppException(code, ExceptionType.Toast, message)

    data class OnPageException(
        override val code: Int,
        override val message: String,
    ) : RunnerAppException(code, ExceptionType.OnPage, message)
}
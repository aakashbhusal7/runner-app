package com.aakash.runningapp.util.exception

data class Tag(
    val name: TagType,
    val message: String?,
)

enum class TagType
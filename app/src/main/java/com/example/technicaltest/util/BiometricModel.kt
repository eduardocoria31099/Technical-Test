package com.example.technicaltest.util

data class BiometricModel(
    val authenticationError: () -> Unit,
    val authenticationSucceeded: () -> Unit,
    val authenticationFailed: () -> Unit,
    val textTitle: String,
    val textSubTitle: String,
    val textNegativeButton: String,
)
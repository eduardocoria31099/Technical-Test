package com.example.technicaltest.util

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Context.startActivity(
    noinline extras: (Intent.() -> Unit)? = null
) {
    startActivity(Intent(this, T::class.java).apply { extras?.invoke(this) })
}
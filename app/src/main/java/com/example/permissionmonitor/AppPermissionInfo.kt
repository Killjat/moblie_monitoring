package com.example.permissionmonitor

import android.graphics.drawable.Drawable

data class AppPermissionInfo(
    val appName: String,
    val packageName: String,
    val permissions: List<String>,
    val icon: Drawable
)
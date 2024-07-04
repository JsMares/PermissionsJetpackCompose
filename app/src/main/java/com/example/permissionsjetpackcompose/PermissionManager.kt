package com.example.permissionsjetpackcompose

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class PermissionViewModel: ViewModel() {
    val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS
    )

    var permissionStatus by mutableStateOf(mapOf<String, String>())
        private set

    init {
        // Start permissions status
        permissionStatus = requiredPermissions.associateWith { "Permission not request" }
    }

    fun checkPermissions(context: Context): Map<String, Boolean> {
        return requiredPermissions.associateWith {  permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun updatePermissionStatus(permission: String, grated: Boolean) {
        permissionStatus = permissionStatus.toMutableMap().apply {
            this[permission] = if (grated) "Permission grated" else "Permission denied"
        }
    }
}
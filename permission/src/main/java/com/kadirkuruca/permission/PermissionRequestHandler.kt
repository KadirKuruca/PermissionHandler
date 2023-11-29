package com.kadirkuruca.permission

import android.content.Context
import androidx.core.content.ContextCompat

class PermissionRequestHandler {
    fun requestPermission(permission: String) {
        //TODO: Implement request permission
    }

    private fun controlPermissions(permission: String): Int {
        return ContextCompat.checkSelfPermission(getContext(), permission)
    }

    companion object {
        private var appContext: Context? = null
        fun initialize(context: Context): PermissionRequestHandler {
            appContext = context
            return PermissionRequestHandler()
        }
    }

    private fun getContext(): Context {
        return checkNotNull(appContext) { "PermissionRequestHandler not initialized." }
    }
}
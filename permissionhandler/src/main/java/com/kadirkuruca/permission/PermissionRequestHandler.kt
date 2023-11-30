package com.kadirkuruca.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PermissionRequestHandler {
    fun requestPermission(permission: String): Flow<PermissionResult> {
        val controlResult = controlPermissions(permission)
        val flow = callbackFlow {
            when (controlResult) {
                PackageManager.PERMISSION_GRANTED -> {
                    trySend(PermissionResult.Granted(permission))
                }
                PackageManager.PERMISSION_DENIED -> {
                    val callback: (PermissionRequester) -> Unit = { requester ->
                        requester.requestRuntimePermission(permission)
                        launch {
                            for (result in requester.resultChannel) {
                                trySend(result)
                            }
                            close()
                        }
                    }
                    startPermissionRequestActivity(callback)
                }
                else -> {
                    close()
                }
            }
            awaitClose {
                close()
            }
        }
        return flow
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

    private fun startPermissionRequestActivity(callback: (PermissionRequester) -> Unit) {
        PermissionHandlerActivity.permissionRequesterCallback = callback
        val intent = Intent(getContext(), PermissionHandlerActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        getContext().startActivity(intent)
    }
}
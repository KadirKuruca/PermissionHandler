package com.kadirkuruca.permission

import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.ThreadLocalRandom

internal class PermissionHandlerActivity: FragmentActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback,
    PermissionRequester {

    override val resultChannel = Channel<PermissionResult>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        permissionRequesterCallback.invoke(this)
    }

    override fun requestRuntimePermission(permission: String) {
        ActivityCompat.requestPermissions(this@PermissionHandlerActivity, arrayOf(permission), REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (permissions.isEmpty()) {
                resultChannel.trySend(PermissionResult.Cancelled)
            }
            else {
                val permission = permissions.first()
                if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                    resultChannel.trySend(PermissionResult.Granted(permission))
                }
                else if (grantResults[0] == PermissionChecker.PERMISSION_DENIED || grantResults[0] == PermissionChecker.PERMISSION_DENIED_APP_OP) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this@PermissionHandlerActivity, permission)) {
                        resultChannel.trySend(PermissionResult.Rejected.NeedsRationale(permission))
                    }
                    else {
                        resultChannel.trySend(PermissionResult.Rejected.RejectedPermanently(permission))
                    }
                }
            }
        }
    }

    companion object {
        internal lateinit var permissionRequesterCallback: (PermissionRequester) -> Unit
        private val REQUEST_CODE: Int = getRequestCode()
        private fun getRequestCode(): Int {
            return ThreadLocalRandom.current().nextInt(1500)
        }
    }
}
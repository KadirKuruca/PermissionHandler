package com.kadirkuruca.permission

import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.channels.Channel
import java.util.concurrent.ThreadLocalRandom

class PermissionHandlerActivity: FragmentActivity(),
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
        ActivityCompat.requestPermissions(this@PermissionHandlerActivity, arrayOf(permission), getRequestId())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        internal lateinit var permissionRequesterCallback: (PermissionRequester) -> Unit
    }
    private fun getRequestId(): Int {
        return ThreadLocalRandom.current().nextInt(1500)
    }
}
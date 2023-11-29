package com.kadirkuruca.permission

import android.os.Bundle
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

class PermissionHandlerActivity: FragmentActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
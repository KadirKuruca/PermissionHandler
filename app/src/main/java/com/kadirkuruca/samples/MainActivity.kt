package com.kadirkuruca.samples

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.kadirkuruca.permission.PermissionRequestHandler
import com.kadirkuruca.permission.PermissionResult
import com.kadirkuruca.samples.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this@MainActivity,
            MainViewModelFactory(PermissionRequestHandler.initialize(applicationContext))
        )[MainViewModel::class.java]

        requestPermissions()
        collectPermissionResults()
    }

    private fun collectPermissionResults() {
        lifecycleScope.launch {
            mainViewModel.permissionState.collect {
                when(it) {
                    is PermissionResult.Granted -> {
                        when (it.permission) {
                            Manifest.permission.CAMERA -> {
                                binding.textviewCameraPermission.text = GRANTED
                            }
                            Manifest.permission.ACCESS_FINE_LOCATION -> {
                                binding.textviewLocationPermission.text = GRANTED
                            }
                            Manifest.permission.POST_NOTIFICATIONS -> {
                                binding.textviewNotificationPermission.text = GRANTED
                            }
                            Manifest.permission.RECORD_AUDIO -> {
                                binding.textviewMicrophonePermission.text = GRANTED
                            }
                        }
                    }
                    is PermissionResult.Rejected.NeedsRationale -> {
                        when (it.permission) {
                            Manifest.permission.CAMERA -> {
                                binding.textviewCameraPermission.text = NEEDS_RATIONALE
                            }
                            Manifest.permission.ACCESS_FINE_LOCATION -> {
                                binding.textviewLocationPermission.text = NEEDS_RATIONALE
                            }
                            Manifest.permission.POST_NOTIFICATIONS -> {
                                binding.textviewNotificationPermission.text = NEEDS_RATIONALE
                            }
                            Manifest.permission.RECORD_AUDIO -> {
                                binding.textviewMicrophonePermission.text = NEEDS_RATIONALE
                            }
                        }
                    }
                    is PermissionResult.Rejected.RejectedPermanently -> {
                        when (it.permission) {
                            Manifest.permission.CAMERA -> {
                                binding.textviewCameraPermission.text = REJECTED_PERMANENTLY
                            }
                            Manifest.permission.ACCESS_FINE_LOCATION -> {
                                binding.textviewLocationPermission.text = REJECTED_PERMANENTLY
                            }
                            Manifest.permission.POST_NOTIFICATIONS -> {
                                binding.textviewNotificationPermission.text = REJECTED_PERMANENTLY
                            }
                            Manifest.permission.RECORD_AUDIO -> {
                                binding.textviewMicrophonePermission.text = REJECTED_PERMANENTLY
                            }
                        }
                    }
                    PermissionResult.Cancelled -> TODO()
                    PermissionResult.Initial -> Unit
                }
            }
        }
    }

    private fun requestPermissions() {
        binding.apply {
            buttonCameraPermission.setOnClickListener {
                mainViewModel.requestPermissions(Manifest.permission.CAMERA)
            }
            buttonLocationPermission.setOnClickListener {
                mainViewModel.requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            buttonNotificationPermission.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mainViewModel.requestPermissions(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            buttonMicrophonePermission.setOnClickListener {
                mainViewModel.requestPermissions(Manifest.permission.RECORD_AUDIO)
            }
        }
    }

    companion object {
        private const val GRANTED = "Granted"
        private const val REJECTED_PERMANENTLY = "Rejected Permanently"
        private const val NEEDS_RATIONALE = "Needs Rationale"
    }
}
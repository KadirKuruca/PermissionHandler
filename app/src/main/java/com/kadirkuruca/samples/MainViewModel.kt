package com.kadirkuruca.samples

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kadirkuruca.permission.PermissionRequestHandler
import com.kadirkuruca.permission.PermissionResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val permissionRequestHandler: PermissionRequestHandler) : ViewModel() {

	private var _permissionState = MutableStateFlow<PermissionResult>(PermissionResult.Initial)
	val permissionState: StateFlow<PermissionResult> = _permissionState

	fun requestPermissions(permission: String) {
		viewModelScope.launch {
			permissionRequestHandler.requestPermission(permission).collect {
				_permissionState.value = it
			}
		}
	}
}

class MainViewModelFactory(private val permissionRequestHandler: PermissionRequestHandler) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		return MainViewModel(permissionRequestHandler) as T
	}
}
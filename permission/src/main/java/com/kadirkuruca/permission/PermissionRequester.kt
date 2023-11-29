package com.kadirkuruca.permission

import kotlinx.coroutines.channels.Channel

internal interface PermissionRequester {
	fun requestRuntimePermission(permission: String)

	val resultChannel: Channel<PermissionResult>
}

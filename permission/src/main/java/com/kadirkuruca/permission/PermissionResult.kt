package com.kadirkuruca.permission

sealed class PermissionResult {
    data class Granted(val permission: String): PermissionResult()
    sealed class Rejected(val permission: String): PermissionResult()  {
        data class NeedsRationale(val permission: String): PermissionResult()
        data class RejectedPermanently(val permission: String): PermissionResult()
    }
    object Cancelled: PermissionResult()
    object Initial: PermissionResult()
}

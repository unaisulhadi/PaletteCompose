package com.hadi.myapplication.utils

sealed class RequestStatus {
    object Loading : RequestStatus()
    object Success : RequestStatus()
    object Error : RequestStatus()
}

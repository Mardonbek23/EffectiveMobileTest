package com.example.effectivemobiletest.utils

sealed class RequestState<out T> {
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T?) : RequestState<T>()
    data class Error(val message: String) : RequestState<Nothing>()
}
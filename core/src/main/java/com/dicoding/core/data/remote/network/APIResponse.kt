package com.dicoding.core.data.remote.network

sealed class APIResponse<out R> {
    data class Success<out T>(val data: T) : APIResponse<T>()
    data class Error(val errorMessage: String) : APIResponse<Nothing>()
    object Empty : APIResponse<Nothing>()
}
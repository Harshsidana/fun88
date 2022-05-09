package com.fun88.dummyapplication.utils

sealed class Resource<T>(
    data: T? = null,
    message: String = ""
) {
    class Success<T>(data: T?, message: String) : Resource<T>(data, message)
    class Error<T>(message: String) : Resource<T>(message = message)
    class Loading<T> : Resource<T>()

}


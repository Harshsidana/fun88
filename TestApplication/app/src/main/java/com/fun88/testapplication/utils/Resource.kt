package com.fun88.testapplication.utils

sealed class Resource<T>(
    data: T? = null,
    message: String = ""
) {

    class Success<T>(val data: T, val message: String) : Resource<T>(data, message)
    class Failure<T>(val message: String) : Resource<T>(message = message)
    class Loading<T>() : Resource<T>()
}

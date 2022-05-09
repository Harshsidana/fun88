package com.fun88.testapplication.local

interface DatabaseHelper {

    suspend fun getUserdata(): List<User>
    suspend fun setUserData(user: User)
}
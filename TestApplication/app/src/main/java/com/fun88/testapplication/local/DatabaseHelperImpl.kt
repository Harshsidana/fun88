package com.fun88.testapplication.local

class DatabaseHelperImpl(val userDataBase: UserDataBase): DatabaseHelper {
    override suspend fun getUserdata(): List<User> {
        return userDataBase.userDao().getAll()
    }

    override suspend fun setUserData(user: User) {
        return userDataBase.userDao().insertAll(user)
    }
}
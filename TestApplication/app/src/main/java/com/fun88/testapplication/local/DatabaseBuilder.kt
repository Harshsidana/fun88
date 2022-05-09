package com.fun88.testapplication.local

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: UserDataBase? = null

    fun getInstance(context: Context): UserDataBase {
        if (INSTANCE == null) {
            synchronized(UserDataBase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            UserDataBase::class.java,
            "mindorks-example-coroutines"
        ).build()

}
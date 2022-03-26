package com.sprinter.android.helpers

import com.sprinter.android.AppConfig
import com.sprinter.android.Constants
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import java.lang.Exception

object ConfigHandler {

    fun getAppConfig(): AppConfig? {
        val gson = Gson()
        return try {
            gson.fromJson(getString(Constants.APP_CONFIG), AppConfig::class.java)
        } catch (e: Exception) {
            null
        }
    }

    private fun getString(key: String): String {
        val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        return remoteConfig.getString(key)
    }


}
package com.fun88.testapplication.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fun88.testapplication.local.DatabaseHelper
import com.fun88.testapplication.repository.AppRepository
import java.lang.RuntimeException

class ViewModelFactory(val appRepository: AppRepository, val application: Application,val databaseHelper:DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(appRepository, application = application,databaseHelper) as T
        }
        throw RuntimeException("No ViewModel found")    }
}
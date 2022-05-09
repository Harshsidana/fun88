package com.fun88.dummyapplication.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fun88.dummyapplication.repository.AppRepository

class ViewModelProviderFactory(val app:Application, val repo:AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(app, repo) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}
package com.fun88.dummyapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fun88.dummyapplication.repository.AppRepository
import com.fun88.dummyapplication.utils.Resource
import com.hadi.retrofitmvvm.model.PicsResponse
import kotlinx.coroutines.launch

class HomeViewModel(app: Application, val repo: AppRepository) : AndroidViewModel(app) {

    private val _resultData: MutableLiveData<Resource<PicsResponse>> = MutableLiveData()
    fun getResultLiveData(): LiveData<Resource<PicsResponse>> {
        return _resultData
    }
    fun fetchData() {
        viewModelScope.launch {
            val result = repo.getData()
            Resource.Success(result.body(), result.message())
        }
    }


}
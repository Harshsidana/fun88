package com.fun88.testapplication.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.fun88.testapplication.local.DatabaseHelper
import com.fun88.testapplication.local.User
import com.fun88.testapplication.model.DataItems
import com.fun88.testapplication.model.PicsResponse
import com.fun88.testapplication.repository.AppRepository
import com.fun88.testapplication.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(val repository: AppRepository, application: Application,val databaseHelper: DatabaseHelper) :
    AndroidViewModel(application) {

     val _resultLiveData by lazy {
         MutableLiveData<Resource<PicsResponse>>()
     }


    fun getResultLiveData(): LiveData<Resource<PicsResponse>> {
        return _resultLiveData
    }

    val _resultUserData by lazy {
        MutableLiveData<User>()
    }


    fun getResultUserData(): LiveData<User> {
        return _resultUserData
    }

    fun getUserData()
    {
        viewModelScope.launch {
         val result=databaseHelper.getUserdata()
            _resultUserData.postValue(result.get(0))


        }
    }

    fun setUserData()
    {
        viewModelScope.launch {
            databaseHelper.setUserData(User(name =  "Harsh",email =  "harsh.sidana@gmail.com",avatar =  "avatar1"))
        }
    }

    fun getItemList() {
        _resultLiveData.value =
            Resource.Loading()
        viewModelScope.launch {
            val data = repository.getValues()
            if (data.isSuccessful) {
                _resultLiveData.value =
                    Resource.Success(data.body() ?: PicsResponse(), data.message())
            } else {
                _resultLiveData.value = Resource.Failure(data.message())
            }
        }
    }

}
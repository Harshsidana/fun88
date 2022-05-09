package com.fun88.testapplication

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fun88.testapplication.databinding.ActivityMainBinding
import com.fun88.testapplication.local.DatabaseBuilder
import com.fun88.testapplication.local.DatabaseHelper
import com.fun88.testapplication.local.DatabaseHelperImpl
import com.fun88.testapplication.repository.AppRepository
import com.fun88.testapplication.utils.Resource
import com.fun88.testapplication.viewmodels.HomeViewModel
import com.fun88.testapplication.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var homeViewModel: HomeViewModel
    lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleLarge)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val factory = ViewModelFactory(appRepository = AppRepository(), application,DatabaseHelperImpl(DatabaseBuilder.getInstance(this)))
        homeViewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        val adapter = MyAdapter()
        binding.rvItems.adapter = adapter
        homeViewModel.getItemList()
        homeViewModel.setUserData()
        homeViewModel.getUserData()
        homeViewModel.getResultUserData().observe(this) {
            Toast.makeText(this,it.email,Toast.LENGTH_LONG).show()
        }

        homeViewModel.getResultLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    showHideProgress(false)
                    adapter.setItems(it.data)
                }
                is Resource.Failure -> {
                    showHideProgress(false)
                }
                is Resource.Loading -> {
                    showHideProgress(true)
                }
            }
        }

    }

    private fun showHideProgress(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.INVISIBLE
        }
    }
}
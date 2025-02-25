package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.SharedPreferences

class CabangViewModelFactory(private val sharedPreferences: SharedPreferences) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return CabangViewModel(sharedPreferences) as T
    }
}
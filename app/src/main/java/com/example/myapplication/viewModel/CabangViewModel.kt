package com.example.myapplication.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CabangViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _cabang = MutableStateFlow(sharedPreferences.getCabang())
    val cabang: StateFlow<String> = _cabang

    fun setCabang(newCabang: String) {
        _cabang.value = newCabang
        sharedPreferences.saveCabang(newCabang)
    }

    fun getCabang(): String {
        return _cabang.value
    }
}

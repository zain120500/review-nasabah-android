package com.example.myapplication.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SharedPreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    private val CABANG_KEY = "cabang"

    fun saveCabang(cabang: String) {
        sharedPreferences.edit().putString(CABANG_KEY, cabang).apply() // atau .commit()
    }

    fun getCabang(): String {
        return sharedPreferences.getString(CABANG_KEY, "Default") ?: "Default"
    }
}

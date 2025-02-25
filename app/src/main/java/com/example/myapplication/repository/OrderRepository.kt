package com.example.myapplication.repository

import com.example.myapplication.data.ApiClient
import com.example.myapplication.data.ApiResponse
import retrofit2.Response

class OrderRepository {
    private val apiService = ApiClient.apiService

    suspend fun fetchOrders(): Response<ApiResponse> {
        return apiService.getOrders()
    }
}

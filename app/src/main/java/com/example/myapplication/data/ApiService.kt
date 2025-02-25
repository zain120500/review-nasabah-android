package com.example.myapplication.data

import com.example.myapplication.model.Order
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @GET("api/orderAll/")
    @Headers(
        "X-Api-Key: SECRET",
        "Content-Type: application/json"
    )
    suspend fun getOrders(): Response<ApiResponse>
}

data class ApiResponse(
    val code: Int,
    val status: String,
    val data: List<Order>
)



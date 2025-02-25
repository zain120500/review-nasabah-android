package com.example.myapplication.model

data class Order(
    val id: Int,
    val total: Int,
    val products: List<Product>
)

data class Product(
    val id: String,
    val name: String,
    val price: Int,
    val quantity: Int,
    val createAt: String,
    val updatedAt: String?
)



package com.example.myapplication.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.myapplication.model.Order
import com.example.myapplication.viewModel.OrderViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun OrderScreen(viewModel: OrderViewModel) {
    val orderList by viewModel.orderList.observeAsState(emptyList())
    val errorMessage by viewModel.error.observeAsState()
    val isLoading by viewModel.loading.observeAsState(false)

    when {
        isLoading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        }
        orderList.isNotEmpty() -> {
            LazyColumn {
                items(orderList) { order ->
                    OrderItem(order)
                }
            }
        }
        errorMessage != null -> {
            Text(
                text = "Error: $errorMessage",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxSize()
            )
        }
        else -> {
            Text(text = "No data available", modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Order ID: ${order.id}", style = MaterialTheme.typography.titleMedium)
        Text(text = "Total: ${order.total}", style = MaterialTheme.typography.titleMedium)
//        order.products.forEach { product ->
//            Text(text = "- ${product.name} (${product.quantity} x ${product.price})")
//        }
    }
}




package com.example.myapplication.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.SharedPreferences
import com.example.myapplication.viewModel.CabangViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    cabangViewModel: CabangViewModel
) {
    var clickCount by remember { mutableStateOf(0) }
    var showModal by remember { mutableStateOf(false) }
    var lastClickTime by remember { mutableStateOf(System.currentTimeMillis()) }

    val handleClick = {
        clickCount++
        lastClickTime = System.currentTimeMillis()
        if (clickCount >= 10) {
            showModal = true
        }
    }


    LaunchedEffect(lastClickTime) {
        delay(2000L)
        if (System.currentTimeMillis() - lastClickTime >= 2000L) {
            clickCount = 0
        }
    }

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    handleClick()
                }
            )
        },


        )

    if (showModal) {
        EditCabangModal(
            onDismiss = { showModal = false },
            onSubmit= {
                showModal = false
            },
            cabangViewModel = cabangViewModel
        )
    }
}

@Composable
fun EditCabangModal(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    cabangViewModel : CabangViewModel,
) {
    var inputTxt by remember { mutableStateOf("") }
    val cabang by cabangViewModel.cabang.collectAsState()
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Edit Nama Cabang ${cabang}") },
        text = {
            OutlinedTextField(
                value = inputTxt,
                onValueChange = { inputTxt = it },
                label = { Text("Nama Cabang") },
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    cabangViewModel.setCabang(inputTxt)
                    onSubmit()
                }
            ) {
                Text("Kirim")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                onClick = onDismiss
            ) {
                Text("Batal")
            }
        }
    )
}

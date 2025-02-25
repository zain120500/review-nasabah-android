package com.example.myapplication.ui

import FeedbackModal
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun ThankYouScreen(onTimeout: () -> Unit) {
    var countdown by remember { mutableStateOf(30) }
    LaunchedEffect(key1 = Unit) {
        while (countdown > 0) {
            delay(1000L)
            countdown -= 1
        }
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Terimakasih atas penilaiannya",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Kembali ke halaman utama dalam $countdown detik",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun FeedbackFlow(onNavigateBack: () -> Unit) {
    var showThankYou by remember { mutableStateOf(false) }

    if (showThankYou) {
        ThankYouScreen(onTimeout = onNavigateBack)
    } else {
        FeedbackModal(
            onDismiss = onNavigateBack,
            onSubmit = { showThankYou = true }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedbackFlow() {
    FeedbackFlow(onNavigateBack = {})
}
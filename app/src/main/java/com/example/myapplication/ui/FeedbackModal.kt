import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.myapplication.ui.ThankYouScreen

@Composable
fun FeedbackModal(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit
) {
    var feedbackText by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    AlertDialog(
        onDismissRequest = {},
        title = { Text("Yakin dengan penilaian Anda ?") },
        confirmButton = {
            Button(
                onClick = {
                    onSubmit()
                    keyboardController?.hide()
                }
            ) {
                Text("Kirim")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Batal")
            }
        }
    )
}

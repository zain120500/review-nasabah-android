package com.example.myapplication

import FeedbackModal
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.ui.MyTheme
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.SharedPreferences
import com.example.myapplication.ui.SplashScreen
import com.example.myapplication.repository.OrderRepository
import com.example.myapplication.ui.NavGraph
import com.example.myapplication.ui.ThankYouScreen
import com.example.myapplication.ui.TidakPuasScreen
import com.example.myapplication.ui.TopBar
import com.example.myapplication.viewModel.CabangViewModel
import com.example.myapplication.viewModel.OrderViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = OrderRepository()
        val viewModel = ViewModelProvider(
            this,
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return OrderViewModel(repository) as T
                }
            }
        )[OrderViewModel::class.java]

        setContent {
            val systemUiController: SystemUiController = rememberSystemUiController()
            val backgroundColor = MaterialTheme.colorScheme.background
            systemUiController.setSystemBarsColor(color = backgroundColor)

            val context = LocalContext.current
            val sharedPreferences = SharedPreferences(context)

            var cabangViewModel = CabangViewModel(sharedPreferences)

            val navController = rememberNavController()

//            MyTheme {
//                var showSplashScreen by remember { mutableStateOf(true) }
//
//                if (showSplashScreen) {
//                    SplashScreen(
//                        onNavigateToMain = {
//                            showSplashScreen = false
//                        }
//                    )
//                } else {
//                    MainContent(cabangViewModel = cabangViewModel)
//                }
//            }

            MyTheme {
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainContent(
                            cabangViewModel = cabangViewModel,
                            navController = navController
                        )
                    }
                    composable("tidak_puas") {
                        TidakPuasScreen(navController = navController) // Pastikan parameter diteruskan
                    }
                    composable("thank_you") {
                        ThankYouScreen(
                            onTimeout = {
                                navController.popBackStack("main", inclusive = false)
                            }
                        )
                    }
                }
            }
            viewModel.fetchOrders()
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(cabangViewModel: CabangViewModel, navController: NavController) {
    var showModal by remember { mutableStateOf(false) }
    var showThankYouScreen by remember { mutableStateOf(false) }

    val cabang by cabangViewModel.cabang.collectAsState()
val sharedPreferences = SharedPreferences(LocalContext.current)
    Scaffold(
        topBar = {
            TopBar(title = "CABANG : ${cabang}", cabangViewModel = cabangViewModel)
        }
    ) { paddingValues ->
        if (showThankYouScreen) {
            ThankYouScreen(
                onTimeout = {
                    showThankYouScreen = false
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding() + 16.dp
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bagaimana pelayanan kami?",
                    style = TextStyle(fontSize = 24.sp),
                    modifier = Modifier.padding(top = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    FeedbackOption(
                        imageResourceId = R.drawable.sad,
                        buttonText = "Tidak Puas",
                        buttonColor = MaterialTheme.colorScheme.error,
                        onClick = {
                            navController.navigate("tidak_puas")
                        }
                    )

                    FeedbackOption(
                        imageResourceId = R.drawable.smile,
                        buttonText = "Puas",
                        buttonColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                           navController.navigate("thank_you")
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (showModal) {
                    FeedbackModal(
                        onDismiss = { showModal = false },
                        onSubmit = {
                            showModal = false
                            showThankYouScreen = true
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun FeedbackOption(
    imageResourceId: Int,
    buttonText: String,
    buttonColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = androidx.compose.animation.core.spring()
    )

    if (isPressed) {
        LaunchedEffect(isPressed) {
            kotlinx.coroutines.delay(150)
            isPressed = false
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
        )

        Button(
            onClick = {
                isPressed = true
                onClick()
            },
            modifier = Modifier
                .width(200.dp)
                .scale(scale),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text(text = buttonText, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val context = LocalContext.current
    val sharedPreferences = SharedPreferences(context)
    val cabangViewModel = CabangViewModel(sharedPreferences)
    val navController = rememberNavController()

    MainContent(cabangViewModel, navController)
}

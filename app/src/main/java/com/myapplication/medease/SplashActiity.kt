package com.myapplication.medease

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.ui.screens.splash.SplashScreen
import com.myapplication.medease.ui.theme.MedEaseTheme
import kotlinx.coroutines.delay

class SplashActiity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedEaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val user = viewModel.getSession().observeAsState(
                        initial = UserModel(
                            id = "",
                            name = "",
                            email = "",
                            token = "",
                            isLogin = false,
                            isGuest = true
                        )
                    )
                    var isTimeout by rememberSaveable {
                        mutableStateOf(false)
                    }
                    val timer by rememberSaveable {
                        mutableStateOf(3)
                    }
                    if (isTimeout) {
                        if (user.value?.isLogin == true || user.value?.isGuest == true) {
                            startActivity(Intent(this@SplashActiity, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            })
                        } else {
                            startActivity(Intent(this@SplashActiity, AuthenticationActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            })
                        }
                    } else {
                        SplashScreen()
                        ShowSplash(timer = timer) {
                            isTimeout = true
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSplash(timer: Int,onTimeout: () -> Unit) {
    val currentOnTimeout by rememberUpdatedState(onTimeout)
    LaunchedEffect(true) {
        var currentTimer = timer
        while (currentTimer > 0) {
            delay(1000)
            currentTimer--
        }
        currentOnTimeout()
    }
}

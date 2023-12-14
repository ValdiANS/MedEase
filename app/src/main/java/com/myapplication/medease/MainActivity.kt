package com.myapplication.medease

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.ui.theme.MedEaseTheme

class MainActivity : ComponentActivity() {

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
                            token = "",
                            isLogin = false,
                            isGuest = true
                        )
                    )
                    if (user.value?.isLogin == true || user.value?.isGuest == true) {
                        MedEaseApp(
                            userModel = user.value,
                            onLogout = {
                                logout()
                            }
                        )
                    } else {
                        logout()
                    }
                }
            }
        }
    }

    private fun logout() {
        viewModel.onLogOut()
        startActivity(
            Intent(this, AuthenticationActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }
}


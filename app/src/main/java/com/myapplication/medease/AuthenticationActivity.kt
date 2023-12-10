package com.myapplication.medease

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.ui.navigation.Screen
import com.myapplication.medease.ui.screens.authentication.AuthenticationScreen
import com.myapplication.medease.ui.screens.welcome.WelcomeScreen
import com.myapplication.medease.ui.theme.MedEaseTheme

class AuthenticationActivity : ComponentActivity() {

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
                    WelcomeContent()
                }
            }
        }
    }

    @Composable
    fun WelcomeContent(
        modifier: Modifier = Modifier,
        navController: NavHostController = rememberNavController(),
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen {
                    navController.navigate(Screen.Authentication.route)
                }
            }
            composable(Screen.Authentication.route) {
                AuthenticationScreen(
                    onSignIn = {
                        startActivity(
                            Intent(this@AuthenticationActivity, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                        )
                    },
                    onSignInAsGuest = {
                        viewModel.saveSession(
                            UserModel(
                                "",
                                "",
                                "",
                                false,
                                true
                            )
                        )
                        startActivity(Intent(this@AuthenticationActivity, MainActivity::class.java))
                    }
                )
            }
        }
    }
}
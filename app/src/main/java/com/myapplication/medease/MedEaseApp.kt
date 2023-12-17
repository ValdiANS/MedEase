package com.myapplication.medease

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myapplication.medease.data.local.preference.UserModel
import com.myapplication.medease.ui.components.BottomBar
import com.myapplication.medease.ui.navigation.NavigationItem
import com.myapplication.medease.ui.navigation.Screen
import com.myapplication.medease.ui.screens.add_schedule.AddScheduleScreen
import com.myapplication.medease.ui.screens.camera.CameraScreen
import com.myapplication.medease.ui.screens.detail_medicine.DetailMedicineScreen
import com.myapplication.medease.ui.screens.home.HomeScreen
import com.myapplication.medease.ui.screens.locked.LockedScreen
import com.myapplication.medease.ui.screens.profile.ProfileScreen
import com.myapplication.medease.ui.screens.schedule.ScheduleScreen
import com.myapplication.medease.ui.theme.MedEaseTheme

@Composable
fun MedEaseApp(
    userModel: UserModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onLogout: () -> Unit,
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isUserLoggedIn = userModel.isLogin && !userModel.isGuest

    val checkIfRouteShowBottomNav: (String) -> Boolean = { route: String ->
        when (route) {
            Screen.Home.route -> true
            Screen.Schedule.route -> true
            Screen.Profile.route -> true
            else -> false
        }
    }

    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.menu_home),
            icon = Icons.Outlined.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = stringResource(R.string.menu_schedule),
            icon = Icons.Outlined.Notifications,
            screen = Screen.Schedule
        ),
        NavigationItem(
            title = stringResource(R.string.menu_profile),
            icon = Icons.Outlined.Person,
            screen = Screen.Profile
        ),
    )

    val lockedScreenSignInHandler = {
        val authenticationActivityIntent = Intent(context, AuthenticationActivity::class.java)
        context.startActivity(authenticationActivityIntent)
    }

    Scaffold(
        bottomBar = {
            if (checkIfRouteShowBottomNav(currentRoute.toString())) {
                BottomBar(
                    navigationItems = navigationItems,
                    navController = navController
                )
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    userModel = userModel,
                    onDetailClick = { medicineId: String ->
                        navController.navigate(Screen.DetailMedicine.createRoute(medicineId))
                    },
                    onNavigateToCamera = {
                        navController.navigate(Screen.Camera.route)
                    }
                )
            }

            composable(Screen.Schedule.route) {
                if (isUserLoggedIn) {
                    ScheduleScreen(
                        onNavigateToAddSchedule = {
                            navController.navigate(Screen.AddSchedule.route)
                        }
                    )
                } else {
                    LockedScreen(
                        onSignIn = lockedScreenSignInHandler
                    )
                }
            }

            composable(Screen.Profile.route) {
                if (isUserLoggedIn) {
                    ProfileScreen(onLogout = onLogout)
                } else {
                    LockedScreen(
                        onSignIn = lockedScreenSignInHandler
                    )
                }
            }

            composable(
                route = Screen.DetailMedicine.route,
                arguments = listOf(
                    navArgument(context.getString(R.string.medicine_id)) {
                        type = NavType.StringType
                    }
                )
            ) {
                val medicineId = it.arguments?.getString(stringResource(R.string.medicine_id)) ?: ""

                DetailMedicineScreen(
                    medicineId = medicineId,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onSetSchedule = { medicineName: String ->
                        navController.navigate(Screen.AddSchedule.createRoute(medicineName))
                    }
                )
            }

            composable(Screen.Camera.route) {
                CameraScreen(
                    onPermissionDenied = {
                        navController.navigateUp()
                    },
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }

            composable(
                route = Screen.AddSchedule.route,
                arguments = listOf(
                    navArgument(context.getString(R.string.medicine_name)) {
                        type = NavType.StringType
                    }
                )
            ) {
                val medicineName =
                    it.arguments?.getString(stringResource(R.string.medicine_name)) ?: ""

                AddScheduleScreen(
                    medicineName = if (medicineName == "{${stringResource(R.string.medicine_name)}}") "" else medicineName,
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedEaseAppPreview() {
    MedEaseTheme {
//        MedEaseApp(
//            onLogout = {}
//        )
    }
}
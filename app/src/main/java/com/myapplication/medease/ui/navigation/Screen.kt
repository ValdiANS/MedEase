package com.myapplication.medease.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object Schedule : Screen("schedule")

    object Profile : Screen("profile")

    object DetailMedicine : Screen("home/{medicineId}") {
        fun createRoute(medicineId: String) = "home/$medicineId"
    }

    object Camera : Screen("home/camera")

    object Welcome : Screen("welcome")
    object Authentication : Screen("welcome/authentication")

    data object AddSchedule : Screen("schedule/addSchedule")
}

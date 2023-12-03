package com.myapplication.medease.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.myapplication.medease.ui.navigation.NavigationItem

@Composable
fun BottomBar(
    navigationItems: List<NavigationItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // LAYOUT
    NavigationBar(
        modifier = modifier
    ) {
        navigationItems.map { item ->
            NavigationBarItem(
                label = { Text(item.title) },
                alwaysShowLabel = false,
                selected = currentRoute == item.screen.route,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        restoreState = true
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}
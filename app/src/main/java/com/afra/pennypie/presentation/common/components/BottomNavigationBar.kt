package com.afra.pennypie.presentation.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.afra.pennypie.R
import com.afra.pennypie.presentation.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        NavigationItem(
            route = Screen.Home.route,
            icon = Icons.Default.Home,
            labelResId = R.string.nav_home
        ),
        NavigationItem(
            route = Screen.Transactions.route,
            icon = Icons.AutoMirrored.Filled.List,
            labelResId = R.string.nav_transactions
        ),
        NavigationItem(
            route = Screen.Budgets.route,
            icon = Icons.Default.MoreVert,
            labelResId = R.string.nav_budgets
        ),
        NavigationItem(
            route = Screen.Reports.route,
            icon = Icons.Default.DateRange,
            labelResId = R.string.nav_reports
        )
    )

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = stringResource(item.labelResId)) },
                label = { Text(stringResource(item.labelResId)) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
} 
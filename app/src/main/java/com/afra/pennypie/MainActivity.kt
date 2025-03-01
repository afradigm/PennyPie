package com.afra.pennypie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.afra.pennypie.presentation.common.components.BottomNavigationBar
import com.afra.pennypie.presentation.home.HomeScreen
import com.afra.pennypie.presentation.navigation.Screen
import com.afra.pennypie.presentation.transactions.TransactionsScreen
import com.afra.pennypie.presentation.transactions.add.AddTransactionScreen
import com.afra.pennypie.ui.theme.PennyPieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PennyPieTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController = navController)
            }
            composable(Screen.Transactions.route) {
                TransactionsScreen(navController = navController)
            }
            composable(Screen.AddTransaction.route) {
                AddTransactionScreen(navController = navController)
            }
            composable(Screen.Accounts.route) {
                // TODO: Add AccountsScreen implementation
            }
            composable(Screen.AddAccount.route) {
                // TODO: Add AddAccountScreen implementation
            }
            composable(Screen.Budgets.route) {
                // TODO: Add BudgetsScreen implementation
            }
            composable(Screen.AddBudget.route) {
                // TODO: Add AddBudgetScreen implementation
            }
            composable(Screen.Reports.route) {
                // TODO: Add ReportsScreen implementation
            }
            composable(Screen.Categories.route) {
                // TODO: Add CategoriesScreen implementation
            }
            composable(Screen.AddCategory.route) {
                // TODO: Add AddCategoryScreen implementation
            }
        }
    }
}
package com.afra.pennypie.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Transactions : Screen("transactions")
    object AddTransaction : Screen("add_transaction")
    object Accounts : Screen("accounts")
    object AddAccount : Screen("add_account")
    object Budgets : Screen("budgets")
    object AddBudget : Screen("add_budget")
    object Reports : Screen("reports")
    object Categories : Screen("categories")
    object AddCategory : Screen("add_category")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
} 
package com.example.expensetracker.ui.navigation

sealed class Routes(val route: String) {
    // Simple screens are just objects
    object Dashboard : Routes("home_screen")
    object Expenses : Routes("expenses_screen")

    object AddEditExpense : Routes("add_edit_expense?expenseId={expenseId}"){
        const val ARG_EXPENSE_ID = "expenseId"
        fun createAddRoute() = "add_edit_expense?expenseId=-1"
        fun createEditRoute(id: Int) = "add_edit_expense?expenseId=$id"
    }
}
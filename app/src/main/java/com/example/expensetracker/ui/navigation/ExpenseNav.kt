package com.example.expensetracker.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.example.expensetracker.ui.feature.expenses.ExpenseViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensetracker.ui.feature.addEditExpense.AddEditExpenseScreen
import com.example.expensetracker.ui.feature.expenses.ExpensesScreen
import com.example.expensetracker.ui.feature.home.DashboardScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    expenseViewModel: ExpenseViewModel = hiltViewModel()
){
    NavHost(
        navController = navController,
        startDestination = Routes.Dashboard.route,
        modifier = modifier,
        // 1. ENTER: When going TO a new screen (Forward)
        // Slide in from the Right edge
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400) // 400ms duration
            )
        },
        // 2. EXIT: The screen being covered moves slightly to the Left
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400)
            )
        },
        // 3. POP ENTER: When coming BACK to a screen
        // The previous screen slides back in from the Left
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        },
        // 4. POP EXIT: When leaving the current screen to go BACK
        // The current screen slides away to the Right
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400)
            )
        }
    ) {
        composable(Routes.Dashboard.route){
            DashboardScreen(
                onAddExpenseClicked = {
                    navController.navigate(route = Routes.AddEditExpense.route)
                },
                onSeeAllClicked = {
                    navController.navigate(route = Routes.Expenses.route)
                }
            )
        }
        composable(Routes.Expenses.route){
            ExpensesScreen(
                onExpenseClicked = {navController.navigate(route = Routes.AddEditExpense.route)},
                onBackClicked = {navController.popBackStack()}
            )
        }
        composable(
            route = Routes.AddEditExpense.route,
            listOf(
                navArgument("expenseId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ){
            backStackEntry ->
            val expenseId = backStackEntry.arguments?.getInt(Routes.AddEditExpense.ARG_EXPENSE_ID) ?: -1
            val uiState by expenseViewModel.uiState.collectAsState()
            val existingExpense = if(expenseId != -1) uiState.expenses.find { it.id == expenseId } else null

            AddEditExpenseScreen(
                existingExpense = existingExpense,
                onSave = { amount, category, date, note ->
                    if(existingExpense == null) {
                        expenseViewModel.addExpense(amount, category, date, note)
                    } else{
                        expenseViewModel.updateExpense(existingExpense)
                    }
                    navController.popBackStack()

                },
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}
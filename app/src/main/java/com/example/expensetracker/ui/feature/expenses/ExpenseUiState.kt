package com.example.expensetracker.ui.feature.expenses

import com.example.expensetracker.data.local.CategoryTotal
import com.example.expensetracker.data.local.entity.ExpenseEntity

data class ExpenseUiState(
    val expenses: List<ExpenseEntity> = emptyList(),
    val totalSpent: Double = 0.0,
    val monthSpent: Double = 0.0,
    val categoryTotals: List<CategoryTotal> = emptyList()
)
package com.example.expensetracker.ui.feature.expenses

import com.example.expensetracker.data.local.CategoryTotal
import com.example.expensetracker.data.local.entity.ExpenseEntity

data class ExpenseUiState(
    val expenses: List<ExpenseEntity> = emptyList(),
    val dayTotal: Double = 0.0,
    val monthTotal: Double = 0.0,
    val categoryTotals: List<CategoryTotal> = emptyList()
)
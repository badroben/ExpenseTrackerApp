package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.local.CategoryTotal
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    suspend fun insertExpense(expense: ExpenseEntity)
    suspend fun deleteExpense(expense: ExpenseEntity)
    suspend fun updateExpense(expense: ExpenseEntity)
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
    fun getAmountSummary(): Flow<Double>
    fun getCategorySummary(): Flow<List<CategoryTotal>>
    fun getSummarySince(since: Long): Flow<Double>
}
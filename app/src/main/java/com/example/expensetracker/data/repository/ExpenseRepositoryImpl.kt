package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.CategoryTotal
import com.example.expensetracker.data.local.dao.ExpenseDao
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val dao: ExpenseDao
) : ExpenseRepository{
    override suspend fun insertExpense(expense: ExpenseEntity) {
        dao.insertExpense(expense)
    }

    override suspend fun deleteExpense(expense: ExpenseEntity) {
        dao.deleteExpense(expense)
    }

    override suspend fun updateExpense(expense: ExpenseEntity) {
        dao.updateExpense(expense)
    }

    override fun getAllExpenses(): Flow<List<ExpenseEntity>> = dao.getAllExpenses()

    override fun getAmountSummary(): Flow<Double> = dao.getAmountSummary()

    override fun getCategorySummary(): Flow<List<CategoryTotal>> = dao.getCategorySummary()

    override fun getMonthSummary(startOfMonth: Long): Flow<Double> = dao.getMonthSummary(startOfMonth)
}
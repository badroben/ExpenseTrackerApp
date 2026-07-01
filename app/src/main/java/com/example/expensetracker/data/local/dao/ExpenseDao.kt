package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.local.CategoryTotal
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.domain.model.ExpenseCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao{
    @Insert
    suspend fun insertExpense(expense: ExpenseEntity): Long

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT * from expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses")
    fun getAmountSummary(): Flow<Double>

    @Query("SELECT expenseCategory as category, SUM(amount) as total FROM expenses GROUP BY expenseCategory")
    fun getCategorySummary(): Flow<List<CategoryTotal>>

    @Query("SELECT COALESCE(SUM(amount), 0.0) FROM expenses WHERE date >= :startOfMonth")
    fun getMonthSummary(startOfMonth: Long): Flow<Double>
}
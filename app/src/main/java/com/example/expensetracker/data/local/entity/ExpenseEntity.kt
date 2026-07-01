package com.example.expensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expensetracker.domain.model.ExpenseCategory

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val expenseCategory: ExpenseCategory = ExpenseCategory.OTHER,
    val note: String? = null,
    val date: Long
    )
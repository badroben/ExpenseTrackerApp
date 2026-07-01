package com.example.expensetracker.ui.feature.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel(){
    private val startOfMonth: Long = getStartOfMonthMillis()

    val uiState: StateFlow<ExpenseUiState> = combine(
        repository.getAllExpenses(),
        repository.getAmountSummary(),
        repository.getMonthSummary(startOfMonth),
        repository.getCategorySummary()
    ) { expenses, total, month, categoryTotals ->
        ExpenseUiState(
            expenses = expenses,
            totalSpent = total,
            monthSpent = month,
            categoryTotals = categoryTotals
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExpenseUiState())

    fun addExpense(amount: Double, category: ExpenseCategory,  date: Long, note: String?){
        viewModelScope.launch {
            repository.insertExpense(
                ExpenseEntity(
                    amount = amount,
                    expenseCategory = category,
                    note = note,
                    date = date
                )
            )
        }
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }

    fun updateExpense(expense: ExpenseEntity){
        viewModelScope.launch{
            repository.updateExpense(expense)
        }
    }

    private fun getStartOfMonthMillis(): Long {
        val cal = Calendar.getInstance()
        cal.set(Calendar.DAY_OF_MONTH, 1)
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.timeInMillis
    }
}
package com.example.expensetracker.ui.feature.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.domain.model.ExpenseCategory

@Composable
fun ExpensesScreen(
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var amountText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        // Summary section
        Text("Total spent: £${uiState.totalSpent}")
        Text("This month: £${uiState.monthSpent}")
        Spacer(Modifier.height(8.dp))
        uiState.categoryTotals.forEach { ct ->
            Text("${ct.category.displayName}: £${ct.total}")
        }

        Spacer(Modifier.height(16.dp))

        // Minimal insert
        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                val amount = amountText.toDoubleOrNull() ?: 0.0
                viewModel.addExpense(
                    amount = amount,
                    category = ExpenseCategory.FOOD,   // hardcoded for now
                    note = "Test expense",
                    date = System.currentTimeMillis()
                )
                amountText = ""
            }
        ) {
            Text("Add Expense")
        }

        Spacer(Modifier.height(16.dp))

        // The list
        LazyColumn {
            items(uiState.expenses) { expense ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${expense.expenseCategory.displayName}: £${expense.amount}")
                    Text(expense.note ?: "")
                }
            }
        }
    }
}
package com.example.expensetracker.ui.feature.addEditExpense

import android.R.attr.fontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.ui.theme.Ink
import com.example.expensetracker.ui.theme.Mint
import com.example.expensetracker.ui.theme.Muted
import com.example.expensetracker.ui.theme.Paper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditExpenseScreen(
    onBackClicked: () -> Unit,
    onSave: (amount: Double, category: ExpenseCategory, date: Long, note: String?) -> Unit,
    existingExpense: ExpenseEntity?
) {

    var amountText by remember(existingExpense) { mutableStateOf(existingExpense?.amount?.toString() ?: "") }
    var note by remember(existingExpense) { mutableStateOf(existingExpense?.note ?: "") }
    var selectedCategory by remember(existingExpense) { mutableStateOf(existingExpense?.expenseCategory ?: ExpenseCategory.OTHER)}

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Mint,
        unfocusedBorderColor = Muted.copy(alpha = 0.5f),
        focusedLabelColor = Mint,
        cursorColor = Mint
    )

    Scaffold(
        containerColor = Paper,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if(existingExpense == null) "Add expense" else "Edit expense",
                        fontWeight = FontWeight.Bold
                    )
                        },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Paper,
                    titleContentColor = Ink,
                    navigationIconContentColor = Ink
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Paper)
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            Text("Amount", style = MaterialTheme.typography.labelLarge, color = Muted)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                prefix = { Text("£", fontWeight = FontWeight.Bold) },
                placeholder = { Text("0.00") },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(Modifier.height(24.dp))

            Text("Category", style = MaterialTheme.typography.labelLarge, color = Muted)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ExpenseCategory.entries.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category.displayName) },
                        leadingIcon = { Icon(category.icon, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Mint,
                            selectedLabelColor = Ink,
                            selectedLeadingIconColor = Ink
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Text("Note (optional)", style = MaterialTheme.typography.labelLarge, color = Muted)
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("What was it for?") },
                modifier = Modifier.fillMaxWidth(),
                colors = fieldColors
            )

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull() ?: 0.0
                    onSave(amount, selectedCategory, System.currentTimeMillis() , note.takeIf { it.isNotBlank()})
                },
                enabled = amountText.toDoubleOrNull() != null && amountText.toDoubleOrNull()!! > 0.0,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Mint, contentColor = Ink)
            ) {
                Text("Save expense", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}
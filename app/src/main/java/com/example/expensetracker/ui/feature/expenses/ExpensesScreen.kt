package com.example.expensetracker.ui.feature.expenses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.ui.theme.CardPaper
import com.example.expensetracker.ui.theme.Ink
import com.example.expensetracker.ui.theme.Mint
import com.example.expensetracker.ui.theme.MintDeep
import com.example.expensetracker.ui.theme.Muted
import com.example.expensetracker.ui.theme.Paper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpensesScreen(
    onBackClicked: () -> Unit,
    onExpenseClicked: (ExpenseEntity) -> Unit,
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val expenses = uiState.expenses

    Scaffold(
        containerColor = Paper,
        topBar = {
            TopAppBar(
                title = { Text("All expenses", fontWeight = FontWeight.Bold) },
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
        if (expenses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Paper)
                    .padding(innerPadding)
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.AutoMirrored.Rounded.ReceiptLong, contentDescription = null, tint = Muted, modifier = Modifier.size(56.dp))
                    Spacer(Modifier.height(14.dp))
                    Text("No expenses yet", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Ink)
                    Spacer(Modifier.height(6.dp))
                    Text("Add one from the overview screen to get started.", style = MaterialTheme.typography.bodyMedium, color = Muted)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Paper),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding() + 8.dp,
                    bottom = innerPadding.calculateBottomPadding() + 16.dp,
                    start = 20.dp,
                    end = 20.dp
                )
            ) {
                items(expenses.size) { index ->
                    ExpenseRow(
                        expense = expenses[index],
                        onEditClicked = {onExpenseClicked(expenses[index])},
                        onDeleteClicked = {viewModel.deleteExpense(expenses[index])}
                    )
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun ExpenseRow(
    expense: ExpenseEntity,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            containerColor = CardPaper,
            titleContentColor = Ink,
            textContentColor = Muted,
            title = { Text("Delete expense?") },
            text = { Text("This will permanently remove this expense. This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    onDeleteClicked()
                }) {
                    Text("Delete", color = Mint, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel", color = Ink)
                }
            }
        )
    }

    Surface(
        onClick = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = CardPaper,
        shadowElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ----- Always-visible summary row -----
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Mint.copy(alpha = 0.15f),
                    modifier = Modifier.size(44.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            expense.expenseCategory.icon,
                            contentDescription = null,
                            tint = MintDeep,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
                Spacer(Modifier.size(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        expense.expenseCategory.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Ink
                    )
                    Text(
                        formatDate(expense.date),
                        style = MaterialTheme.typography.bodySmall,
                        color = Muted
                    )
                }
                Text(
                    "£${"%.2f".format(expense.amount)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Ink
                )
                Spacer(Modifier.size(6.dp))
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Muted
                )
            }

            // ----- Expanded section: note + actions -----
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(14.dp))
                    HorizontalDivider(color = Muted.copy(alpha = 0.2f))
                    Spacer(Modifier.height(14.dp))

                    // Full note (or a muted placeholder if none)
                    Text(
                        text = "Note",
                        style = MaterialTheme.typography.labelMedium,
                        color = Muted
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = expense.note?.takeIf { it.isNotBlank() } ?: "No note added",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (expense.note.isNullOrBlank()) Muted else Ink
                    )

                    Spacer(Modifier.height(16.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onEditClicked,
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Ink)
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.size(6.dp))
                            Text("Edit")
                        }
                        OutlinedButton(
                            onClick = {showDeleteDialog = true},
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFC4453B))
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.size(6.dp))
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}


private fun formatDate(millis: Long): String {
    return SimpleDateFormat("d MMM", Locale.getDefault()).format(Date(millis))
}
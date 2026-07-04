package com.example.expensetracker.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.expensetracker.data.local.CategoryTotal
import com.example.expensetracker.domain.model.ExpenseCategory
import com.example.expensetracker.domain.model.SpendingPeriod
import com.example.expensetracker.ui.feature.expenses.ExpenseViewModel
import com.example.expensetracker.ui.theme.CardPaper
import com.example.expensetracker.ui.theme.Ink
import com.example.expensetracker.ui.theme.Mint
import com.example.expensetracker.ui.theme.MintDeep
import com.example.expensetracker.ui.theme.Muted
import com.example.expensetracker.ui.theme.Paper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onAddExpenseClicked: () -> Unit,
    onSeeAllClicked: () -> Unit,
    viewModel: ExpenseViewModel = hiltViewModel()
) {
    var period by remember { mutableStateOf(SpendingPeriod.MONTH)}
    val uiState by viewModel.uiState.collectAsState()
    val categoryTotals: List<CategoryTotal> = uiState.categoryTotals
    val displayedTotal = when (period){
        SpendingPeriod.DAY -> uiState.dayTotal
        SpendingPeriod.MONTH -> uiState.monthTotal
    }
    Scaffold(
        containerColor = Paper,
        topBar = {
            TopAppBar(
                title = { Text("Overview", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Paper,
                    titleContentColor = Ink
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddExpenseClicked,
                containerColor = Mint,
                contentColor = Ink,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add expense", fontWeight = FontWeight.SemiBold) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Paper),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = innerPadding.calculateBottomPadding() + 96.dp,
                start = 20.dp,
                end = 20.dp
            )
        ) {
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    color = Ink
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                when (period) {
                                    SpendingPeriod.DAY -> "Spent today"
                                    SpendingPeriod.MONTH -> "Spent this month"
                                },
                                style = MaterialTheme.typography.labelLarge,
                                color = Muted,
                                modifier = Modifier.weight(1f)
                            )

                            SingleChoiceSegmentedButtonRow {
                                SegmentedButton(
                                    selected = period == SpendingPeriod.DAY,
                                    onClick = { period = SpendingPeriod.DAY },
                                    shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                                    colors = SegmentedButtonDefaults.colors(
                                        activeContainerColor = Mint,
                                        activeContentColor = Ink,
                                        inactiveContainerColor = Color.Transparent,
                                        inactiveContentColor = Muted,
                                        activeBorderColor = Mint,
                                        inactiveBorderColor = Muted.copy(alpha = 0.4f)
                                    )
                                ) { Text("Day") }

                                SegmentedButton(
                                    selected = period == SpendingPeriod.MONTH,
                                    onClick = { period = SpendingPeriod.MONTH },
                                    shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                                    colors = SegmentedButtonDefaults.colors(
                                        activeContainerColor = Mint,
                                        activeContentColor = Ink,
                                        inactiveContainerColor = Color.Transparent,
                                        inactiveContentColor = Muted,
                                        activeBorderColor = Mint,
                                        inactiveBorderColor = Muted.copy(alpha = 0.4f)
                                    )
                                ) { Text("Month") }
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        Text(
                            "£${"%.2f".format(displayedTotal)}",
                            fontSize = 44.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(Modifier.height(20.dp))

                        Box(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                                Text(
                                    "All time",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = Muted,
                                    modifier = Modifier.align(Alignment.End)
                                )
                                Text(
                                    "£${"%.2f".format(uiState.monthTotal)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Mint,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "By category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Ink
                    )
                    Text(
                        "See all",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MintDeep,
                        modifier = Modifier.clickable { onSeeAllClicked() }
                    )
                }
                Spacer(Modifier.height(12.dp))
            }

            if (categoryTotals.isEmpty()) {
                item {
                    EmptyHint(
                        icon = Icons.AutoMirrored.Rounded.ReceiptLong,
                        text = "No spending yet. Add your first expense to see the breakdown."
                    )
                }
            } else {
                items(categoryTotals.size) { index ->
                    val (category, total) = categoryTotals[index]
                    CategoryRow(category = category, total = total)
                    Spacer(Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun CategoryRow(category: ExpenseCategory, total: Double) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = CardPaper,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Mint.copy(alpha = 0.15f),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(category.icon, contentDescription = null, tint = MintDeep, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(Modifier.size(14.dp))
            Text(
                category.displayName,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Ink
            )
            Text(
                "£${"%.2f".format(total)}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
        }
    }
}

@Composable
private fun EmptyHint(icon: ImageVector, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = null, tint = Muted, modifier = Modifier.size(48.dp))
        Spacer(Modifier.height(12.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = Muted,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
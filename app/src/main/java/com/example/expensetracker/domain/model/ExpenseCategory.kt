package com.example.expensetracker.domain.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Restaurant
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class ExpenseCategory (
    val displayName: String,
    val icon: ImageVector
    ) {
    FOOD("Food & Dining", Icons.Rounded.Restaurant),
    TRANSPORT("Transportation", Icons.Rounded.DirectionsCar),
    BILLS("Bills", Icons.Rounded.Bolt),
    ENTERTAINMENT("Entertainment", Icons.Rounded.Movie),
    GROCERIES("Groceries", Icons.Rounded.ShoppingCart),
    SHOPPING("Shopping", Icons.Rounded.ShoppingBag),
    FITNESS("Fitness", Icons.Rounded.FitnessCenter),
    INVESTMENTS("Investments", Icons.Rounded.AttachMoney),
    OTHER("Other", Icons.Rounded.MoreHoriz)
}
package com.example.recipeapp.ui.navigation

sealed class Routes(val route: String) {
    // Simple screens are just objects
    object Home : Routes("home_screen")
    object Recipes : Routes("recipes_screen")
    object Favourites : Routes("favourites_screen")

    // Complex screens (with args) use helper functions
    object RecipeDetails : Routes("recipe_details/{recipeId}") {
        const val ARG_RECIPE_ID = "recipeId"

        // Helper to build the route with the ID
        fun createRoute(id: Int) = "recipe_details/$id"
    }

    object AddEditRecipe : Routes("add_edit_recipe?recipeId={recipeId}"){
        const val ARG_RECIPE_ID = "recipeId"
        fun createAddRoute() = "add_edit_recipe?recipeId=-1"
        fun createEditRoute(id: Int) = "add_edit_recipe?recipeId=$id"
    }
}
package com.example.recipeapproom

import androidx.room.*

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe WHERE id=:id")
    fun getRecipeById(id:Int): Recipe

    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): List<Recipe>

    @Insert
    fun addRecipe(recipe: Recipe)

    @Update
    fun updateRecipe(recipe: Recipe)

    @Delete
    fun deleteRecipe(recipe: Recipe)
}
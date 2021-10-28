package com.example.recipeapproom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipeDatabase:RoomDatabase() {

    companion object {
        var INSTANCE:RecipeDatabase?=null

        fun getInstance(context: Context):RecipeDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null) return tempInstance as RecipeDatabase

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "Recipes"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

    abstract fun getRecipeDao():RecipeDao
}
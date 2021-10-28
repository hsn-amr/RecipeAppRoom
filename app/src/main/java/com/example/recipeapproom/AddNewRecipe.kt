package com.example.recipeapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewRecipe : AppCompatActivity() {

    private val recipeDatabase by lazy { RecipeDatabase.getInstance(applicationContext) }

    lateinit var recipeTitle: EditText
    lateinit var recipeAuthor: EditText
    lateinit var recipeIngredients: EditText
    lateinit var recipeInstructions: EditText

    lateinit var addButton: Button
    lateinit var viewButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_recipe)

        recipeTitle = findViewById(R.id.etTitle)
        recipeAuthor = findViewById(R.id.etAuthor)
        recipeIngredients = findViewById(R.id.etIngredients)
        recipeInstructions = findViewById(R.id.etInstructions)

        addButton = findViewById(R.id.btnAdd)
        viewButton = findViewById(R.id.btnView)


        addButton.setOnClickListener {
            if(recipeTitle.text.isNotEmpty() && recipeAuthor.text.isNotEmpty()
                && recipeIngredients.text.isNotEmpty() && recipeInstructions.text.isNotEmpty()){
                val title = recipeTitle.text.toString()
                val author = recipeAuthor.text.toString()
                val ingredients = recipeIngredients.text.toString()
                val instructions = recipeInstructions.text.toString()

                val recipe = Recipe(0,title, author, ingredients, instructions)
                CoroutineScope(Dispatchers.IO).launch {
                    recipeDatabase.getRecipeDao().addRecipe(recipe)
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@AddNewRecipe, "Added", Toast.LENGTH_LONG).show()
                        recipeTitle.text.clear()
                        recipeAuthor.text.clear()
                        recipeIngredients.text.clear()
                        recipeInstructions.text.clear()
                    }
                }
            }else{
                Toast.makeText(this, "Fill in the blanks please", Toast.LENGTH_LONG).show()
            }
        }

        viewButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
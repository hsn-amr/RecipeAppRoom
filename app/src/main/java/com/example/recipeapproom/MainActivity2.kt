package com.example.recipeapproom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity2 : AppCompatActivity() {

    private val recipeDatabase by lazy { RecipeDatabase.getInstance(applicationContext) }

    lateinit var recipeTitle: EditText
    lateinit var recipeAuthor: EditText
    lateinit var recipeIngredients: EditText
    lateinit var recipeInstructions: EditText

    lateinit var updateButton: Button
    lateinit var viewButton: Button
    lateinit var deleteButton: Button
    var id = 0
    var recipe: Recipe?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val extra = intent.extras
        if(extra!=null){
            id = extra.getInt("id")
        }

        recipeTitle = findViewById(R.id.etTitle)
        recipeAuthor = findViewById(R.id.etAuthor)
        recipeIngredients = findViewById(R.id.etIngredients)
        recipeInstructions = findViewById(R.id.etInstructions)

        CoroutineScope(Dispatchers.IO).launch {
            recipe = recipeDatabase.getRecipeDao().getRecipeById(id)
            withContext(Dispatchers.Main){
                recipeTitle.setText(recipe!!.title)
                recipeAuthor.setText(recipe!!.author)
                recipeIngredients.setText(recipe!!.ingredients)
                recipeInstructions.setText(recipe!!.instructions)
            }
        }

        updateButton = findViewById(R.id.btnUpdate)
        viewButton = findViewById(R.id.btnView)
        deleteButton = findViewById(R.id.btnDelete)

        updateButton.setOnClickListener {
            val title = recipeTitle.text.toString()
            val author = recipeAuthor.text.toString()
            val ingredients = recipeIngredients.text.toString()
            val instructions = recipeInstructions.text.toString()

            val recipe = Recipe(id,title, author, ingredients, instructions)
            CoroutineScope(Dispatchers.IO).launch {
                recipeDatabase.getRecipeDao().updateRecipe(recipe)
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity2, "Updated", Toast.LENGTH_LONG).show()
                }
            }
        }

        deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                recipeDatabase.getRecipeDao().deleteRecipe(recipe!!)
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity2, "Deleted", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@MainActivity2, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        viewButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
package com.example.recipeapproom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val recipeDatabase by lazy { RecipeDatabase.getInstance(applicationContext) }

    lateinit var rvMain: RecyclerView
    lateinit var adapter: RVRecipe
    var recipes = listOf<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rvMain)

        CoroutineScope(Dispatchers.IO).launch {
            recipes = recipeDatabase.getRecipeDao().getAllRecipes()
            withContext(Dispatchers.Main){
                adapter = RVRecipe(recipes,this@MainActivity)

                rvMain.adapter = adapter
                rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this,AddNewRecipe::class.java))
        return super.onOptionsItemSelected(item)
    }
}
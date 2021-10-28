package com.example.recipeapproom

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class RVRecipe(var recipes: List<Recipe>, val context: Context)
    :RecyclerView.Adapter<RVRecipe.ItemViewHolder>(){
    class ItemViewHolder(view:View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val recipe = recipes[position]

        holder.itemView.apply {
            tvTitle.text = recipe.title
            tvAuthor.text = recipe.author
            tvIngredients.text = recipe.ingredients
            tvInstructions.text = recipe.instructions

            llHolder.isVisible = false

            btnMore.setOnClickListener {
                llHolder.isVisible = !llHolder.isVisible
            }

            btnSetting.setOnClickListener {
                val intent = Intent(context, MainActivity2::class.java)
                intent.putExtra("id",recipe.id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = recipes.size

}
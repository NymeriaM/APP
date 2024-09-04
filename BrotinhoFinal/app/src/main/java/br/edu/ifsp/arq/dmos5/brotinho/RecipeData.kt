package br.edu.ifsp.arq.dmos5.brotinho

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.edu.ifsp.arq.dmos5.brotinho.model.Recipe
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.RecipeViewModel
import com.google.firebase.database.*

class RecipeData : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    // Elementos da interface
    private lateinit var recipeImageView: ImageView
    private lateinit var recipeTitleTextView: TextView
    private lateinit var recipeIngredientsTextView: TextView
    private lateinit var recipePreparationTextView: TextView

    private val recipeViewModel by viewModels<RecipeViewModel>()

    lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_data) // Altere para o nome correto do layout

        // Inicializar elementos da interface
        recipeImageView = findViewById(R.id.recipe_image_detail)
        recipeTitleTextView = findViewById(R.id.recipe_title_detail)
        recipeIngredientsTextView = findViewById(R.id.recipe_ingredients_detail)
        recipePreparationTextView = findViewById(R.id.recipe_preparation_detail)

        // Obter o ID da receita passada pelo Intent
        val recipeId = intent.getStringExtra("RECIPE_ID")

        if (recipeId != null) {
            Log.d("testando", recipeId)
            loadRecipe(recipeId)
        }
    }


    private fun loadRecipe(nome: String) {
        recipeViewModel.load(nome).observe(this, Observer {
            if (it != null) {
                recipe = it
                recipeTitleTextView.setText(it.nome)
                recipePreparationTextView.setText(it.modoPreparo)

                recipe.foto?.let { it1 -> {
                    recipeViewModel.loadRecipe(it1, recipeImageView)
                    Log.d("testando", it1)
                } }

            }
        })
    }
}

package br.edu.ifsp.arq.dmos5.brotinho

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.arq.dmos5.brotinho.adapter.RecipeAdapter
import br.edu.ifsp.arq.dmos5.brotinho.model.Recipe
import br.edu.ifsp.arq.dmos5.brotinho.model.User
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.RecipeViewModel
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.UserViewModel
import com.google.firebase.database.*

class RecipeListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recipeListLayout: LinearLayout
    lateinit var adapter: RecipeAdapter
    lateinit var activitysList: ListView
    private val recipeViewModel by viewModels<RecipeViewModel>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        // Inicializar elementos da interface
        val topic = intent.getStringExtra("TOPIC").toString()
        val textViewTopicTitle: TextView = findViewById(R.id.textViewTopicTitle)
        recipeListLayout = findViewById(R.id.recipeListLayout)

        // Definir o título da tela com base na categoria
        when (topic) {
            "gluten_free" -> textViewTopicTitle.text = "Receitas Sem Glúten"
            "lactose_free" -> textViewTopicTitle.text = "Receitas Sem Lactose"
            "vegan" -> textViewTopicTitle.text = "Receitas Veganas"
            "salad" -> textViewTopicTitle.text = "Receitas Saladas"
            "dessert" -> textViewTopicTitle.text = "Receitas De Sobremesas"
        }

        val recipeListView = findViewById<ListView>(R.id.recipe_list)

        recipeListView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, RecipeData::class.java)

            val selectedRecipe = parent.getItemAtPosition(position) as Recipe
            intent.putExtra("RECIPE_ID", selectedRecipe.nome)

            startActivity(intent)
        }

        setAdapter(topic)


    }

    private fun setAdapter(topic:String) {
        recipeViewModel.allRecipes(topic).observe(this, Observer{
            adapter = RecipeAdapter(
                this@RecipeListActivity,
                android.R.layout.simple_list_item_1,
                it
            )
            activitysList = findViewById<View>(R.id.recipe_list) as ListView
            activitysList.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }



}

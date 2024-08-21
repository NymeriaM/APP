package br.edu.ifsp.arq.dmos5.brotinho

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class RecipeListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var recipeListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        // Inicializar elementos da interface
        val topic = intent.getStringExtra("TOPIC")
        val textViewTopicTitle: TextView = findViewById(R.id.textViewTopicTitle)
        recipeListLayout = findViewById(R.id.recipeListLayout)

        // Definir o título da tela com base na categoria
        when (topic) {
            "gluten_free" -> textViewTopicTitle.text = "Receitas Sem Glúten"
            "lactose_free" -> textViewTopicTitle.text = "Receitas Sem Lactose"
            // Outros tópicos podem ser tratados aqui
        }
    }

}

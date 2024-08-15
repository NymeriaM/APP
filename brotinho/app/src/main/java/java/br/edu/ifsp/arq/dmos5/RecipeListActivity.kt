package java.br.edu.ifsp.arq.dmos5

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        val topic = intent.getStringExtra("TOPIC")

        val textViewTopicTitle: TextView = findViewById(R.id.textViewTopicTitle)
        when (topic) {
            "gluten_free" -> textViewTopicTitle.text = "Receitas Sem Glúten"
            "lactose_free" -> textViewTopicTitle.text = "Receitas Sem Lactose"

        }
    }
}
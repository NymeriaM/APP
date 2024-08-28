package br.edu.ifsp.arq.dmos5.brotinho.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import br.edu.ifsp.arq.dmos5.brotinho.R
import br.edu.ifsp.arq.dmos5.brotinho.model.Recipe

class RecipeAdapter(
    context: Context,
    textViewResourceId: Int,
    private val recipes: List<Recipe>
) : ArrayAdapter<Recipe>(context, textViewResourceId, recipes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v: View? = convertView
        if (v == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            v = inflater.inflate(R.layout.activity_list_layout, parent, false)
        }

        val recipe = recipes[position] ?: return v!!

        val recipeImage = v?.findViewById<ImageView>(R.id.recipe_image)
        val recipeTitle = v?.findViewById<TextView>(R.id.recipe_title)

        recipeTitle?.text = recipe.nome
        //recipeTitle?.text = recipe.nome ?: "Receita Desconhecida"

        recipeImage?.setImageResource(R.drawable.freegluten)

        return v!!
    }
}

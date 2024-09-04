package br.edu.ifsp.arq.dmos5.brotinho.viewmodel

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.arq.dmos5.brotinho.model.Recipe
import br.edu.ifsp.arq.dmos5.brotinho.model.User
import br.edu.ifsp.arq.dmos5.brotinho.repository.RecipesRepository

class RecipeViewModel (application: Application) : AndroidViewModel(application) {

    private val recipesRepository = RecipesRepository(getApplication())

    fun allRecipes(category: String?): LiveData<List<Recipe>> {
        return recipesRepository.getAllRecipes(category)
    }


    fun load(nome: String):  LiveData<Recipe>{
        return recipesRepository.load(nome)
    }

    fun loadRecipe(nome: String, imageView: ImageView) = recipesRepository.loadRecipe(nome, imageView)

}
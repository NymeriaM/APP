package br.edu.ifsp.arq.dmos5.brotinho.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.arq.dmos5.brotinho.model.Recipe
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class ActivitiesRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllRecipes(category: String?): LiveData<List<Recipe>> {
        val liveData = MutableLiveData<List<Recipe>>()
        val recipes: MutableList<Recipe> = ArrayList()
        firestore.collection("receitas").whereEqualTo("categoria", category).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (doc in task.result) {
                        val recipe = doc.toObject(Recipe::class.java)

                        recipes.add(recipe)
                    }
                }
                liveData.setValue(recipes)
            }
        return liveData
    }
}
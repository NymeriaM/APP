package br.edu.ifsp.arq.dmos5.brotinho.repository

import android.app.Application
import android.util.Log
import android.util.Log.*
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.arq.dmos5.brotinho.model.Recipe
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class RecipesRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()

    fun getAllRecipes(category: String?): LiveData<List<Recipe>> {
        val liveData = MutableLiveData<List<Recipe>>()
        val recipes: MutableList<Recipe> = ArrayList()

        val document: DocumentReference = firestore.collection("categoria").document(category.toString())

        Log.d("document", document.id)

        firestore.collection("receitas").whereEqualTo("categoria", document.id).get()
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
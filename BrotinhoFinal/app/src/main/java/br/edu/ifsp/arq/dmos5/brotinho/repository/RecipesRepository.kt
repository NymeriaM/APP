package br.edu.ifsp.arq.dmos5.brotinho.repository

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import android.util.Log.*
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.arq.dmos5.brotinho.R
import br.edu.ifsp.arq.dmos5.brotinho.model.Recipe
import br.edu.ifsp.arq.dmos5.brotinho.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.storage.ktx.storage


class RecipesRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()
    private val glide = Glide.with(application)
    private val preference = PreferenceManager.getDefaultSharedPreferences(application)
    private val storage = Firebase.storage(Firebase.app)



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

    fun load(nome: String) : LiveData<Recipe> {
        val liveData = MutableLiveData<Recipe>()

        firestore.collection("receitas").whereEqualTo("nome", nome).get()
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    for (doc in task.result) {
                        val recipe = doc.toObject(Recipe::class.java)
                        liveData.value = recipe
                    }
                }

        }

        return liveData
    }

    fun loadRecipe(nome: String, imageView: ImageView) = storage.reference.child(nome)
        .downloadUrl.addOnSuccessListener {
            glide.load(it)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.drawable.freegluten)
                .placeholder(R.drawable.freegluten)
                .into(imageView)
        }

}
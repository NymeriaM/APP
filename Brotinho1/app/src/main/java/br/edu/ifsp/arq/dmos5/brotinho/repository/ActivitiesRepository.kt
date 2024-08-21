package br.edu.ifsp.arq.dmos5.brotinho.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.arq.dmos5.brotinho.model.PhysicalActivity
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class ActivitiesRepository (application: Application) {

    private val firestore = FirebaseFirestore.getInstance()

    fun insert(activity: PhysicalActivity?) {
        firestore.collection("activity").add(activity!!)
            .addOnSuccessListener { unused: DocumentReference? ->
                Log.d(
                    this.toString(),
                    "Atividade cadastrada com sucesso."
                )
            }
    }

    fun update(activity: PhysicalActivity) {
        firestore.collection("activity").document(activity.id).set(activity)
            .addOnSuccessListener { unused: Void? ->
                Log.d(
                    this.toString(),
                    "Atividade atualizada com sucesso."
                )
            }
    }

    fun delete(activity: PhysicalActivity) {
        firestore.collection("activity").document(activity.id).delete()
            .addOnSuccessListener { unused: Void? ->
                Log.d(
                    this.toString(),
                    "Atividade atualizada com sucesso."
                )
            }
    }

    fun getAllActivities(userId: String?): LiveData<List<PhysicalActivity>> {
        val liveData = MutableLiveData<List<PhysicalActivity>>()
        val activities: MutableList<PhysicalActivity> = ArrayList()
        firestore.collection("activity").whereEqualTo("userId", userId).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (doc in task.result) {
                        val activity = doc.toObject(PhysicalActivity::class.java)
                        activity.id = doc.id
                        activities.add(activity)
                    }
                }
                liveData.setValue(activities)
            }
        return liveData
    }
}
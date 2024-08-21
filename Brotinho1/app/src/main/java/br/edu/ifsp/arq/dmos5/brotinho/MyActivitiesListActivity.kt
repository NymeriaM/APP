package br.edu.ifsp.arq.dmos5.brotinho

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.edu.ifsp.arq.dmos5.brotinho.adapter.ActivityAdapter
import br.edu.ifsp.arq.dmos5.brotinho.model.PhysicalActivity
import br.edu.ifsp.arq.dmos5.brotinho.model.User
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.PhysicalActivityViewModel
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyActivitiesListActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var txtTitulo: TextView

    lateinit var btnNewActivity: FloatingActionButton

    lateinit var activitysList: ListView
    lateinit var adapter: ActivityAdapter

    private val userViewModel by viewModels<UserViewModel>()
    private val activityViewModel by viewModels<PhysicalActivityViewModel>()

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_activities_list)
        setToolBar()
        setBtnNewActivity()
    }

    private fun setAdapter() {
        activityViewModel.allActivities(user.id).observe(this, Observer{
            adapter = ActivityAdapter(
                this@MyActivitiesListActivity,
                android.R.layout.simple_list_item_1,
                it
            )
            activitysList = findViewById<View>(R.id.activity_list) as ListView
            activitysList.adapter = adapter
            adapter.notifyDataSetChanged()
        })
    }

    fun deleteActivity(pos: Int) {
        activityViewModel.allActivities(user?.id)
            ?.observe(this, object : Observer<List<PhysicalActivity>> {
                override fun onChanged(activities: List<PhysicalActivity>) {
                    activityViewModel!!.deleteActivity(activities[pos])
                    setAdapter()
                    Toast.makeText(
                        this@MyActivitiesListActivity, "Atividade removida com sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun loadUserLogged() {
        userViewModel.isLogged().observe(this,  Observer {
            if (it == null) {
                startActivity(
                    Intent(
                        this@MyActivitiesListActivity,
                        LoginActivity::class.java
                    )
                )
                finish()
            } else {
                this@MyActivitiesListActivity.user = it
                setAdapter()
            }
        })
    }

    private fun setBtnNewActivity() {
        btnNewActivity = findViewById<FloatingActionButton>(R.id.btn_add_activity)
        btnNewActivity?.setOnClickListener {

            val intent = Intent(
                this@MyActivitiesListActivity,
                ActivityRegisterActivity::class.java
            )
            val activity = PhysicalActivity()
            activity.id = "0"
            intent.putExtra("activity", activity)
            startActivity(intent)

        }
    }

    private fun setToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        txtTitulo = findViewById<TextView>(R.id.toolbar_title)
        txtTitulo?.setText("Atividades")
    }

    override fun onResume() {
        super.onResume()
        loadUserLogged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
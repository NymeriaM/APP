package br.edu.ifsp.arq.dmos5.brotinho

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.edu.ifsp.arq.dmos5.brotinho.model.PhysicalActivity
import br.edu.ifsp.arq.dmos5.brotinho.model.User
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.PhysicalActivityViewModel
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.UUID


class ActivityRegisterActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var txtTitle: TextView
    lateinit var txtDate: TextInputEditText
    lateinit var txtDuration: TextInputEditText
    lateinit var txtDistance: TextInputEditText
    lateinit var spnActivityType: Spinner
    lateinit var btnSave: Button

    private val userViewModel by viewModels<UserViewModel>()
    private val activityViewModel by viewModels<PhysicalActivityViewModel>()

    lateinit var user: User
    lateinit var activity: PhysicalActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        activity = intent.extras!!.getSerializable("activity") as PhysicalActivity
        setToolBar()
        setComponents()
        if(activity.id != "0"){
            fillFields()
        }
        setBtnSave()
    }

    private fun setComponents() {
        spnActivityType = findViewById<Spinner>(R.id.spn_activity)
        txtDate = findViewById<TextInputEditText>(R.id.edt_activity_date)
        txtDuration = findViewById<TextInputEditText>(R.id.edt_duration)
        txtDistance = findViewById<TextInputEditText>(R.id.edt_distance)
        btnSave = findViewById<Button>(R.id.btn_add_activity)
    }

    private fun fillFields() {
        val activities: Array<PhysicalActivity.PhysicalActivityType> = PhysicalActivity.PhysicalActivityType.values()
        for (i in activities.indices) {
            if (activities[i].equals(activity.type)) {
                spnActivityType.setSelection(i)
            }
        }
        txtDate.setText(activity.date)
        txtDistance.setText(activity.distance.toString())
        txtDuration.setText(activity.duration.toString())
    }

    private fun setBtnSave() {
        btnSave.setOnClickListener {
            if (activity.id == "0") {
                addActivity()
            } else {
                updateActivity()
            }
        }
    }

    private fun updateActivity() {
        if (validate()) {
            activity.type = PhysicalActivity.PhysicalActivityType.values().get(spnActivityType.selectedItemPosition)
            activity.date = txtDate.text.toString()
            activity.distance = txtDistance.text.toString().toDouble()
            activity.duration = txtDuration.text.toString().toDouble()
            activityViewModel.updateActivity(activity)
            Toast.makeText(
                this@ActivityRegisterActivity,
                "Atividade atualizada com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun addActivity() {
        if (validate()) {
            val activity = PhysicalActivity(
                UUID.randomUUID().toString(),
                user.id,
                PhysicalActivity.PhysicalActivityType.values().get(spnActivityType.selectedItemPosition),
                txtDistance.text.toString().toDouble(),
                txtDuration.text.toString().toDouble(),
                txtDate.text.toString()
            )
            activityViewModel.createActivity(activity)
            Toast.makeText(
                this@ActivityRegisterActivity,
                "Atividade adicionada com sucesso!",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun validate(): Boolean {
        var isValid = true
        if (txtDistance.text.toString().trim { it <= ' ' }.isEmpty()) {
            txtDistance.error = "Preencha o campo da distância"
            isValid = false
        } else {
            txtDistance.error = null
        }
        if (txtDuration.text.toString().trim { it <= ' ' }.isEmpty()) {
            txtDuration.error = "Preencha o campo da duração"
            isValid = false
        } else {
            txtDuration.error = null
        }
        if (txtDate.text.toString().trim { it <= ' ' }.isEmpty()) {
            txtDate.error = "Preencha o campo da data"
            isValid = false
        } else {
            txtDate.error = null
        }
        return isValid
    }

    private fun loadUserLogged() {
        userViewModel.isLogged().observe(this,  Observer {
            if (it == null) {
                startActivity(
                    Intent(
                        this@ActivityRegisterActivity,
                        LoginActivity::class.java
                    )
                )
                finish()
            } else {
                this@ActivityRegisterActivity.user = it
            }
        })
    }

    private fun setToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        txtTitle = findViewById(R.id.toolbar_title)
        if(activity.id == "0"){
            txtTitle.text = getString(R.string.new_activity)
        }else{
            txtTitle.text = getString(R.string.edit_activity)
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBar()
        loadUserLogged()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
package java.br.edu.ifsp.arq.dmos5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import java.br.edu.ifsp.arq.dmos5.model.User
import java.br.edu.ifsp.arq.dmos5.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserProfileActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var txtTitle: TextView
    lateinit var edtName: TextInputEditText
    lateinit var edtSurname: TextInputEditText
    lateinit var edtEmail: TextInputEditText
    lateinit var edtDateOfBirth: TextInputEditText
    lateinit var spnGender: Spinner
    lateinit var btnProfileUpdate: Button

    private val userViewModel by viewModels<UserViewModel>()

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        setToolBar()
        setComponents()
        loadUserLogged()
        setBtnProfileUpdate()
    }

    private fun setBtnProfileUpdate() {
        val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        btnProfileUpdate = findViewById(R.id.btn_profile_update)
        btnProfileUpdate.setOnClickListener{
            user.email = edtEmail.text.toString()
            user.name = edtName.text.toString()
            user.surname = edtSurname.text.toString()
            user.dateOfBirth = LocalDate.parse(edtDateOfBirth.text.toString(), dtf)
            user.gender = User.Gender.values()[spnGender.selectedItemPosition]


            userViewModel.updateUser(user)
        }
    }


    private fun loadUserLogged() {
        userViewModel.isLogged().observe(this, Observer {
            if (it != null) {
                user = it
                edtName.setText(it.name)
                edtSurname.setText(it.surname)
                edtEmail.setText(it.email)
                if(it.dateOfBirth != null){
                    val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    edtDateOfBirth.setText(it.dateOfBirth!!.format(dtf).toString())
                }else{
                    edtDateOfBirth.setText("")
                }
                resources.getStringArray(R.array.gender).asList().indexOf(it.gender?.value).let {
                    spnGender.setSelection(it)
                }
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun setComponents() {
        edtName = findViewById(R.id.txt_edt_name);
        edtSurname = findViewById(R.id.txt_edt_surname);
        edtEmail = findViewById(R.id.txt_edt_email);
        edtDateOfBirth = findViewById(R.id.txt_edt_date_of_birth);
        spnGender = findViewById(R.id.spn_gender);
    }

    private fun setToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        txtTitle = findViewById(R.id.toolbar_title)
        txtTitle.text = getString(R.string.user_profile)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
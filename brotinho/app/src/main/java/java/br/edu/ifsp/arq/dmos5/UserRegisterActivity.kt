package java.br.edu.ifsp.arq.dmos5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import java.br.edu.ifsp.arq.dmos5.model.User
import java.br.edu.ifsp.arq.dmos5.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputEditText

class UserRegisterActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var txtTitle: TextView
    lateinit var edtName: TextInputEditText
    lateinit var edtEmail: TextInputEditText
    lateinit var edtPassword: TextInputEditText
    lateinit var btnUserRegister: Button

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_register)
        setToolBar()
        setBtnUserRegister()
    }

    private fun setBtnUserRegister() {
        edtName = findViewById(R.id.txt_edt_name)
        edtEmail = findViewById(R.id.txt_edt_email)
        edtPassword = findViewById(R.id.txt_edt_password)

        btnUserRegister = findViewById(R.id.btn_user_register)
        btnUserRegister.setOnClickListener {
            val user = User(
                email = edtEmail.text.toString(),
                name = edtName.text.toString(),
                surname = "",
                password = edtPassword.text.toString(),
                image = "",
                dateOfBirth = null,
                gender = User.Gender.PREFIRO_NAO_DIZER
            )

            userViewModel.createUser(user)
            userViewModel.login(user.email, user.password).observe(this, Observer {
                finish()
            })
        }
    }

    private fun setToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        txtTitle = findViewById(R.id.toolbar_title)
        txtTitle.text = getString(R.string.new_user)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
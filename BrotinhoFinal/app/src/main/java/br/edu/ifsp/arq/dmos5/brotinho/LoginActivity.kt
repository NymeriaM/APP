package br.edu.ifsp.arq.dmos5.brotinho

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import br.edu.ifsp.arq.dmos5.brotinho.viewmodel.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var txtTitle: TextView
    lateinit var btnNewUser: Button
    lateinit var btnLoginUser: Button
    lateinit var edtEmail: TextInputEditText
    lateinit var edtPassword: TextInputEditText
    lateinit var txtForgotPassword: TextView
    lateinit var dialogResetPassword: AlertDialog

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setToolBar()
        setBtnNewUser()
        setBtnLoginUser()
        buildResetPasswordDialog()
        setTxtForgotPassword()
    }

    private fun buildResetPasswordDialog() {

        val view = LayoutInflater.from(this).inflate(R.layout.dialog_reset_password, null)
        val edtEmail = view.findViewById<TextInputEditText>(R.id.edt_reset_email)

        dialogResetPassword = MaterialAlertDialogBuilder(this)
            .setPositiveButton("Resetar") { dialog, which ->
                userViewModel.resetPassword(edtEmail.text.toString())
                Toast.makeText(this, "Verifique seu email para resetar a senha", Toast.LENGTH_LONG).show()
                edtEmail.text?.clear()
            }
            .setNegativeButton("Cancelar") { dialog, which ->
                edtEmail.text?.clear()
            }
            .setIcon(android.R.drawable.ic_dialog_email)
            .setView(view)
            .setTitle("Preencha seu email para resetar sua senha.")
            .create()
    }

    private fun setTxtForgotPassword() {
        txtForgotPassword = findViewById<TextView>(R.id.txt_forgot_password)
        txtForgotPassword.setOnClickListener { dialogResetPassword.show() }
    }

    private fun setBtnLoginUser() {
        edtEmail = findViewById(R.id.txt_edt_email)
        edtPassword = findViewById(R.id.txt_edt_password)
        btnLoginUser = findViewById(R.id.btn_login_user)
        btnLoginUser.setOnClickListener {
            userViewModel.login(edtEmail.text.toString(), edtPassword.text.toString()).observe(this, Observer {
                if(it == null)
                    Toast.makeText(applicationContext, getString(R.string.login_message), Toast.LENGTH_SHORT).show()
                else {
                    intent = Intent(
                        this@LoginActivity,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }
            })
        }
    }

    private fun setBtnNewUser() {
        btnNewUser = findViewById(R.id.btn_login_new_user)
        btnNewUser.setOnClickListener(View.OnClickListener {
            val intent = Intent(
                this@LoginActivity,
                UserRegisterActivity::class.java
            )
            startActivity(intent)
            finish()
        })
    }

    private fun setToolBar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        txtTitle = findViewById(R.id.toolbar_title)
        txtTitle.text = getString(R.string.login)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
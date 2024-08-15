package java.br.edu.ifsp.arq.dmos5


import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    private lateinit var editTextRegisterEmail: EditText
    private lateinit var editTextRegisterPassword: EditText
    private lateinit var editTextRegisterSex: EditText
    private lateinit var editTextRegisterBirthdate: EditText
    private lateinit var editTextRegisterNickname: EditText
    private lateinit var buttonRegister: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        editTextRegisterEmail = findViewById(R.id.editTextRegisterEmail)
        editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword)
        editTextRegisterSex = findViewById(R.id.editTextRegisterSex)
        editTextRegisterBirthdate = findViewById(R.id.editTextRegisterBirthdate)
        editTextRegisterNickname = findViewById(R.id.editTextRegisterNickname)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val email = editTextRegisterEmail.text.toString().trim()
            val password = editTextRegisterPassword.text.toString().trim()
            val sex = editTextRegisterSex.text.toString().trim()
            val birthdate = editTextRegisterBirthdate.text.toString().trim()
            val nickname = editTextRegisterNickname.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                editTextRegisterEmail.error = "Email é obrigatório."
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                editTextRegisterPassword.error = "Senha é obrigatória."
                return@setOnClickListener
            }

            if (password.length < 6) {
                editTextRegisterPassword.error = "A senha deve ter pelo menos 6 caracteres."
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(sex)) {
                editTextRegisterSex.error = "Sexo é obrigatório."
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(birthdate)) {
                editTextRegisterBirthdate.error = "Data de Nascimento é obrigatória."
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(nickname)) {
                editTextRegisterNickname.error = "Apelido é obrigatório."
                return@setOnClickListener
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = mAuth.currentUser?.uid
                        val user = hashMapOf(
                            "email" to email,
                            "sexo" to sex,
                            "dataNascimento" to birthdate,
                            "apelido" to nickname
                        )

                        userId?.let {
                            firestore.collection("users").document(it)
                                .set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Cadastro feito com sucesso",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                    finish()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Erro ao salvar dados: ${e.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Erro ao cadastrar: " + task.exception?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}
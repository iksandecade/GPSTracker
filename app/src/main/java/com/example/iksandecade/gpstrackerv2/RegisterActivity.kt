package com.example.iksandecade.gpstrackerv2

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var email = ""
    var password = ""
    var nama = ""
    var userName = ""

    internal var firebaseAuth: FirebaseAuth? = null
    internal var databaseReference: DatabaseReference? = null
    internal var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        progressDialog = ProgressDialog(this)

        progressDialog!!.setMessage("Loading")

        btnRegister.setOnClickListener { _ ->
            run {
                if (isValidate()) {
                    progressDialog!!.show()
                    firebaseAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        run {

                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser? = firebaseAuth!!.currentUser
                                sendEmail(firebaseUser)
                            } else {
                                progressDialog!!.dismiss()
                                if (task.exception is FirebaseAuthUserCollisionException) {
                                    Toast.makeText(this, "akun sudah ada", Toast.LENGTH_SHORT).show()
                                } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show()
                                } else if (task.exception is FirebaseAuthWeakPasswordException) {
                                    Toast.makeText(this, "Password anda terlalu lemah", Toast.LENGTH_SHORT).show()
                                } else if (task.exception is FirebaseAuthInvalidUserException) {
                                    Toast.makeText(this, "Email belum terdaftar", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    fun sendEmail(firebaseUser: FirebaseUser?) {
        firebaseUser!!.sendEmailVerification().addOnCompleteListener { task2 ->
            run {
                if (task2.isSuccessful) {
                    progressDialog!!.dismiss()
                    val userModel: UserModel = UserModel()
                    val tokenModel: TokenModel = TokenModel()
                    val token = FirebaseInstanceId.getInstance().token

                    userModel.nama = nama
                    userModel.userId = firebaseUser.uid
                    userModel.validate = false
                    userModel.token = token
                    userModel.email = email
                    userModel.username = userName

                    tokenModel.userId = firebaseUser.uid
                    tokenModel.token = token

                    databaseReference!!.child("user").child(firebaseUser.uid).setValue(userModel)
                    databaseReference!!.child("token").child(userName).setValue(tokenModel)
                    MaterialDialog.Builder(this)
                            .title("Finish")
                            .content("Check email anda untuk memverifikasi akun :DD")
                            .positiveText("Oke Oce")
                            .onPositive { _, _ -> finish() }
                            .show()
                } else {
                    Toast.makeText(this, "Opps, result not se", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun isValidate(): Boolean {
        var result = true

        email = etEmail.text.toString()
        password = etPassword.text.toString()
        nama = etName.text.toString()
        userName = etUsername.text.toString()
        val emailDomain = email.substring(email.lastIndexOf("@") + 1)

        if (email.trim().isEmpty()) {
            etEmail.error = "Masukan email"
            result = false
        } else if (!email.contains("@")) {
            etEmail.error = "Email tidak valid"
            result = false
        } else if (!emailDomain.contains(".")) {
            etEmail.error = "Email tidak valid"
            result = false
        }

        if (password.trim().isEmpty()) {
            etPassword.error = "Masukan Password"
            result = false
        }

        if (nama.trim().isEmpty()) {
            etName.error = "Masukan nama"
            result = false
        }

        if (userName.trim().isEmpty()) {
            etUsername.error = "Masukan Username"
            result = false
        }


        return result
    }
}

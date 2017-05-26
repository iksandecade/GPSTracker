package com.example.iksandecade.gpstrackerv2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.auth.*
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var username = ""
    var password = ""

    internal var firebaseAuth: FirebaseAuth? = null
    internal var progressDialog: ProgressDialog? = null
    internal var databaseReference: DatabaseReference? = null
    internal var context: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SPGPSTracker.getIsLogin(this)) {
            startActivity(Intent(this, MainMenuActivity::class.java))
            finish()
        }

        context = this
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference
        progressDialog = ProgressDialog(this)

        progressDialog!!.setMessage("Loading")

        btnLogin.setOnClickListener { _ ->
            run {
                if (isValidate()) {
                    progressDialog!!.show()

                    firebaseAuth!!.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
                        run {
                            progressDialog!!.dismiss()
                            if (task.isSuccessful) {
                                val firebaseUser: FirebaseUser? = firebaseAuth!!.currentUser
                                if (firebaseUser!!.isEmailVerified) {
                                    databaseReference!!.child("user").child(firebaseUser.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onCancelled(p0: DatabaseError?) {
                                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                                        }

                                        override fun onDataChange(p0: DataSnapshot?) {
                                            val userModel: UserModel = p0!!.getValue(UserModel::class.java)
                                            SPGPSTracker.setAuthCode(context, firebaseUser.uid)
                                            SPGPSTracker.setIsLogin(context, true)
                                            SPGPSTracker.setEmail(context, userModel.email)
                                            SPGPSTracker.setNama(context, userModel.nama)
                                            SPGPSTracker.setUsername(context, userModel.username)
                                            SPGPSTracker.setToken(context, userModel.token)
                                            startActivity(Intent(context, MainMenuActivity::class.java))
                                        }

                                    })
                                } else {
                                    Toast.makeText(this, "Email belum di verifikasi", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                if (task.exception is FirebaseAuthUserCollisionException) {
                                    Toast.makeText(this, "AKUN UDAH ADA CAK", Toast.LENGTH_SHORT).show()
                                } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show()
                                } else if (task.exception is FirebaseAuthWeakPasswordException) {
                                    Toast.makeText(this, "Password ANDA LEMAH SEPERTI ANDA", Toast.LENGTH_SHORT).show()
                                } else if (task.exception is FirebaseAuthInvalidUserException) {
                                    Toast.makeText(this, "Email belum terdaftar", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }

        btnRegister.setOnClickListener { v ->
            run {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }

    }

    fun isValidate(): Boolean {
        var result = true

        username = etUsername.text.toString()
        password = etPassword.text.toString()
        val emailDomain = username.substring(username.lastIndexOf("@") + 1)

        if (username.trim().isEmpty()) {
            etUsername.error = "Email atau Username harus di isi"
            result = false
        } else if (!username.contains("@")) {

        } else if (!emailDomain.contains(".")) {
            etUsername.error = "Alamat email tidak valid"
            result = false
        }

        if (password.trim().isEmpty()) {
            etPassword.error = "Password harus di isi"
            result = false
        }

        return result
    }
}

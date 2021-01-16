package com.pascal.appsmk.view.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pascal.appsmk.R
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initView()
    }

    private fun initView() {
        mAuth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {
            val email = edt_register_email.text.toString()
            val password = edt_register_password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FragmentActivity.TAG", "createUserWithEmail:success")
                            val user = mAuth!!.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(
                                "FragmentActivity.TAG",
                                "createUserWithEmail:failure",
                                task.exception
                            )
                            showToast("Register gagal")
                        }
                    }
            } else {
                showToast("Email atau Password tidak boleh kosong")
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        showToast("Register berhasil")
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
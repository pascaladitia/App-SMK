package com.pascal.appsmk.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pascal.appsmk.R
import com.pascal.appsmk.view.admin.dashboard.DashboardAdminActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initView()
    }

    private fun initView() {
        mAuth = FirebaseAuth.getInstance()

        btn_login.setOnClickListener {
            val email = edt_login_email.text.toString()
            val password = edt_login_password.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(
                        this
                    ) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FragmentActivity.TAG", "signInWithEmail:success")
                            val user = mAuth!!.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FragmentActivity.TAG", "signInWithEmail:failure", task.exception)
                            showToast("Email atau Password salah")
                        }
                    }
            } else {
                showToast("Email atau password tidak boleh kosong")
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, DashboardAdminActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
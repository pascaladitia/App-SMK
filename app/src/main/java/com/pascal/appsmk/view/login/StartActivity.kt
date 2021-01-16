package com.pascal.appsmk.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pascal.appsmk.R
import com.pascal.appsmk.view.admin.dashboard.DashboardAdminActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_to_dashboard.setOnClickListener(this)
        btn_to_login.setOnClickListener(this)
        btn_to_register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_to_dashboard -> {
                startActivity(Intent(this@StartActivity, DashboardAdminActivity::class.java))
            }
            R.id.btn_to_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.btn_to_register -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }
}
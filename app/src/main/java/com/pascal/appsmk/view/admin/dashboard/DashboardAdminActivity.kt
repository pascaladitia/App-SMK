package com.pascal.appsmk.view.admin.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pascal.appsmk.R
import com.pascal.appsmk.view.admin.eskul.EskulAdminActivity
import com.pascal.appsmk.view.admin.jadwal.JadwalAdminActivity
import com.pascal.appsmk.view.admin.kehadiran.KehadiranAdminActivity
import com.pascal.appsmk.view.admin.prestasi.PrestasiAdminActivity
import com.pascal.appsmk.view.admin.profil.ProfilAdminActivity
import com.pascal.appsmk.view.admin.staff.StaffAdminActivity
import kotlinx.android.synthetic.main.activity_dashboard_admin.*

class DashboardAdminActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)

        dashboard_profil.setOnClickListener(this)
        dashboard_staff.setOnClickListener(this)
        dashboard_eskul.setOnClickListener(this)
        dashboard_kehadiran.setOnClickListener(this)
        dashboard_jadwal.setOnClickListener(this)
        dashboard_prestasi.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.dashboard_profil -> {
                startActivity(Intent(this, ProfilAdminActivity::class.java))
            }
            R.id.dashboard_staff -> {
                startActivity(Intent(this, StaffAdminActivity::class.java))
            }
            R.id.dashboard_eskul -> {
                startActivity(Intent(this, EskulAdminActivity::class.java))
            }
            R.id.dashboard_kehadiran -> {
                startActivity(Intent(this, KehadiranAdminActivity::class.java))
            }
            R.id.dashboard_jadwal -> {
                startActivity(Intent(this, JadwalAdminActivity::class.java))
            }
            R.id.dashboard_prestasi -> {
                startActivity(Intent(this, PrestasiAdminActivity::class.java))
            }
        }
    }
}
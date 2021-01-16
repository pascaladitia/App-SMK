package com.pascal.appsmk.view.admin.eskul

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Staff
import com.pascal.appsmk.view.admin.staff.DetailStaffActivity
import com.pascal.appsmk.view.admin.staff.StaffAdminAdapter
import com.pascal.appsmk.viewModel.ViewModelEskul
import kotlinx.android.synthetic.main.activity_eskul_admin.*

class EskulAdminActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelEskul
    private var reference: DatabaseReference? = null
    private var db: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eskul_admin)

        initView()
        getFirebase()
    }

    private fun initView() {
        db = FirebaseDatabase.getInstance()
        reference = db?.getReference("eskul")

        viewModel = ViewModelProviders.of(this).get(ViewModelEskul::class.java)

        btnEskul_add.setOnClickListener {
            startActivity(Intent(this, InputEskulActivity::class.java))
        }
    }

    private fun getFirebase() {
        val dataWisata = ArrayList<Staff>()

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {
                Toast.makeText(this@EskulAdminActivity, snapshot.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datas in snapshot.children) {
                    val key = datas.key
                    val image = datas.child("image").value.toString()
                    val nama = datas.child("name").value.toString()
                    val ttl = datas.child("ttl").value.toString()
                    val alamat = datas.child("alamat").value.toString()
                    val jabatan = datas.child("jabatan").value.toString()
                    val tentang = datas.child("tentang").value.toString()

                    val wisata = Staff(image, nama, ttl, alamat, jabatan, tentang, key)
                    dataWisata.add(wisata)
                    showData(dataWisata)
                    progress_eskul.visibility = View.GONE
                }
            }

            private fun showData(dataWisata: ArrayList<Staff>) {
                recycler_eskul.adapter = StaffAdminAdapter(dataWisata, object : StaffAdminAdapter.OnClickListener {
                    override fun update(item: Staff?) {
                        val intent = Intent(this@EskulAdminActivity, InputEskulActivity::class.java)
                        intent.putExtra("data", item)
                        startActivity(intent)
                    }

                    override fun delete(item: Staff?) {
                        AlertDialog.Builder(this@EskulAdminActivity).apply {
                            setTitle("Hapus")
                            setMessage("Yakin Hapus Siswa?")
                            setCancelable(false)

                            setPositiveButton("Yes") { dialogInterface, i ->
                                reference?.child(item?.key ?: "")?.removeValue()
                                getFirebase()
                            }
                            setNegativeButton("Cancel") { dialogInterface, i ->
                                dialogInterface?.dismiss()
                            }
                        }.show()
                    }

                    override fun detail(item: Staff?) {
                        val intent = Intent(this@EskulAdminActivity, DetailStaffActivity::class.java)
                        intent.putExtra("data", item)
                        startActivity(intent)
                    }

                })
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getFirebase()
    }
}
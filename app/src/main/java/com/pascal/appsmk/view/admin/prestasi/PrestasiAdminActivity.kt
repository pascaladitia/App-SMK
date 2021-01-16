package com.pascal.appsmk.view.admin.prestasi

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.database.*
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Prestasi
import com.pascal.appsmk.viewModel.ViewModelPrestasi
import kotlinx.android.synthetic.main.activity_prestasi_admin.*

class PrestasiAdminActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelPrestasi
    private var reference: DatabaseReference? = null
    private var db: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prestasi_admin)

        initView()
        getFirebase()
    }

    private fun initView() {
        db = FirebaseDatabase.getInstance()
        reference = db?.getReference("prestasi")

        viewModel = ViewModelProviders.of(this).get(ViewModelPrestasi::class.java)

        btnPrestasi_add.setOnClickListener {
            startActivity(Intent(this, InputPrestasiActivity::class.java))
        }
    }

    private fun getFirebase() {
        val data = ArrayList<Prestasi>()

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {
                Toast.makeText(this@PrestasiAdminActivity, snapshot.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (datas in snapshot.children) {
                    val key = datas.key
                    val image = datas.child("image").value.toString()
                    val nama = datas.child("name").value.toString()
                    val tgl = datas.child("tgl").value.toString()
                    val jenis = datas.child("jenis").value.toString()
                    val deskripsi = datas.child("deskripsi").value.toString()

                    val database = Prestasi(image, nama, tgl, jenis, deskripsi, key)
                    data.add(database)
                    showData(data)
                    progress_prestasi.visibility = View.GONE
                }
            }

            private fun showData(dataWisata: ArrayList<Prestasi>) {
                recycler_prestasi.adapter = PrestasiAdminAdapter(dataWisata, object : PrestasiAdminAdapter.OnClickListener {
                    override fun update(item: Prestasi?) {
                        val intent = Intent(this@PrestasiAdminActivity, InputPrestasiActivity::class.java)
                        intent.putExtra("data", item)
                        startActivity(intent)
                    }

                    override fun delete(item: Prestasi?) {
                        AlertDialog.Builder(this@PrestasiAdminActivity).apply {
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

                    override fun detail(item: Prestasi?) {
                        val intent = Intent(this@PrestasiAdminActivity, DetailPrestasiActivity::class.java)
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
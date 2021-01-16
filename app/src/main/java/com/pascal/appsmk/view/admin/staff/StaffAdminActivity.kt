package com.pascal.appsmk.view.admin.staff

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import com.google.firebase.database.*
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Staff
import com.pascal.appsmk.viewModel.ViewModelStaff
import kotlinx.android.synthetic.main.activity_staff_admin.*

class StaffAdminActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelStaff
    private var reference: DatabaseReference? = null
    private var db: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff_admin)

        initView()
        getFirebase()
    }

    private fun initView() {
        db = FirebaseDatabase.getInstance()
        reference = db?.getReference("staff")

        viewModel = ViewModelProviders.of(this).get(ViewModelStaff::class.java)

        btnStaff_add.setOnClickListener {
            startActivity(Intent(this, InputStaffActivity::class.java))
        }
    }

    private fun getFirebase() {
        val dataWisata = ArrayList<Staff>()

        reference?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {
                Toast.makeText(this@StaffAdminActivity, snapshot.message, Toast.LENGTH_SHORT).show()
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
                    progress_staff.visibility = View.GONE
                }
            }

            private fun showData(dataWisata: ArrayList<Staff>) {
                recycler_staff.adapter = StaffAdminAdapter(dataWisata, object : StaffAdminAdapter.OnClickListener {
                    override fun update(item: Staff?) {
                        val intent = Intent(this@StaffAdminActivity, InputStaffActivity::class.java)
                        intent.putExtra("data", item)
                        startActivity(intent)
                    }

                    override fun delete(item: Staff?) {
                        AlertDialog.Builder(this@StaffAdminActivity).apply {
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
                        val intent = Intent(this@StaffAdminActivity, DetailStaffActivity::class.java)
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
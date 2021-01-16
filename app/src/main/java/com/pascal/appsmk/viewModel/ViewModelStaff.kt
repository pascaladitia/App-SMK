package com.pascal.appsmk.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.pascal.appsmk.model.Staff

class ViewModelStaff(application: Application): AndroidViewModel(application) {
    
    var isEmpty = MutableLiveData<Boolean>()

    fun insertStaff(name: String, ttl: String, alamat: String, jabatan: String, tentang: String, imgPath: Uri) {
        if (name.isNotEmpty() && ttl.isNotEmpty() && alamat.isNotEmpty() && jabatan.isNotEmpty() && tentang.isNotEmpty()) {
            val storageRef = FirebaseStorage.getInstance().getReference("staff")
            val databaseRef = FirebaseDatabase.getInstance().getReference("staff").push()

            storageRef.putFile(imgPath!!)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        databaseRef.child("image").setValue(it.toString())
                        databaseRef.child("name").setValue(name)
                        databaseRef.child("ttl").setValue(ttl)
                        databaseRef.child("alamat").setValue(alamat)
                        databaseRef.child("jabatan").setValue(jabatan)
                        databaseRef.child("tentang").setValue(tentang)
                    }
                }
                .addOnFailureListener{
                    println("Info File : ${it.message}")
                }
        } else {
            isEmpty.value = true
        }
    }

    fun updateStaff(name: String, ttl: String, alamat: String, jabatan: String, tentang: String, key: String, imgPath: Uri) {
        if (name.isNotEmpty() && ttl.isNotEmpty() && alamat.isNotEmpty() && jabatan.isNotEmpty() && tentang.isNotEmpty()) {
            val storageRef = FirebaseStorage.getInstance().getReference("staff")
            val databaseRef = FirebaseDatabase.getInstance().getReference("staff")

            storageRef.putFile(imgPath!!)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        val wisata = Staff(it.toString(), name, ttl, alamat, jabatan, tentang)
                        databaseRef.child(key ?: "").setValue(wisata)
                    }
                }
                .addOnFailureListener{
                    println("Info File : ${it.message}")
                }
        } else {
            isEmpty.value = true
        }
    }
}
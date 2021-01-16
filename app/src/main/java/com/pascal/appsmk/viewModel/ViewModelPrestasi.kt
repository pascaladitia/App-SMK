package com.pascal.appsmk.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.pascal.appsmk.model.Staff

class ViewModelPrestasi(application: Application): AndroidViewModel(application) {

    var isEmpty = MutableLiveData<Boolean>()

    fun insertPrestasi(name: String, tgl: String, jenis: String, deskripsi: String, imgPath: Uri) {
        if (name.isNotEmpty() && tgl.isNotEmpty() && jenis.isNotEmpty() && deskripsi.isNotEmpty()) {
            val storageRef = FirebaseStorage.getInstance().getReference("prestasi")
            val databaseRef = FirebaseDatabase.getInstance().getReference("prestasi").push()

            storageRef.putFile(imgPath!!)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        databaseRef.child("image").setValue(it.toString())
                        databaseRef.child("name").setValue(name)
                        databaseRef.child("tgl").setValue(tgl)
                        databaseRef.child("jenis").setValue(jenis)
                        databaseRef.child("deskripsi").setValue(deskripsi)
                    }
                }
                .addOnFailureListener{
                    println("Info File : ${it.message}")
                }
        } else {
            isEmpty.value = true
        }
    }

    fun updatePrestasi(name: String, tgl: String, jenis: String, deskripsi: String, key: String, imgPath: Uri) {
        if (name.isNotEmpty() && tgl.isNotEmpty() && jenis.isNotEmpty() && deskripsi.isNotEmpty()) {
            val storageRef = FirebaseStorage.getInstance().getReference("prestasi")
            val databaseRef = FirebaseDatabase.getInstance().getReference("prestasi")

            storageRef.putFile(imgPath!!)
                .addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                        val wisata = Staff(it.toString(), name, tgl, jenis, deskripsi)
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
package com.pascal.appsmk.view.admin.eskul

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Staff
import com.pascal.appsmk.viewModel.ViewModelEskul
import kotlinx.android.synthetic.main.activity_input_eskul.*
import kotlinx.android.synthetic.main.dialog_choose_image.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.random.Random

class InputEskulActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModelEskul
    private var imgPath: Uri? = null
    private var firebase: Staff? = null

    private val CAMERA_CODE = 1
    private val GALLERY_CODE = 2

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_eskul)

        viewModel = ViewModelProviders.of(this).get(ViewModelEskul::class.java)

        getParcel()
        attachObserve()
        initView()
        initBtn()
    }

    private fun attachObserve() {
        viewModel.isEmpty.observe(this, Observer { showErrorFirebase(it) })
    }

    private fun getParcel() {
        firebase = intent?.getParcelableExtra("data")
    }

    private fun initBtn() {

        inputEskul_image.setOnClickListener {
            showDialog()
        }
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        }

        if (firebase != null) {
            inputEskul_name.setText(firebase?.name)
            inputEskul_ttl.setText(firebase?.ttl)
            inputEskul_alamat.setText(firebase?.alamat)
            inputEskul_jabatan.setText(firebase?.jabatan)
            inputEskul_tentang.setText(firebase?.tentang)


            btnEskul_Input.text = "Update"
        }

        inputData()
    }

    private fun inputData() {
        when (btnEskul_Input.text) {
            "Update" -> {
                btnEskul_Input.setOnClickListener {
                    val name = inputEskul_name.text.toString()
                    val ttl = inputEskul_ttl.text.toString()
                    val alamat = inputEskul_alamat.text.toString()
                    val jabatan = inputEskul_jabatan.text.toString()
                    val tentang = inputEskul_tentang.text.toString()

                    imgPath?.let {
                        viewModel.updateEskul(
                            name, ttl, alamat, jabatan, tentang,
                            firebase?.key!!, it
                        )
                    }
                    finish()
                }
            }

            else -> {
                btnEskul_Input.setOnClickListener {
                    val name = inputEskul_name.text.toString()
                    val ttl = inputEskul_ttl.text.toString()
                    val alamat = inputEskul_alamat.text.toString()
                    val jabatan = inputEskul_jabatan.text.toString()
                    val tentang = inputEskul_tentang.text.toString()

                    imgPath?.let {
                        viewModel.insertEskul(
                            name, ttl, alamat, jabatan, tentang,
                            it
                        )
                    }
                    finish()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        dialog?.dismiss()
        if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            resultCamera(data)
        } else if (requestCode == GALLERY_CODE && resultCode == Activity.RESULT_OK) {
            resultGallery(data)
        }
    }

    private fun resultGallery(data: Intent?) {
        val image_bitmap = selectFromGalleryResult(data)
        inputEskul_image.setImageBitmap(image_bitmap)
    }

    private fun selectFromGalleryResult(data: Intent?): Bitmap {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                imgPath = data.data

                bm =
                    MediaStore.Images.Media.getBitmap(applicationContext.contentResolver, data.data)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return bm!!
    }

    private fun resultCamera(data: Intent?) {
        val image = data?.extras?.get("data")
        val random = Random.nextInt(0, 999)
        val name_file = "Camera$random"

        Log.d("data img camera", image.toString())

        val image_bitmap = persistImage(image as Bitmap, name_file)
        //imgPath = Uri.parse(image_bitmap)
        imgPath = Uri.fromFile(File(image_bitmap))

        inputEskul_image.setImageBitmap(BitmapFactory.decodeFile(image_bitmap))
    }

    private fun persistImage(bitmap: Bitmap, name: String): String? {
        val filesDir = filesDir
        val imageFile = File(filesDir, "${name}.png")

        var path = imageFile.path
        val os: OutputStream?
        try {
            os = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e("TAG", "persistImage: ${e.message.toString()}", e)
        }
        return path
    }

    private fun showDialog() {
        val window = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_choose_image, null)
        window.setView(view)

        view.dialog_image.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_CODE)
        }

        view.dialog_gallery.setOnClickListener {
            val mimeTypes = arrayOf("image/jpg", "image/jpeg", "image/gif", "image/png")

            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivityForResult(Intent.createChooser(intent, "Choose Image"), GALLERY_CODE)
        }

        dialog = window.create()
        dialog?.show()
    }

    private fun showErrorFirebase(it: Boolean?) {
        if (it == true) {
            showToast("form tidak boleh ada yang kosong")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }
}
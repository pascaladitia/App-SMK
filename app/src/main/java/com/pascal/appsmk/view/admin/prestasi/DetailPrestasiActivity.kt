package com.pascal.appsmk.view.admin.prestasi

import android.graphics.text.LineBreaker
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Prestasi
import com.pascal.appsmk.model.Staff
import kotlinx.android.synthetic.main.activity_detail_prestasi.*
import kotlinx.android.synthetic.main.activity_detail_staff.*

class DetailPrestasiActivity : AppCompatActivity() {

    private var item: Prestasi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_prestasi)

        initView()
    }

    private fun initView() {
        item = intent?.getParcelableExtra("data")

        detailPrestasi_nama.setText(item?.name)
        detailPrestasi_tgl.setText(item?.tgl)
        detailPrestasi_jenis.setText(item?.jenis)
        detailPrestasi_deskripsi.setText(item?.deskripsi)

        Glide.with(this)
            .load(item?.image)
            .apply(
                RequestOptions()
                    .override(500, 500)
                    .placeholder(R.drawable.ic_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH))
            .into(detailPrestasi_image)

        detailPrestasi_close.setOnClickListener {
            onBackPressed()
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            detailPrestasi_deskripsi.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
    }
}
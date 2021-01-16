package com.pascal.appsmk.view.admin.staff

import android.graphics.text.LineBreaker
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Staff
import kotlinx.android.synthetic.main.activity_detail_staff.*

class DetailStaffActivity : AppCompatActivity() {

    private var item: Staff? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_staff)

        initView()
    }

    private fun initView() {
        item = intent?.getParcelableExtra("data")

        detailStaff_nama.setText(item?.name)
        detailStaff_ttl.setText(item?.ttl)
        detailStaff_alamat.setText(item?.alamat)
        detailStaff_jabatan.setText(item?.jabatan)
        detailStaff_tentang.setText(item?.tentang)

        Glide.with(this)
            .load(item?.image)
            .apply(
                RequestOptions()
                    .override(500, 500)
                    .placeholder(R.drawable.ic_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH))
            .into(detailStaff_image)

        detailStaff_close.setOnClickListener {
            onBackPressed()
            finish()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            detailStaff_tentang.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
    }
}
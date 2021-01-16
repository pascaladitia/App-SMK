package com.pascal.appsmk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Prestasi (
    var image: String? = null,
    var name: String? = null,
    var tgl: String? = null,
    var jenis: String? = null,
    var deskripsi: String? = null,
    var key: String? = null
    ) : Parcelable {
        constructor(): this( "", "", "", "", "", "")
    }
package com.pascal.appsmk.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Staff (
    var image: String? = null,
    var name: String? = null,
    var ttl: String? = null,
    var alamat: String? = null,
    var jabatan: String? = null,
    var tentang: String? = null,
    var key: String? = null
) : Parcelable {
    constructor(): this( "", "", "", "", "", "", "")
}
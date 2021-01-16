package com.pascal.appsmk.view.admin.prestasi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Prestasi
import kotlinx.android.synthetic.main.item_prestasi.view.*

class PrestasiAdminAdapter (private val data: ArrayList<Prestasi>?,
                            private val itemClick: OnClickListener
) : RecyclerView.Adapter<PrestasiAdminAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrestasiAdminAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prestasi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Prestasi?) {
            view.itemPrestasi_nama.text = item?.name
            view.itemPrestasi_description.text = item?.deskripsi
            view.itemPrestasi_jenis.text = item?.jenis

            Glide.with(itemView.context)
                .load(item?.image)
                .apply(
                    RequestOptions()
                        .override(200,200)
                        .placeholder(R.drawable.ic_account)
                        .error(R.drawable.ic_account)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .into(itemView.itemPrestasi_image)

            view.itemPrestasi_update.setOnClickListener{
                itemClick.update(item)
            }

            view.itemPrestasi_delete.setOnClickListener {
                itemClick.delete(item)
            }

            view.setOnClickListener {
                itemClick.detail(item)
            }
        }
    }

    interface OnClickListener {
        fun update(item: Prestasi?)
        fun delete(item: Prestasi?)
        fun detail(item: Prestasi?)
    }
}
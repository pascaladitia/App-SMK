package com.pascal.appsmk.view.admin.staff

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.pascal.appsmk.R
import com.pascal.appsmk.model.Staff
import kotlinx.android.synthetic.main.item_staff.view.*

class StaffAdminAdapter (private val data: ArrayList<Staff>?,
                         private val itemClick: OnClickListener
) : RecyclerView.Adapter<StaffAdminAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffAdminAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_staff, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data?.get(position)

        holder.bind(item)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: Staff?) {
            view.staff_name.text = item?.name
            view.staff_description.text = item?.jabatan

            Glide.with(itemView.context)
                .load(item?.image)
                .apply(
                    RequestOptions()
                        .override(200,200)
                        .placeholder(R.drawable.ic_account)
                        .error(R.drawable.ic_account)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .priority(Priority.HIGH))
                .into(itemView.staff_image)

            view.staff_update.setOnClickListener{
                itemClick.update(item)
            }

            view.staff_delete.setOnClickListener {
                itemClick.delete(item)
            }

            view.setOnClickListener {
                itemClick.detail(item)
            }
        }
    }

    interface OnClickListener {
        fun update(item: Staff?)
        fun delete(item: Staff?)
        fun detail(item: Staff?)
    }
}
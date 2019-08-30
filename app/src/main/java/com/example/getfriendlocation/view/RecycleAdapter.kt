package com.example.getfriendlocation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.getfriendlocation.R
import com.example.getfriendlocation.data.UserLocationEntity

class ViewAdapter(var items: MutableList<UserLocationEntity>)
    : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycle_view_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(new: MutableList<UserLocationEntity>){
        this.items = new
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userLogin = itemView.findViewById<TextView>(R.id.userLogin)
        private val host = itemView.findViewById<TextView>(R.id.host)
        private val end_at = itemView.findViewById<TextView>(R.id.end_at)

        fun bind(item: UserLocationEntity) {
            userLogin.text = item.login
            host.text = item.host
            end_at.text = item.end_at
        }
    }
}
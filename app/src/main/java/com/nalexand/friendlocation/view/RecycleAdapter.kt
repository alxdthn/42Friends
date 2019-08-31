package com.nalexand.friendlocation.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.R.color.*
import com.nalexand.friendlocation.data.UserLocationEntity

class ViewAdapter(private var items: MutableList<UserLocationEntity>, val callback: Callback)
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
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val view = itemView.findViewById<ConstraintLayout>(R.id.recycle_view_item)

        fun bind(item: UserLocationEntity) {
            userLogin.text = item.login
            Log.d("bestTAG", "bind data!")
            Log.d("bestTAG", "login ${item.login} status ${item.end_at}")

            if (item.end_at == null) {
                date.text = GetDate().beginAt(item.begin_at)
                host.text = item.host
                date.setTextColor(ContextCompat.getColor(itemView.context, colorPrimaryLight))
                host.setTextColor(ContextCompat.getColor(itemView.context, colorPrimaryLight))
                userLogin.setTextColor(ContextCompat.getColor(itemView.context, colorPrimaryLight))
                view.setBackgroundDrawable(getDrawable(itemView.context, R.drawable.layout_border_light))
            }
            else {
                date.text = GetDate().endAt(item.end_at)
                host.text = "-"
                date.setTextColor(ContextCompat.getColor(itemView.context, fullDark))
                host.setTextColor(ContextCompat.getColor(itemView.context, Dark))
                userLogin.setTextColor(ContextCompat.getColor(itemView.context, Dark))
                view.setBackgroundDrawable(getDrawable(itemView.context, R.drawable.layout_border_dark))
            }
            itemView.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback.onItemClicked(items[adapterPosition])
                    return@setOnLongClickListener true
                }
                return@setOnLongClickListener false
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: UserLocationEntity)
    }

}
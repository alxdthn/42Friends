package com.nalexand.friendlocationv2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocationv2.R
import com.nalexand.friendlocationv2.data.Note

class NoteRecycleAdapter(private var items: MutableList<Note>, val callback: Callback)
    : RecyclerView.Adapter<NoteRecycleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == R.layout.recycle_note_item) {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycle_note_item, parent, false))
        }
        else {
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.add_note_button, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size) R.layout.add_note_button else R.layout.recycle_note_item
    }

    override fun getItemCount(): Int {
        return (items.size + 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == items.size) {
            holder.bindAddButton()
        }
        else
            holder.bind(items[position])
    }

    fun updateData(new: MutableList<Note>){
        this.items = new

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Note) {

            val header = itemView.findViewById<TextView>(R.id.noteHeader)
            val content = itemView.findViewById<TextView>(R.id.noteContent)
            val date = itemView.findViewById<TextView>(R.id.noteDate)

            header.text = item.header
            content.text = item.note
            date.text = GetDate().noteDate(item.date)

            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback.onItemClicked(items[adapterPosition])
                }
            }
            itemView.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback.onItemLongClicked(items[adapterPosition])
                    return@setOnLongClickListener true
                }
                return@setOnLongClickListener false
            }
        }
        fun bindAddButton() {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback.onAddButtonClicked()
                }
            }
        }
    }

    interface Callback {
        fun onItemClicked(item: Note)
        fun onItemLongClicked(item: Note)
        fun onAddButtonClicked()
    }

}
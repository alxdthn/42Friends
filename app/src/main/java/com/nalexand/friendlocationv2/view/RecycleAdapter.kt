package com.nalexand.friendlocationv2.view

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.nalexand.friendlocationv2.R
import com.nalexand.friendlocationv2.R.color.*
import com.nalexand.friendlocationv2.data.AppDatabase
import com.nalexand.friendlocationv2.data.Note
import com.nalexand.friendlocationv2.data.UserEntity

fun bindNotes(itemView : View, notes : MutableList<Note>, color : Int) {
    val note1 = itemView.findViewById<TextView>(R.id.note1)
    val note2 = itemView.findViewById<TextView>(R.id.note2)
    val note3 = itemView.findViewById<TextView>(R.id.note3)
    val notesImageView = itemView.findViewById<ImageView>(R.id.notesImageView)
    val notesLinearLayout = itemView.findViewById<LinearLayout>(R.id.notesLinearLayout)


    note1.text = ""
    note2.text = ""
    note3.text = ""

    notesImageView.visibility = View.VISIBLE
    notesLinearLayout.visibility = View.INVISIBLE

    if (notes.isNotEmpty()) {
        notesImageView.visibility = View.INVISIBLE
        notesLinearLayout.visibility = View.VISIBLE
        note1.text = notes[0].header
        note1.setTextColor(color)
        if (notes.size > 1) {
            note2.text = notes[1].header
            note2.setTextColor(color)

        }
        if (notes.size > 2) {
            note3.text = notes[2].header
            note3.setTextColor(color)
        }
    }
}

fun setView(itemView: View, item: UserEntity, notes : MutableList<Note>) {
    val userLogin = itemView.findViewById<TextView>(R.id.userLogin)
    val host = itemView.findViewById<TextView>(R.id.host)
    val date = itemView.findViewById<TextView>(R.id.date)
    val view = itemView.findViewById<ConstraintLayout>(R.id.recycle_view_item)
    val notesImageView = itemView.findViewById<ImageView>(R.id.notesImageView)
    val breaker = itemView.findViewById<View>(R.id.breaker)
    val color : Int
    val background : Drawable?

    if (item.end_at == "a") {
        color = ContextCompat.getColor(itemView.context, colorPrimaryLight)
        background = getDrawable(itemView.context, R.drawable.layout_border_light)
        date.setTextColor(color)
        date.text = GetDate().beginAt(item.begin_at)
        host.text = item.host
        notesImageView.setImageResource(R.drawable.ic_bookmark_border_light)
        breaker.setBackgroundColor(color)
    }
    else {
        color = ContextCompat.getColor(itemView.context, Dark)
        background = getDrawable(itemView.context, R.drawable.layout_border_dark)
        date.setTextColor(ContextCompat.getColor(itemView.context, fullDark))
        date.text = GetDate().endAt(item.end_at)
        host.text = "-"
        notesImageView.setImageResource(R.drawable.ic_bookmark_border_dark)
        breaker.setBackgroundColor(color)
    }
    userLogin.text = item.login
    host.setTextColor(color)
    userLogin.setTextColor(color)
    view.setBackgroundDrawable(background)
    bindNotes(itemView, notes, color)
}

class ViewAdapter(private var items: MutableList<UserEntity>, private val db : AppDatabase, val callback: Callback)
    : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycle_user_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], db)
    }

    fun updateData(new: MutableList<UserEntity>){
        this.items = new

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val notesView = itemView.findViewById<ConstraintLayout>(R.id.notes)

        fun bind(item: UserEntity, db: AppDatabase) {

            Log.d("bestTAG", "bind data!")
            Log.d("bestTAG", "login ${item.login} status ${item.end_at}")

            setView(itemView, item, db.make().getNotes(item.user_id))

            itemView.setOnLongClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    callback.onItemLongClicked(items[adapterPosition])
                    return@setOnLongClickListener true
                }
                return@setOnLongClickListener false
            }
            notesView.setOnClickListener {
                callback.onNotesClicked(items[adapterPosition])
            }
        }
    }

    interface Callback {
        fun onItemLongClicked(item: UserEntity)
        fun onNotesClicked(item: UserEntity)
    }

}
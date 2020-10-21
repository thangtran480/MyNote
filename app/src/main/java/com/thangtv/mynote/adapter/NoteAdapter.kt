package com.thangtv.mynote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thangtv.mynote.inter.OnClickItemViewNote
import com.thangtv.mynote.R
import com.thangtv.mynote.data.entity.Note
import kotlinx.android.synthetic.main.note_item.view.*

class NoteAdapter(val listener: OnClickItemViewNote, var allNote: List<Note> = listOf()) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    fun setListNote(allNote: List<Note>){
        this.allNote = allNote

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteHolder(view)
    }

    override fun getItemCount(): Int {
        return allNote.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onClickItem(position)
        }
        holder.bind(allNote[position])
    }

    class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvTitle = itemView.tv_title
        private val tvDescription = itemView.tv_description

        fun bind(note: Note){
            tvTitle.text = note.title
            tvDescription.text = note.description
        }
    }
}
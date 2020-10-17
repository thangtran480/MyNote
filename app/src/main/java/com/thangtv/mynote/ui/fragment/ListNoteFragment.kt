package com.thangtv.mynote.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thangtv.mynote.inter.OnClickItemViewNote
import com.thangtv.mynote.R
import com.thangtv.mynote.adapter.NoteAdapter
import com.thangtv.mynote.data.entity.Note
import com.thangtv.mynote.viewmodel.ListNoteViewModel
import kotlinx.android.synthetic.main.fragment_list_note.*

class ListNoteFragment : Fragment(), OnClickItemViewNote {

    companion object{
        const val KEY_NOTE = "com.thangtv.mynote.ui.note"

    }

    private lateinit var listNoteViewModel: ListNoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
        listNoteViewModel = ViewModelProviders.of(requireActivity()).get(ListNoteViewModel::class.java)
        noteAdapter = NoteAdapter(this)

        initView()
        listChangeLiveDate()

    }

    private fun initView(){
        fab_add.setOnClickListener {
            navController.navigate(R.id.action_ListNoteFragment_to_AddNoteFragment)
        }
        rv_list_note.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listNoteViewModel.delete(noteAdapter.allNote[viewHolder.adapterPosition])
            }

        }).attachToRecyclerView(rv_list_note)
    }
    private fun listChangeLiveDate(){
        listNoteViewModel.allNotes.observe(viewLifecycleOwner, Observer {
            noteAdapter.setListNote(it)
        })
    }

    override fun onClickItem(note: Note) {
        val bundle = bundleOf(Pair(KEY_NOTE, note))
        navController.navigate(R.id.action_ListNoteFragment_to_AddNoteFragment, bundle)
    }

    override fun onClickItem(position: Int) {
        val bundle = bundleOf(Pair(KEY_NOTE, position))
        navController.navigate(R.id.action_ListNoteFragment_to_AddNoteFragment, bundle)
    }
}
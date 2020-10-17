package com.thangtv.mynote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thangtv.mynote.data.database.NoteRepository
import com.thangtv.mynote.data.entity.Note
import kotlinx.coroutines.launch

class ListNoteViewModel(application: Application) : AndroidViewModel(application){
    private var noteRepository: NoteRepository =
        NoteRepository(application)
    var allNotes: LiveData<List<Note>> = noteRepository.allNote


    fun insert(note: Note){
        viewModelScope.launch {
            noteRepository.insert(note)
        }
    }

    fun update(note: Note){
        viewModelScope.launch {
            noteRepository.update(note)
        }
    }

    fun delete(note: Note){
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }
    }
    fun deleteAllNote(note: Note){
        viewModelScope.launch {
            noteRepository.deleteAllNote()
        }
    }


}
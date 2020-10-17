package com.thangtv.mynote.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.thangtv.mynote.data.dao.NoteDAO
import com.thangtv.mynote.data.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository {
    private var noteDAO : NoteDAO
    var allNote: LiveData<List<Note>>

    constructor(application: Application){
        noteDAO = NoteDatabase.invoke(application.applicationContext).noteDAO()
        allNote = noteDAO.getAllNote()
    }

    suspend fun insert(note: Note) = withContext(Dispatchers.IO){
        noteDAO.insert(note)
    }

    suspend fun update(note: Note) = withContext(Dispatchers.IO){
        noteDAO.update(note)
    }

    suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO){
        noteDAO.delete(note)
    }

    suspend fun deleteAllNote() = withContext(Dispatchers.IO){
        noteDAO.deleteAllNote()
    }

}
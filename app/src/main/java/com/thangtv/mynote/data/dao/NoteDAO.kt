package com.thangtv.mynote.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thangtv.mynote.data.entity.Note
import com.thangtv.mynote.util.ConverterDateTime

@Dao
@TypeConverters(ConverterDateTime::class)
interface NoteDAO {

    @Insert
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM note_table")
    fun deleteAllNote()

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    fun getAllNote(): LiveData<List<Note>>

}
package com.thangtv.mynote.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thangtv.mynote.data.dao.NoteDAO
import com.thangtv.mynote.data.entity.Note
import com.thangtv.mynote.util.ConverterDateTime

@TypeConverters(ConverterDateTime::class)
@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase: RoomDatabase(){

    abstract fun noteDAO(): NoteDAO

    companion object{
        @Volatile private var instance: NoteDatabase? = null
        operator fun invoke(context: Context) = instance
            ?: synchronized(Any()){
            instance
                ?: buildDatabase(
                    context
                )
                    .also { instance = it }
        }

        private fun buildDatabase(context: Context) : NoteDatabase = Room.databaseBuilder(context, NoteDatabase::class.java, "note_list")
                                                                    .fallbackToDestructiveMigration()
//                                                                    .addCallback(roomCallBack)
                                                                    .build()

        private val roomCallBack = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val noteDAO = instance?.noteDAO()
            }
        }

    }

}
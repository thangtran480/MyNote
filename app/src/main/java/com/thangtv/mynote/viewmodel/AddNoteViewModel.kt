package com.thangtv.mynote.viewmodel

import android.app.AlarmManager
import android.app.Application
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.thangtv.mynote.broadcast.MyNotificationPublisher
import com.thangtv.mynote.data.database.NoteRepository
import com.thangtv.mynote.data.database.StatusRepository
import com.thangtv.mynote.data.entity.Note
import com.thangtv.mynote.ui.popup.CustomTimePickerDialog
import com.thangtv.mynote.util.Utils
import kotlinx.coroutines.launch
import java.util.*

@Suppress("DEPRECATION")
class AddNoteViewModel(application: Application) : AndroidViewModel(application){

    // NoteRepository
    var noteRepository: NoteRepository = NoteRepository(application)

    //List note
    var allNotes: LiveData<List<Note>> = noteRepository.allNote

    // Status process
    var statusRepository: MutableLiveData<StatusRepository> = MutableLiveData()


    lateinit var startTimeAlertPicker: CustomTimePickerDialog
    lateinit var endTimeAlertPicker: CustomTimePickerDialog
    lateinit var startDateAlertPicker: DatePickerDialog
    lateinit var endDateAlertPicker: DatePickerDialog

    lateinit var note: Note

    init {
        statusRepository.value = StatusRepository(StatusRepository.NO, "")
    }

    fun insert(context: Context){
        viewModelScope.launch {
            statusRepository.value = StatusRepository(StatusRepository.PROCESSING)
            try {
                getNoteValue()
                if (note.id != 0){
                    noteRepository.update(note)
                }else{
                    noteRepository.insert(note)
                }
                setNotificationScheduler(context, note)
                statusRepository.value = StatusRepository(StatusRepository.SUCCESSFUL)
            }catch (e: Exception){
                statusRepository.value = StatusRepository(StatusRepository.ERROR, e.message.toString())
            }
        }
    }

    private fun setNotificationScheduler(context: Context, note: Note) {
        val currentDate = Calendar.getInstance()
        val date = note.startDateAlert

        if (currentDate.time >= date){
            val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, MyNotificationPublisher::class.java)
            intent.putExtra("title", note.title)
            intent.putExtra("description", note.description)
            val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.time, pendingIntent)
        }
    }

    private fun startDateTimeAlert(): Date{
        return convertTime(startTimeAlertPicker, startDateAlertPicker)
    }
    private fun endDateTimeAlert() : Date{
        return convertTime(endTimeAlertPicker, endDateAlertPicker)
    }

    private fun convertTime(timePicker: CustomTimePickerDialog, datePicker: DatePickerDialog): Date{

        val calendar = Calendar.getInstance()
        calendar.set(datePicker.datePicker.year, datePicker.datePicker.month, datePicker.datePicker.dayOfMonth, timePicker.hour, timePicker.minute)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.time
    }

    fun setNoteValue(note: Note?){
        if (note == null){
            this.note = initNote()
        }else{
            this.note = note
        }
    }

    private fun initNote(): Note{
        return Note(
            title               = "",
            description         = "",
            startDateAlert      = startDateTimeAlert(),
            endDateAlert        = endDateTimeAlert(),
            timeTransport       = "",
            typeAlert           = 0,
            status              = 0,
            note                = "",
            createDate          = Date(System.currentTimeMillis()),
            updateDate          = Date(System.currentTimeMillis()),
            active              = 1
        )
    }

    private fun getNoteValue(){
        this.note.startDateAlert = startDateTimeAlert()
        this.note.endDateAlert = endDateTimeAlert()
        this.note.updateDate = Date(System.currentTimeMillis())
    }

}
package com.thangtv.mynote.ui.popup

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import java.lang.reflect.Field


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class CustomTimePickerDialog(
    context: Context?,
    listener: OnTimeSetListener?,
    hourOfDay: Int,
    minute: Int,
    is24HourView: Boolean
) :
    TimePickerDialog(context, listener, hourOfDay, minute, is24HourView) {

    override fun onTimeChanged(view: TimePicker, hourOfDay: Int, minute: Int) {
        super.onTimeChanged(view, hourOfDay, minute)
        hour = hourOfDay
        this.minute = minute
    }

    var hour = hourOfDay
        private set
    var minute = minute
        private set

    init {
        try {
            val superClass: Class<*> = javaClass.superclass
            val timePickerField: Field = superClass.getDeclaredField("mTimePicker")
            timePickerField.isAccessible = true
            val timePicker = timePickerField.get(this) as TimePicker
            timePicker.setOnTimeChangedListener(this)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }
}
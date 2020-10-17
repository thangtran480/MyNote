package com.thangtv.mynote.data.entity

import androidx.room.*
import com.thangtv.mynote.util.ConverterDateTime
import java.util.*

@Entity(tableName = "note_table")
data class Note (

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "priority")
    var priority: Int = 0,

    @ColumnInfo(name = "start_date_alert")
    @TypeConverters(ConverterDateTime::class)
    var startDateAlert: Date,

    @ColumnInfo(name = "end_date_alert")
    @TypeConverters(ConverterDateTime::class)
    var endDateAlert: Date,

    @ColumnInfo(name = "repeat")
    var repeat: Int = 0,

    @ColumnInfo(name = "time_transport")
    var timeTransport: String,

    @ColumnInfo(name = "type_alert")
    var typeAlert: Int,

    @ColumnInfo(name = "status")
    var status: Int,

    @ColumnInfo(name = "note")
    var note: String,

    @ColumnInfo(name = "create_date")
    @TypeConverters(ConverterDateTime::class)
    var createDate: Date,

    @ColumnInfo(name = "update_date")
    @TypeConverters(ConverterDateTime::class)
    var updateDate: Date,

    @ColumnInfo(name = "active")
    var active: Int
){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
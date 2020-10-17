package com.thangtv.mynote.util

import androidx.room.TypeConverter
import java.util.*

class ConverterDateTime {

    @TypeConverter
    fun toDate(dateLong : Long? ) : Date? {
        return if(dateLong == null){
            null
        }else{
            Date(dateLong)
        };
    }

    @TypeConverter
    fun fromDate(date : Date? ): Long?{
        return date?.time
    }

}
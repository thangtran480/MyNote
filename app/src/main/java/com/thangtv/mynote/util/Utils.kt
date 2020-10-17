package com.thangtv.mynote.util

import android.content.Context
import com.thangtv.mynote.R

object Utils {
    fun convertRepeat(context: Context, value: Int): String{

        val id =  when (value){
            0 ->{
                R.string.repeat_none
            }
            1 ->{
                R.string.repeat_daily
            }
            2 ->{
                R.string.repeat_every_week
            }
            3 ->{
                R.string.repeat_monthly
            }
            4 ->{
                R.string.repeat_yearly
            }
            else ->{
                R.string.repeat_none
            }
        }

        return context.resources.getString(id)
    }
}
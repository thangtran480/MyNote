package com.thangtv.mynote.data.database

class StatusRepository(var code: Int, var message: String = "") {
    companion object{
        const val NO            = 0
        const val START         = 1
        const val SUCCESSFUL    = 2
        const val ERROR       = 3
        const val PROCESSING    = 4
    }

}
package com.thangtv.mynote.inter

import com.thangtv.mynote.data.entity.Note

interface OnClickItemViewNote {
    fun onClickItem(note: Note)
    fun onClickItem(position: Int)
}
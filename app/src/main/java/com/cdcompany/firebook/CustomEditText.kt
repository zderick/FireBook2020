package com.cdcompany.firebook

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.KeyEvent.*
import androidx.appcompat.widget.AppCompatEditText


class CustomEditText : AppCompatEditText {
    private lateinit var  backKeyListener: BackKeyListener

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    interface BackKeyListener {
        fun onBackPressed()
    }
    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KEYCODE_BACK &&
            event.action === ACTION_UP
        ) {
            // do your stuff
            backKeyListener.onBackPressed()
            false
        } else super.dispatchKeyEvent(event)
    }
    fun setBackKeyListener(backKeyListener: BackKeyListener) {
        this.backKeyListener = backKeyListener
    }}


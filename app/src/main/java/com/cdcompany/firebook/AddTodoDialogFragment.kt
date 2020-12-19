package com.cdcompany.firebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

class AddTodoDialogFragment : DialogFragment() {
    
    companion object {
        val TAG : String = "AddTodoDialogFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.add_todo_dialog, container, false)
//        return super.onCreateView(inflater, container, savedInstanceState)
    }
}
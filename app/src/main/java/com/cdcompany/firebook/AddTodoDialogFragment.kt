package com.cdcompany.firebook

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment


class AddTodoDialogFragment : DialogFragment(), DialogInterface.OnClickListener {
    
    companion object {
        val TAG : String = "AddTodoDialogFragment"
    }
    
    private lateinit var editText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        editText = EditText(activity)

        val alertDialog : AlertDialog =  AlertDialog.Builder(activity)
            .setTitle("Add Todo")
            .setMessage("Please Enter Your Todo")
            .setPositiveButton("OK", this)
            .setNegativeButton("CANCEL", null)
            .setView(editText)
            .create()

        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
        
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled =
                    !TextUtils.isEmpty(editText.text.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        editText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    passBackTodo()
                    dismiss()
                    return true
                }
                return false
            }

        })
        
        return alertDialog
    }
    
    override fun onClick(p0: DialogInterface?, p1: Int) {
        passBackTodo()
    }
    
    private fun passBackTodo() {
        val todoString = editText.text.toString()
        if (!TextUtils.isEmpty(todoString)) {
            val callingActivity = activity as MainActivity
            callingActivity.onTodoAddedText(todoString)
        }
    }
}
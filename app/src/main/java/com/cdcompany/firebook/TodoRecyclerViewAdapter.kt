package com.cdcompany.firebook

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class TodoRecyclerViewAdapter(
    private val todoList: List<Todo>,
    private val dataController: DataController
) : RecyclerView.Adapter<TodoRecyclerViewAdapter.TodoViewHolder>() {
    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: MyEditText = view.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.todo, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo: Todo = todoList[position]
        val editText: MyEditText = holder.description
        editText.setText(todo.description)
        attachOnFocusChangeListener(editText, todo)
        attachOnEditActionListener(editText, todo)
        editText.setBackKeyListener(object : MyEditText.BackKeyListener {
            override fun onBackPressed() {
                editText.clearFocus()
                editText.setText(todo.description)
            }

        })
    }

    private fun attachOnFocusChangeListener(editText: EditText, todo: Todo) {
        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                editText.setText(todo.description)
            }
        }
    }

    private fun attachOnEditActionListener(editText: EditText, todo: Todo) {
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                editText.onFocusChangeListener = null
                editText.clearFocus()
                attachOnFocusChangeListener(editText, todo)
                todo.description = editText.text.toString()
                dataController.updateTodo(todo) }
            false
        }
    }
        
//        editText.setOnKeyListener { _, keyCode, event ->
//            
//
//            }
//        }
//        EditText.setOnKeyListener({ v, keyCode, event ->
//            if (event.getAction() === android.view.KeyEvent.ACTION_DOWN) {
//                when (keyCode) {
//                    KeyEvent.KEYCODE_BACK -> getPresenter().onBackPressed()
//                }
//            }
//            false
//        })
//    }
}
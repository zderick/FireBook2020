package com.cdcompany.firebook

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class TodoRecyclerViewAdapter(
    private val todoList: List<Todo>,
    private val dataController: DataController
) : RecyclerView.Adapter<TodoRecyclerViewAdapter.TodoViewHolder>() {
    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val description: CustomEditText = view.findViewById(R.id.description)
        val completed : CheckBox = view.findViewById(R.id.checkbox)
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
        val editText: CustomEditText = holder.description
        val completedBox: CheckBox = holder.completed
        editText.setText(todo.description)
        completedBox.isChecked = todo.complete
        attachOnFocusChangeListener(editText, todo)
        attachOnEditActionListener(editText, todo)
        attachBackPressedListener(editText, todo)
        attachCompletedListener(completedBox, todo)
    }

    private fun attachCompletedListener(checkBox: CheckBox, todo : Todo) {
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked != todo.complete) {
                dataController.updateComplete(todo, isChecked)
                checkBox.setOnCheckedChangeListener(null)
            }
        }
    }

    private fun attachBackPressedListener(editText: CustomEditText, todo: Todo) {
        editText.setBackKeyListener(object : CustomEditText.BackKeyListener {
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
}
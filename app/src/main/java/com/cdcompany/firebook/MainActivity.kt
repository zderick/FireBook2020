package com.cdcompany.firebook

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.todo_fragment_layout.*


class MainActivity : AppCompatActivity() {

    private lateinit var dateController: DataController
    private lateinit var recyclerView: RecyclerView;
    private lateinit var todoMap: MutableMap<String, Todo>
    private lateinit var dialogFragment : AddTodoDialogFragment

    interface DataCallback {
        fun onTodoAdded(key: String, todo: Todo)
        fun onTodoDeleted(key: String)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))
        val todoList: MutableList<Todo> = mutableListOf()
        todoMap = mutableMapOf()
        recyclerView = findViewById(R.id.recyclerview)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = layoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.adapter = TodoRecyclerViewAdapter(todoList)
        dateController = DataController(object : DataCallback {
            override fun onTodoAdded(key: String, todo: Todo) {
                todoList.add(todo)
                todoMap.putIfAbsent(key, todo)
                (recyclerView.adapter as TodoRecyclerViewAdapter).notifyDataSetChanged()
                recyclerView.scrollToPosition(todoList.size - 1)
            }

            override fun onTodoDeleted(key: String) {
                todoList.remove(todoMap.remove(key))
                (recyclerView.adapter as TodoRecyclerViewAdapter).notifyDataSetChanged()

            }
        })


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            dialogFragment = AddTodoDialogFragment()
            dialogFragment.show(supportFragmentManager, AddTodoDialogFragment.TAG)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onTodoAddedText(todoText: String) {
        dateController.addTodo(todoText)
        // TODO add your implementation.
    }
}
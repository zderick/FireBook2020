package com.cdcompany.firebook

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_first.*


class MainActivity : AppCompatActivity() {

    private var testNumber: Int = 1
    private lateinit var dateController : DataController
    private lateinit var recyclerView : RecyclerView;
    
    interface DataCallback {
        fun onDataReady(todo : Todo?)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(findViewById(R.id.toolbar))

        val todo : Todo = Todo("hi", 1, true)
        val todo2 : Todo = Todo("helloooo", 0, true)
        val todoList : MutableList<Todo> = mutableListOf(todo, todo2)
        recyclerView = findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = TodoRecyclerViewAdapter(todoList)
        dateController = DataController(object : DataCallback {
            override fun onDataReady(todo: Todo?) {
                Log.d("Derick", "is this called?")
                todo?.let { todoList.add(it) }
                (recyclerView.adapter as TodoRecyclerViewAdapter).notifyDataSetChanged()
            }
        })


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            dateController.addTodo()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
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
}
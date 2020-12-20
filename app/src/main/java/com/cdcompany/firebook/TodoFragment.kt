package com.cdcompany.firebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_fragment_layout.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TodoFragment : Fragment() {
    
    private lateinit var recyclerView : RecyclerView;

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.todo_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoList : List<Todo> = listOf()
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = TodoRecyclerViewAdapter(todoList)
    }
}
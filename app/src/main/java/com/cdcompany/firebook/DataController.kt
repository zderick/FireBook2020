package com.cdcompany.firebook

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.time.Clock

class DataController(private val dataCallback: MainActivity.DataCallback) {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var childEventListener: ChildEventListener
    private lateinit var clock: Clock
    private val TEMP_KEY: String = ""

    init {
        databaseReference = Firebase.database.reference.child("test")
        attachListener()
        databaseReference.addChildEventListener(childEventListener)
        clock = Clock.systemUTC()

    }

    fun addTodo(todo: String) {

        val key: String = databaseReference.push().key ?: TEMP_KEY

        if (key != TEMP_KEY) {
            databaseReference.child(key)
                .setValue(Todo(key, todo, clock.millis(), false /* isCompleted */))
        }


    }

    fun updateTodo(todo: Todo) {
        databaseReference.child(todo.key).setValue(todo)
    }

    fun attachListener() {
        childEventListener = object : ChildEventListener {
            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase rules. For more information on securing
             * your data, see: [ Security Quickstart](https://firebase.google.com/docs/database/security/quickstart)
             *
             * @param error A description of the error that occurred
             */
            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
            }

            /**
             * This method is triggered when a child location's priority changes. See [ ][DatabaseReference.setPriority] and [Ordered Data](https://firebase.google.com/docs/database/android/retrieve-data#data_order) for more information on priorities and ordering data.
             *
             * @param snapshot An immutable snapshot of the data at the location that moved.
             * @param previousChildName The key name of the sibling location ordered before the child
             * location. This will be null if this location is ordered first.
             */
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
            }

            /**
             * This method is triggered when the data at a child location has changed.
             *
             * @param snapshot An immutable snapshot of the data at the new data at the child location
             * @param previousChildName The key name of sibling location ordered before the child. This will
             * be null for the first child node of a location.
             */
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val todo: Todo? = snapshot.getValue<Todo>()
                val key: String? = snapshot.key
                if (key != null && todo != null) {
                    dataCallback.onTodoChanged(key, todo)
                }
            }

            /**
             * This method is triggered when a new child is added to the location to which this listener was
             * added.
             *
             * @param snapshot An immutable snapshot of the data at the new child location
             * @param previousChildName The key name of sibling location ordered before the new child. This
             * will be null for the first child node of a location.
             */
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val todo: Todo? = snapshot.getValue<Todo>()
                val key: String? = snapshot.key
                if (key != null && todo != null) {
                    dataCallback.onTodoAdded(key, todo)
                }

            }

            /**
             * This method is triggered when a child is removed from the location to which this listener was
             * added.
             *
             * @param snapshot An immutable snapshot of the data at the child that was removed.
             */
            override fun onChildRemoved(snapshot: DataSnapshot) {
                val key = snapshot.key
                if (key != null) {
                    dataCallback.onTodoDeleted(key)
                }
            }
        }
    }
}


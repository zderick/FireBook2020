package com.cdcompany.firebook

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.todo_fragment_layout.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var dateController: DataController
    private lateinit var recyclerView: RecyclerView
    private lateinit var todoMap: MutableMap<String, Todo>
    private lateinit var dialogFragment: AddTodoDialogFragment
    private lateinit var animationOverlay: FrameLayout
    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0

    interface DataCallback {
        fun onTodoAdded(key: String, todo: Todo)
        fun onTodoChanged(key: String, todo: Todo)
        fun onTodoDeleted(key: String)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSoundPool()
        animationOverlay = findViewById(R.id.animationOverlay)

        val todoList: MutableList<Todo> = mutableListOf()
        todoMap = mutableMapOf()
        recyclerView = findViewById(R.id.recyclerview)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = layoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        dateController = DataController(object : DataCallback {
            override fun onTodoAdded(key: String, todo: Todo) {
                if (!todo.complete) {
                    todoList.add(todo)
                    todoMap.putIfAbsent(key, todo)
                    (recyclerView.adapter as TodoRecyclerViewAdapter).notifyDataSetChanged()
                    recyclerView.scrollToPosition(todoList.size - 1)
                }
            }

            override fun onTodoChanged(key: String, todo: Todo) {
                val myTodo : Todo? = todoMap[key]
                todoMap[key]?.description = todo.description

                if (todoMap[key]?.complete != todo.complete) {
                    if (todo.complete) {
                        todoList.remove(todoMap[key])
                    } else {
                        todoList.add(todo)
                    }
                    todoMap[key]?.complete = todo.complete
                }
                (recyclerView.adapter as TodoRecyclerViewAdapter).notifyDataSetChanged()
            }

            override fun onTodoDeleted(key: String) {
                todoList.remove(todoMap.remove(key))
                (recyclerView.adapter as TodoRecyclerViewAdapter).notifyDataSetChanged()
            }
        })

        recyclerView.adapter = TodoRecyclerViewAdapter(todoList, dateController) {
            showCelebrationConfetti()
        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            dialogFragment = AddTodoDialogFragment()
            dialogFragment.show(supportFragmentManager, AddTodoDialogFragment.TAG)
        }
    }

    private fun initSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        soundId = soundPool.load(this, R.raw.wechat_money, 1)
    }

    private fun showCelebrationConfetti() {
        // Play celebratory sound
        if (soundId != 0) {
            soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
        }

        // Drop celebratory confetti from HappyLemon
        val count = 50
        val icons = listOf(
            R.drawable.ic_gold_coin,
            R.drawable.ic_gold_dollar,
            R.drawable.ic_gold_ingot,
            R.drawable.ic_red_envelope
        )
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels.toFloat()

        for (i in 0 until count) {
            val img = ImageView(this)
            img.setImageResource(icons.random())
            img.isClickable = false
            img.isFocusable = false

            val sizeDp = 24 + Random.nextInt(16)
            val sizePx = (sizeDp * resources.displayMetrics.density).toInt()

            val params = FrameLayout.LayoutParams(sizePx, sizePx)
            img.layoutParams = params
            img.x = Random.nextInt(Math.max(1, screenWidth - sizePx)).toFloat()
            img.y = -sizePx.toFloat()

            animationOverlay.addView(img)

            val swayRange = 100f
            val swayTarget = img.x + (Random.nextFloat() * swayRange * 2 - swayRange)

            img.animate()
                .translationY(screenHeight + sizePx)
                .translationX(swayTarget)
                .rotation(Random.nextInt(1080).toFloat())
                .setDuration(3000 + Random.nextLong(2000))
                .withEndAction { animationOverlay.removeView(img) }
                .start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::soundPool.isInitialized) {
            soundPool.release()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onTodoAddedText(todoText: String) {
        dateController.addTodo(todoText)
    }
}
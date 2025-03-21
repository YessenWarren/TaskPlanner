package com.example.taskplannerapp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addButton: FloatingActionButton
    private lateinit var sortButton: Button
    private lateinit var categorySpinner: Spinner
    private lateinit var taskAdapter: TaskAdapter
    private val taskDao by lazy { TaskDatabase.getDatabase(this).taskDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        addButton = findViewById(R.id.addButton)
        sortButton = findViewById(R.id.sortButton)
        categorySpinner = findViewById(R.id.categorySpinner)

        taskAdapter = TaskAdapter(taskDao)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        val categories = listOf("All", "Work", "Personal", "Shopping", "Other")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter

        addButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(
                this,
                R.anim.slide_in_right,
                R.anim.slide_out_left
            )
            startActivity(intent, options.toBundle())
        }

        sortButton.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()
            loadTasks(selectedCategory)
        }

        loadTasks()
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
    }

    private fun loadTasks(category: String? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            val tasks = if (category.isNullOrEmpty() || category == "All") {
                taskDao.getAllTasks()
            } else {
                taskDao.getAllTasks().filter { it.category == category }
            }

            CoroutineScope(Dispatchers.Main).launch {
                taskAdapter.setTasks(tasks)
            }
        }
    }
}

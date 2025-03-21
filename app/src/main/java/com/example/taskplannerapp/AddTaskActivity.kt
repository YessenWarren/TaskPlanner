package com.example.taskplannerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var saveButton: Button
    private val taskDao by lazy { TaskDatabase.getDatabase(this).taskDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        dateEditText = findViewById(R.id.dateEditText)
        categoryEditText = findViewById(R.id.categoryEditText)
        amountEditText = findViewById(R.id.amountEditText)
        saveButton = findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val date = dateEditText.text.toString()
            val category = categoryEditText.text.toString()
            val amount = amountEditText.text.toString()

            if (title.isEmpty() || description.isEmpty() || date.isEmpty() || category.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val task = Task(title = title,
                    description = description,
                    date = date,
                    category = category,
                    amount = amount.toDouble()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    taskDao.insertTask(task)
                    finish()
                }
            }
        }
    }
}

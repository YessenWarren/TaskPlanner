package com.example.taskplannerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTaskActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var categoryEditText: EditText
    private lateinit var amountEditText: EditText
    private lateinit var saveButton: Button
    private val taskDao by lazy { TaskDatabase.getDatabase(this).taskDao() }
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        titleEditText = findViewById(R.id.editTitleEditText)
        descriptionEditText = findViewById(R.id.editDescriptionEditText)
        dateEditText = findViewById(R.id.editDateEditText)
        categoryEditText = findViewById(R.id.editCategoryEditText)
        amountEditText = findViewById(R.id.editAmountEditText)
        saveButton = findViewById(R.id.editSaveButton)

        taskId = intent.getIntExtra("taskId", -1)
        val taskTitle = intent.getStringExtra("taskTitle")
        val taskDescription = intent.getStringExtra("taskDescription")
        val taskDate = intent.getStringExtra("taskDate")
        val taskCategory = intent.getStringExtra("taskCategory")
        val taskAmount = intent.getDoubleExtra("taskAmount", 0.0)

        titleEditText.setText(taskTitle)
        descriptionEditText.setText(taskDescription)
        dateEditText.setText(taskDate)
        categoryEditText.setText(taskCategory)
        amountEditText.setText(taskAmount.toString())

        saveButton.setOnClickListener {
            val updatedTitle = titleEditText.text.toString()
            val updatedDescription = descriptionEditText.text.toString()
            val updatedDate = dateEditText.text.toString()
            val updatedCategory = categoryEditText.text.toString()
            val updatedAmount = amountEditText.text.toString()

            if (updatedTitle.isEmpty() || updatedDescription.isEmpty() || updatedDate.isEmpty() || updatedCategory.isEmpty() || updatedAmount.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                if (taskId != -1) {
                    val updatedTask = Task(
                        id = taskId,
                        title = updatedTitle,
                        description = updatedDescription,
                        date = updatedDate,
                        category = updatedCategory,
                        amount = updatedAmount.toDouble()
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        taskDao.updateTask(updatedTask)
                        finish()
                    }
                }
            }
        }
    }

}

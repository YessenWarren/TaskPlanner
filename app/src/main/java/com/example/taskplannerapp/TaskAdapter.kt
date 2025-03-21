package com.example.taskplannerapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskAdapter(private val taskDao: TaskDao) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var taskList = listOf<Task>()

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.taskTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.taskDescription)
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.taskCheckBox)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.titleTextView.text = task.title
        holder.descriptionTextView.text = task.description
        holder.taskCheckBox.isChecked = task.completed

        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.completed = isChecked
            CoroutineScope(Dispatchers.IO).launch {
                taskDao.updateTask(task)
            }
        }

        holder.deleteButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                taskDao.deleteTask(task)
                taskList = taskList.toMutableList().apply { removeAt(position) }
                CoroutineScope(Dispatchers.Main).launch {
                    notifyDataSetChanged()
                }
            }
        }

        val editTaskIntent = View.OnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, EditTaskActivity::class.java).apply {
                putExtra("taskId", task.id)
                putExtra("taskTitle", task.title)
                putExtra("taskDescription", task.description)
                putExtra("taskDate", task.date)
                putExtra("taskCategory", task.category)
                putExtra("taskAmount", task.amount)
            }
            context.startActivity(intent)
        }

        holder.titleTextView.setOnClickListener(editTaskIntent)
        holder.descriptionTextView.setOnClickListener(editTaskIntent)
    }

    override fun getItemCount(): Int = taskList.size

    fun setTasks(tasks: List<Task>) {
        taskList = tasks
        notifyDataSetChanged()
    }
}

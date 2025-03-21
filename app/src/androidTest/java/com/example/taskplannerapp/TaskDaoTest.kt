package com.example.taskplannerapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var db: TaskDatabase
    private lateinit var taskDao: TaskDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        db = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java).build()
        taskDao = db.taskDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertTaskAndRetrieve() = runBlocking {
        val task = Task(title = "Test Task", description = "Test Description", date = "2024-12-18", category = "Work", amount = 50.0)
        taskDao.insertTask(task)
        val tasks = taskDao.getAllTasks()
        assertEquals(1, tasks.size)
        assertEquals("Test Task", tasks[0].title)
    }

    @Test
    fun deleteTask() = runBlocking {
        val task = Task(title = "Test Task", description = "Test Description", date = "2024-12-18", category = "Work", amount = 50.0)
        taskDao.insertTask(task)
        taskDao.deleteTask(task)
        val tasks = taskDao.getAllTasks()
        assertTrue(tasks.isEmpty())
    }
}

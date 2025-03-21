package com.example.taskplannerapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun addTaskTest() {
        onView(withId(R.id.addButton)).perform(click())

        onView(withId(R.id.titleEditText)).perform(typeText("New Task"), closeSoftKeyboard())
        onView(withId(R.id.descriptionEditText)).perform(typeText("New Description"), closeSoftKeyboard())
        onView(withId(R.id.dateEditText)).perform(typeText("2024-12-18"), closeSoftKeyboard())
        onView(withId(R.id.categoryEditText)).perform(typeText("Work"), closeSoftKeyboard())
        onView(withId(R.id.amountEditText)).perform(typeText("100.0"), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        onView(withText("New Task")).check(matches(isDisplayed()))
    }
}

package com.redmadrobot.sample

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Sample app UI tests.
 *
 * @author taflanidi
 */
@RunWith(AndroidJUnit4::class)
class SampleInstrumentedTest {
    @get: Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCurrencyField_enter12345point678_showsDollar12comma345point678() {
        onView(withId(R.id.currency_edit_text))
            .perform(typeText("12345.678"))
            .check(matches(hasValueEqualTo("$12,345.678")))
    }

    @Test
    fun testDateField_enter1point021989_shows1point02point1989() {
        onView(withId(R.id.date_edit_text))
            .perform(typeText("1.021989"))
            .check(matches(hasValueEqualTo("1.02.1989")))
    }

    @Test
    fun testPhoneField_enter38123456789_showsPlus380SpaceBracket12SpaceBracketSpace345Dash67Dash89() {
        onView(withId(R.id.phone_edit_text))
            .perform(typeText("381234567890"))
            .check(matches(hasValueEqualTo("+380 (12) 345-67-89")))
    }

    private fun hasValueEqualTo(content: String): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            override fun describeTo(description: Description) {
                description.appendText("Has EditText/TextView the value:  $content")
            }

            override fun matchesSafely(view: View?): Boolean {
                if (view !is TextView && view !is EditText) {
                    return false
                }
                val text: String = if (view is TextView) {
                    view.text.toString()
                } else {
                    (view as EditText).text.toString()
                }
                return text.equals(content, ignoreCase = true)
            }
        }
    }
}
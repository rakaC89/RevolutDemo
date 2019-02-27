package com.raka.revolutdemo.presentation.feature.conversion

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.raka.revolutdemo.R
import com.raka.revolutdemo.utils.MockedInterceptor
import com.raka.revolutdemo.utils.atPosition
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConversionActivityTest {

    @get:Rule
    val activityRule =
        ActivityTestRule(ConversionActivity::class.java, false, false)

    @After
    fun teardown() {
        MockedInterceptor.mockedResponseMap.clear()
        MockedInterceptor.mockedErrorResponseMap.clear()
    }

    private fun startActivity(){
        activityRule.launchActivity(Intent())
    }

    @Test
    fun onStart_show33Currencies() {
        MockedInterceptor.mockedResponseMap["latest?base=EUR"] = "/base_eur.json"
        startActivity()
        onView(withId(R.id.currencyRecyclerView)).check(RecyclerViewItemCount(33))
    }

    @Test
    fun onErrorResponse_showErrorSnackBar() {
        MockedInterceptor.mockedErrorResponseMap["latest?base=SPAM"] = "Not Found"
        startActivity()

        onView(withId(R.id.loadingView)).check(matches(isDisplayed()))
        Thread.sleep(3000)
        onView(withId(R.id.snackbar_text)).check(matches(withText("Something went wrong")))
    }

    @Test
    fun baseCurrency_alwaysDisplayedOnTop_withDefaultValue100() {
        MockedInterceptor.mockedResponseMap["latest?base=EUR"] = "/base_eur.json"
        startActivity()

        onView(withId(R.id.currencyRecyclerView)).check(matches(atPosition(0, hasDescendant(withText("EUR")))))
        onView(withId(R.id.currencyRecyclerView)).check(matches(atPosition(0, hasDescendant(withText("Euro")))))
        onView(withId(R.id.currencyRecyclerView)).check(matches(atPosition(0, hasDescendant(withText("100")))))
    }

    @Test
    fun onCurrencyClicked_movesToTop() {
        MockedInterceptor.mockedResponseMap["latest?base=EUR"] = "/base_eur.json"
        startActivity()

        onView(withId(R.id.currencyRecyclerView)).check(matches(atPosition(0, hasDescendant(withText("EUR")))))
        onView(withId(R.id.currencyRecyclerView)).perform(scrollToPosition<ConversionAdapter.ViewHolder>(30))
        onView(withId(R.id.currencyRecyclerView)).check(matches(atPosition(30, hasDescendant(withText("TRY")))))

        onView(withId(R.id.currencyRecyclerView)).perform(actionOnItemAtPosition<ConversionAdapter.ViewHolder>(30, click()))
        onView(withId(R.id.currencyRecyclerView)).check(matches(atPosition(0, hasDescendant(withText("EUR")))))
    }
}

class RecyclerViewItemCount(private val expectedCount: Int) : ViewAssertion {

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        Assert.assertThat(adapter!!.itemCount, CoreMatchers.`is`(expectedCount))
    }
}
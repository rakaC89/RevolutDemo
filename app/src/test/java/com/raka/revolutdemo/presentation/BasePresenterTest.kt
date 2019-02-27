package com.raka.revolutdemo.presentation

import org.junit.Test

class BasePresenterTest {
    private val sut = TestPresenter()
    private val testView = TestView()

    @Test(expected = IllegalStateException::class)
    @Throws(Exception::class)
    fun testViewAttachedOnce() {
        sut.onViewAttached(testView)
        sut.onViewAttached(testView)
    }

    @Test
    @Throws(Exception::class)
    fun testViewDetachedSuccessfully() {
        sut.onViewAttached(testView)
        sut.onViewDetached()
        sut.onViewAttached(testView)
    }

    private class TestPresenter : BasePresenter<TestPresenter.View>() {

        internal interface View : BasePresenter.View
    }

    private inner class TestView : TestPresenter.View
}
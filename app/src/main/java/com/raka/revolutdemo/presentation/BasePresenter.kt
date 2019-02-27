package com.raka.revolutdemo.presentation

import androidx.annotation.CallSuper

abstract class BasePresenter<T : BasePresenter.View> {

    var view: T? = null

    @CallSuper
    open fun onViewAttached(view: T) {
        if (this.view != null) {
            throw IllegalStateException("View is already attached!")
        } else {
            this.view = view
        }
    }

    @CallSuper
    open fun onViewDetached() {
        view = null
    }

    interface View
}

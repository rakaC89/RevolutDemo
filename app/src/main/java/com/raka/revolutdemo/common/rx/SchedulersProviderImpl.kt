package com.raka.revolutdemo.common.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersProviderImpl : SchedulersProvider {
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}

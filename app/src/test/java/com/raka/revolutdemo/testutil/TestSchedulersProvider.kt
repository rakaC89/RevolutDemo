package com.raka.revolutdemo.testutil

import com.raka.revolutdemo.common.rx.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TestSchedulersProvider private constructor() : SchedulersProvider {

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun mainThread(): Scheduler {
        return Schedulers.trampoline()
    }

    companion object {

        val INSTANCE = TestSchedulersProvider()
    }
}

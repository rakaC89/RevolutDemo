package com.raka.revolutdemo.common.rx;

import io.reactivex.Scheduler;

public interface SchedulersProvider {

    Scheduler io();

    Scheduler computation();

    Scheduler mainThread();
}

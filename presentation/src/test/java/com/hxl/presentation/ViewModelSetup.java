package com.hxl.presentation;

import org.junit.rules.ExternalResource;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewModelSetup extends ExternalResource {

    @Override
    protected void before() {

        Scheduler immediateScheduler = Schedulers.trampoline();

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> immediateScheduler);
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> immediateScheduler);
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> immediateScheduler);
        RxJavaPlugins.setSingleSchedulerHandler(scheduler -> immediateScheduler);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediateScheduler);
    }
}
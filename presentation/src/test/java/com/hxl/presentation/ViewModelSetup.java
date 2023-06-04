package com.hxl.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockStatic;

import android.annotation.SuppressLint;
import android.util.Log;

import org.junit.rules.ExternalResource;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewModelSetup extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        classSetUp(); // Your setup code goes here
    }

    @SuppressLint("CheckResult")
    private void classSetUp() {
        // Your setup code here
        mockStatic(Log.class);
        doAnswer(invocation -> {
            String tag = invocation.getArgument(0);
            String message = invocation.getArgument(1);
            Throwable throwable = invocation.getArgument(2);
            System.out.println(tag + ": " + message);
            throwable.printStackTrace();
            return 0;
        }).when(Log.class);
        Log.e(anyString(), anyString(), any(Throwable.class));

        Scheduler immediateScheduler = Schedulers.trampoline();

        RxJavaPlugins.setIoSchedulerHandler(scheduler -> immediateScheduler);
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> immediateScheduler);
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> immediateScheduler);
        RxJavaPlugins.setSingleSchedulerHandler(scheduler -> immediateScheduler);

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediateScheduler);
    }
}
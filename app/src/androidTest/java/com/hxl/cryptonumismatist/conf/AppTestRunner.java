package com.hxl.cryptonumismatist.conf;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

public final class AppTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, HiltTest.class.getName() + "_Application", context);
    }
}

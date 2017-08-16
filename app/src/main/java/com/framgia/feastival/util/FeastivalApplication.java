package com.framgia.feastival.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by tmd on 12/08/2017.
 */
public class FeastivalApplication extends Application {
    private static FeastivalApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (sInstance == null) {
            sInstance = this;
        }
    }

    public static Context getContext() {
        return sInstance;
    }
}

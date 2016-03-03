package com.opalab.sunshine.app;

import android.app.Application;
import android.util.Log;

import com.orm.SugarApp;
import com.orm.SugarContext;

public class SunshineApplication extends Application {
    private final String LOG_TAG = ForecastFragment.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

        Log.i(LOG_TAG, "This is Application onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();

        Log.i(LOG_TAG, "Terminate");
    }
}

package com.cameraview;

import android.app.Application;
import android.util.Log;

public class MainApplication extends Application {
    public static MainApplication instance;

    public static MainApplication getInstance() {
        if (instance == null) {
            instance = new MainApplication();
            return instance;
        } else return instance;
    }

    public static void setImageUrl(String response) {
        Log.e("getImageUrl: ", response);
    }

}

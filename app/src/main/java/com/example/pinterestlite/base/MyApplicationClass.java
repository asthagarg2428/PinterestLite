package com.example.pinterestlite.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}

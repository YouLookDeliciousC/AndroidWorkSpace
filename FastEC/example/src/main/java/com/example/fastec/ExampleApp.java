package com.example.fastec;

/* *
 *Author: Gaot Chen
 */

import android.app.Application;

import com.example.latte.app.Latte;

public class ExampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withApiHost("http://127.0.0.1/")
                .configure();
    }
}

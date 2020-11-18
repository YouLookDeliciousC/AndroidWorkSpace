package com.example.accounting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    public static String GLOBAL_ID = "";
    public static String GLOBAL_USERNAME = "";
    public static String GLOBAL_PASSWORD = "";
    public static String GLOBAL_PHONE = "";
    public static String GLOBAL_REALNAME = "";

    int loading_time = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(MainActivity.this,LoginActivity.class); // To login page
                startActivity(i);
                finish();

            }
        },loading_time);

    }

}

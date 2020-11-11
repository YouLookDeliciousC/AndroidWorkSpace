package com.example.accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UserProfileDetailActivity extends AppCompatActivity {

    Button buttonLogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_detail);
        buttonLogout = findViewById(R.id.btnLogout);

        logout();
    }

    private void logout() {
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),"Logout successfully!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(UserProfileDetailActivity.this, LoginActivity.class);
                startActivity(i);
                MainActivity.GLOBAL_USERNAME="";
                MainActivity.GLOBAL_PASSWORD="";
                MainActivity.GLOBAL_PHONE="";
                MainActivity.GLOBAL_REALNAME="";
                finish();



            }
        });
    }
}

package com.example.accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class UserProfileActivity extends AppCompatActivity {

    DatabaseHelper db;
    LinearLayout linearLayoutTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        db = new DatabaseHelper(this);
        linearLayoutTop = findViewById(R.id.llUserProfile1);
        EnterUserProfileDetail();


    }

    private void EnterUserProfileDetail() {
        linearLayoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfileActivity.this, UserProfileDetailActivity.class);
                startActivity(i);
            }
        });
    }
}

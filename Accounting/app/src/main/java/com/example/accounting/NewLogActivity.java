package com.example.accounting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NewLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_log);

        BottomNavigationView bottomNav = findViewById(R.id.log_navigation);

        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.log_fragment_container,
                new ExpenseFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_expense:
                            selectedFragment = new ExpenseFragment();
                            break;
                        case R.id.nav_income:
                            selectedFragment = new IncomeFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.log_fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}

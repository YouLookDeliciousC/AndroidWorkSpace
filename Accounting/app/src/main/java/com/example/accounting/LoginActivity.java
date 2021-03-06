package com.example.accounting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper db;

    EditText editUserName;
    EditText editPassword;
    Button buttonLogin;
    TextView buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        editUserName = findViewById(R.id.etLoginUName);
        editPassword = findViewById(R.id.etLoginPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.tvLoginRegister);

        login();
        register();
    }


    private void register() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    private void login() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = editUserName.getText().toString().trim(); // Get Data
                final String password = editPassword.getText().toString().trim();
                if(userName.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter YOUR USERNAME & PASSWORD.",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean res = db.findPassword(userName,password);
                if(res){
                    Toast.makeText(getApplicationContext(),"Login successfully!",Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(i);
                            MainActivity.GLOBAL_USERNAME=userName; // Set global variables
                            MainActivity.GLOBAL_PASSWORD=password;
                            MainActivity.GLOBAL_ID=db.findId(userName);
                            MainActivity.GLOBAL_PHONE = db.findPhone(userName);
                            MainActivity.GLOBAL_AVATAR = db.getAvatar(MainActivity.GLOBAL_ID);
                            finish();

                        }
                    },2000);
                }else{
                    String buffer = "Error: wrong username or/and password";
                    showMessage("Error", buffer);
                }
            }
        });
    }

    private void showMessage(String title, String buffer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(buffer);
        builder.show();
    }
}

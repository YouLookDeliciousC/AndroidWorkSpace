package com.example.bookstore;

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

import java.nio.file.attribute.BasicFileAttributes;

public class BeginActivity extends AppCompatActivity {

    DatabaseHelper db;
    private EditText editUserName;
    EditText editPassword;
    Button buttonLogin;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        db = new DatabaseHelper(this);
        editUserName = findViewById(R.id.etUserName);
        editPassword = findViewById(R.id.etPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        buttonRegister = findViewById(R.id.btnRegister);

        Login();
        Register();

    }

    private void Register() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BeginActivity.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }

    private void Login() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String userName = editUserName.getText().toString().trim();
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
                            Intent i = new Intent(BeginActivity.this,HomeActivity.class);
                            startActivity(i);
                            MainActivity.GLOBAL_USERNAME=userName;
                            MainActivity.GLOBAL_PASSWORD=password;
                            MainActivity.GLOBAL_ID=db.findId(userName);


                            //finish();
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

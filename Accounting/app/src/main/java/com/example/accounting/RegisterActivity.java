package com.example.accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;

    private EditText editName;
    private EditText editNickname;
    private EditText editPhone;
    private EditText editPassword;
    private EditText editCPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // connect db & link to widgets
        db = new DatabaseHelper(this);

        editName = findViewById(R.id.etRegisterName);
        editNickname = findViewById(R.id.etRegisterNick);
        editPhone = findViewById(R.id.etRegisterPhone);
        editPassword = findViewById(R.id.etRegisterPassword);
        editCPassword = findViewById(R.id.etRegisterCPassword);
        buttonRegister = findViewById(R.id.btnRegister);

        Register();
    }

    private void Register() {

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String nick = editNickname.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String password = editPassword.getText().toString();
                String cPassword = editCPassword.getText().toString();
                //Determine whether it is empty
                if(name.isEmpty() || nick.isEmpty() || phone.isEmpty() || password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter your information COMPLETELY.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(cPassword.isEmpty() || !password.equals(cPassword)){
                    Toast.makeText(getApplicationContext(),"Please confirm your password.", Toast.LENGTH_LONG).show();
                    return;
                }

                if(db.hasSameNick(nick)){
                    Toast.makeText(getApplicationContext(),"The nick name is in use", Toast.LENGTH_LONG).show();
                    return;
                }
                if(db.hasSamePhone(phone)){
                    Toast.makeText(getApplicationContext(),"The phone number has been registered", Toast.LENGTH_LONG).show();
                    return;
                }

                if(db.insertUser(name,nick,phone,password)){
                    Toast.makeText(getApplicationContext(),"Register Successfully!",Toast.LENGTH_LONG).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(i);
                        }
                    },2000);

                }else{
                    Toast.makeText(getApplicationContext(),"Register Error!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editRealName;
    EditText editNickName;
    EditText editPhone;
    EditText editPassword;
    Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        db = new DatabaseHelper(this);

        editRealName = findViewById(R.id.etMRealName);
        editNickName = findViewById(R.id.etMNickName);
        editPhone = findViewById(R.id.etMPhone);
        editPassword = findViewById(R.id.etMPassword);
        buttonUpdate = findViewById(R.id.btnMUpdate);

        String rname = db.findRealName(MainActivity.GLOBAL_USERNAME);
        String phone = db.findPhone(MainActivity.GLOBAL_USERNAME);

        editRealName.setText(rname);
        editNickName.setText(MainActivity.GLOBAL_USERNAME);
        editPhone.setText(phone);
        editPassword.setText(MainActivity.GLOBAL_PASSWORD);


        editRealName.setFocusable(false);
        editRealName.setFocusableInTouchMode(false);

        modifyInfo();



    }

    private void modifyInfo() {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editRealName.getText().toString().trim(),
                        nick = editNickName.getText().toString().trim(),
                        phone = editPhone.getText().toString().trim(),
                        password = editPassword.getText().toString().trim();
                if(nick.isEmpty()||phone.isEmpty()||password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please type in information completely.",Toast.LENGTH_LONG).show();
                    return;
                }
                boolean flag = db.updateUser(MainActivity.GLOBAL_ID,name,nick,phone,password);
                if(flag){
                    Toast.makeText(getApplicationContext(),"Data updated successfully!",Toast.LENGTH_LONG).show();
                    MainActivity.GLOBAL_USERNAME = nick;
                    MainActivity.GLOBAL_PASSWORD = password;
                }
                else {
                    Toast.makeText(getApplicationContext(),"Error: Data updated error.",Toast.LENGTH_LONG).show();
                }
            }
        });



    }

}

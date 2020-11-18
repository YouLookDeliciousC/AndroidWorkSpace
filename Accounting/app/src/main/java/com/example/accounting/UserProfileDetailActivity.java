package com.example.accounting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileDetailActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button buttonLogout;
    LinearLayout layoutNickName;
    LinearLayout layoutPhone;
    TextView textViewNick;
    TextView textViewPhone;
    TextView textViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_detail);
        db = new DatabaseHelper(this);
        buttonLogout = findViewById(R.id.btnLogout);
        layoutNickName = findViewById(R.id.llUserProfileDetailNick);
        layoutPhone = findViewById(R.id.llUserProfileDetailPhone);
        textViewNick = findViewById(R.id.tvProfileDetailNick);
        textViewId = findViewById(R.id.tvProfileDetailId);
        textViewPhone = findViewById(R.id.tvProfileDetailPhone);

        iniData();

        logout();
        modifyNickName();
        modifyPhone();
    }

    private void modifyPhone() {
        layoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(UserProfileDetailActivity.this);

                AlertDialog.Builder inputDialog = new AlertDialog.Builder(UserProfileDetailActivity.this);
                inputDialog.setTitle("New Phone Number").setView(editText);
                inputDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newPhone = editText.getText().toString().trim();
                                String oldPhone = textViewPhone.getText().toString().trim();
                                if(newPhone.isEmpty() || newPhone.equals(oldPhone)){
                                    return;
                                }
                                if(db.hasSamePhone(newPhone)){
                                    showMessage("Alert", "Fail to set the new phone because it is already in use.One phone number for one account.");
                                    return;
                                }
                                boolean flag = db.setNewPhone(MainActivity.GLOBAL_ID, newPhone);
                                if(!flag){
                                    showMessage("Error","Fail to set new phone number");
                                }
                                textViewPhone.setText(newPhone);
                                Toast.makeText(getApplicationContext(), "You got new phone number!", Toast.LENGTH_LONG).show();

                            }
                        }).show();
            }
        });

    }

    private void iniData() {
        textViewNick.setText(MainActivity.GLOBAL_USERNAME);
        textViewPhone.setText(MainActivity.GLOBAL_PHONE);
        textViewId.setText(MainActivity.GLOBAL_ID);

    }

    private void modifyNickName() {
        layoutNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(UserProfileDetailActivity.this);

                AlertDialog.Builder inputDialog = new AlertDialog.Builder(UserProfileDetailActivity.this);
                inputDialog.setTitle("New Nick Name").setView(editText);
                inputDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newNick = editText.getText().toString().trim();
                                String oldNick = textViewNick.getText().toString().trim();
                                if(newNick.isEmpty() || newNick.equals(oldNick)){
                                    return;
                                }
                                if(db.hasSameNick(newNick)){
                                    showMessage("Alert", "Fail to set the new nick name because it is already in use.");
                                    return;
                                }
                                boolean flag = db.setNewNick(MainActivity.GLOBAL_ID, newNick);
                                if(!flag){
                                    showMessage("Error","Fail to set new nick name");
                                }
                                textViewNick.setText(newNick);
                                Toast.makeText(getApplicationContext(), "You got new nick name!", Toast.LENGTH_LONG).show();

                            }
                        }).show();

            }
        });

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

    private void showMessage(String title, String buffer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(buffer);
        builder.show();
    }
}

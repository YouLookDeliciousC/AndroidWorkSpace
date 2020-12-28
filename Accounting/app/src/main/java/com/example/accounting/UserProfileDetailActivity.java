package com.example.accounting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfileDetailActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button buttonLogout;
    LinearLayout layoutAvatar;
    LinearLayout layoutNickName;
    LinearLayout layoutPhone;
    LinearLayout layoutGender;
    ImageView imgAvatar;
    TextView textViewNick;
    TextView textViewPhone;
    TextView textViewId;
    TextView textViewGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_detail);
        db = new DatabaseHelper(this);
        buttonLogout = findViewById(R.id.btnLogout);
        layoutNickName = findViewById(R.id.llUserProfileDetailNick);
        layoutPhone = findViewById(R.id.llUserProfileDetailPhone);
        layoutGender = findViewById(R.id.llUserProfileDetailGender);
        layoutAvatar = findViewById(R.id.llUserProfileDetailAvatar);
        imgAvatar = findViewById(R.id.imgProfileDetailAvatar);
        textViewNick = findViewById(R.id.tvProfileDetailNick);
        textViewId = findViewById(R.id.tvProfileDetailId);
        textViewPhone = findViewById(R.id.tvProfileDetailPhone);
        textViewGender = findViewById(R.id.tvProfileDetailGender);

        iniData();
        logout();
        modifyAvatar();
        modifyNickName();
        modifyPhone();
        modifyGender();
    }

    private void modifyAvatar() {
        layoutAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserProfileDetailActivity.this, AvatarActivity.class);
                startActivity(i);
            }
        });
    }

    int yourChoice;

    private void modifyGender() {
        final String[] items = { "Male","Female","Secret"};

        layoutGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedNum = 0;
                String checkedGender = textViewGender.getText().toString().trim();
                for(String i : items){
                    if(checkedGender.equals(i)){
                        break;
                    }
                    checkedNum ++;
                }

                yourChoice = -1;
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(UserProfileDetailActivity.this);
                singleChoiceDialog.setTitle("Your Gender");
                // second param is default, here set 0
                singleChoiceDialog.setSingleChoiceItems(items, checkedNum,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yourChoice = which;
                            }
                        });
                singleChoiceDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (yourChoice != -1) {
                                    boolean flag = db.setNewGender(MainActivity.GLOBAL_ID, items[yourChoice]);
                                    if(!flag){
                                        showMessage("Error", "Fail to set gender");
                                        return;
                                    }
                                    textViewGender.setText(items[yourChoice]);
                                    Toast.makeText(UserProfileDetailActivity.this,
                                            "You choose " + items[yourChoice],
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                singleChoiceDialog.show();
            }
        });
    }

    private void modifyPhone() {
        layoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(UserProfileDetailActivity.this);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(UserProfileDetailActivity.this);

                editText.setInputType(InputType.TYPE_CLASS_PHONE);
                editText.setRawInputType(Configuration.KEYBOARD_12KEY);
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
                                MainActivity.GLOBAL_PHONE = newPhone;
                                Toast.makeText(getApplicationContext(), "You got new phone number!", Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });
    }
    private void iniData() {
        imgAvatar.setImageResource(AvatarActivity.convertAvatar(MainActivity.GLOBAL_AVATAR));
        textViewNick.setText(MainActivity.GLOBAL_USERNAME);
        textViewPhone.setText(MainActivity.GLOBAL_PHONE);
        textViewId.setText(MainActivity.GLOBAL_ID);
        textViewGender.setText(db.findGender(MainActivity.GLOBAL_ID));
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
                                MainActivity.GLOBAL_USERNAME = newNick;
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
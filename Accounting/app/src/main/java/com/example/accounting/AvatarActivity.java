package com.example.accounting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class AvatarActivity extends AppCompatActivity {

    RadioGroup rgAvatar;
    RadioButton rbAvatar;
    Button btnChooseAvatar;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);


        rgAvatar = findViewById(R.id.rgAvatar);
        db = new DatabaseHelper(this);


        rgAvatar.check(convertId(MainActivity.GLOBAL_AVATAR));

        btnChooseAvatar = findViewById(R.id.btnChooseAvatar);

        btnChooseAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId=rgAvatar.getCheckedRadioButtonId();
                rbAvatar=(RadioButton)findViewById(selectedId);
                String choice = rbAvatar.getText().toString();
                boolean flag = db.setAvatar(MainActivity.GLOBAL_ID, choice);
                if(!flag){
                    showMessage("Error", "Error to set avatar");
                    return;
                }
                MainActivity.GLOBAL_AVATAR = choice;
                Intent intent = new Intent(AvatarActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
    public static Integer convertAvatar(String text){
        Map<String, Integer> strToAvatar = new HashMap<>();
        strToAvatar.put("1", R.drawable.avatar1);
        strToAvatar.put("2", R.drawable.avatar2);
        strToAvatar.put("3", R.drawable.avatar3);
        strToAvatar.put("4", R.drawable.avatar4);

        return strToAvatar.get(text);
    }
    public static Integer convertId(String id){
        Map<String, Integer> strToAvatar = new HashMap<>();
        strToAvatar.put("1", R.id.imgA1);
        strToAvatar.put("2", R.id.imgA2);
        strToAvatar.put("3", R.id.imgA3);
        strToAvatar.put("4", R.id.imgA4);

        return strToAvatar.get(id);
    }
    private void showMessage(String title, String buffer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(buffer);
        builder.show();
    }
}

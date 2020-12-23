package com.example.accounting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogActivity extends AppCompatActivity implements View.OnClickListener {
    String tag;
    DatabaseHelper db;
    ImageView iconImg1;
    ImageView iconImg2;
    ImageView iconImg3;
    ImageView iconImg4;
    Button btnConfirm;
    Button btnGetDate;
    TextView tvAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        db = new DatabaseHelper(this);
        iconImg1 = findViewById(R.id.icons1);
        iconImg2 = findViewById(R.id.icons2);
        iconImg3 = findViewById(R.id.icons3);
        iconImg4 = findViewById(R.id.icons4);

        btnGetDate = findViewById(R.id.getDate);
        tvAmount = findViewById(R.id.getAmount);
        btnConfirm = findViewById(R.id.btnExpenseConfirm);

        iconImg1.setOnClickListener(this);
        iconImg2.setOnClickListener(this);
        iconImg3.setOnClickListener(this);
        iconImg4.setOnClickListener(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateStringParse = sdf.format(date);
        btnGetDate.setText(dateStringParse);



        /*String s = (String) iconImg1.getTag();
        System.out.println(s);*/
        confirmExpense();
        changeDate();

    }

    private void changeDate() {
        btnGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                showDatePickerDialog(LogActivity.this, calendar);
            }
        });
    }

    public  void showDatePickerDialog(Activity activity, Calendar calendar) {
        // Calendar 需要这样来得到
        // Calendar calendar = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity,
                // 绑定监听器(How the parent is notified that the date is set.)
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // 此处得到选择的时间，可以进行你想要的操作
                        monthOfYear ++;

                        btnGetDate.setText(year + "/" + monthOfYear + "/" + dayOfMonth);

                        Toast.makeText( LogActivity.this, year + "年" + monthOfYear
                                + "月" + dayOfMonth + "日",Toast.LENGTH_SHORT).show();
                    }
                }
                // 设置初始日期
                ,calendar.get(Calendar.YEAR)
                ,calendar.get(Calendar.MONTH)
                ,calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void confirmExpense() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = btnGetDate.getText().toString().trim();
                String amount = tvAmount.getText().toString().trim();
                if(tag == null || tag.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please choose one category for your bill", Toast.LENGTH_LONG).show();
                    return;
                }

                if(amount.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please enter your bill", Toast.LENGTH_LONG).show();
                    return;
                }
                String s = db.getItems(MainActivity.GLOBAL_ID);
                String currentItem = tag + "杰" + date + "杰" + amount + "鑫";
                String newGroup = s + currentItem;
                boolean flag = db.addItems(MainActivity.GLOBAL_ID,newGroup);
                if(!flag){
                    Toast.makeText(getApplicationContext(),"Error to log, please retry", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"Log successfully", Toast.LENGTH_LONG).show();
                System.out.println(newGroup);
                finish();

            }
        });
    }

    @Override
    public void onClick(View view) {
        ImageView chosenItem = null;
        switch (view.getId()){
            case (R.id.icons1):
                chosenItem = iconImg1;
                break;
            case (R.id.icons2):
                chosenItem = iconImg2;
                break;
            case (R.id.icons3):
                chosenItem = iconImg3;
                break;
            case (R.id.icons4):
                chosenItem = iconImg4;
                break;
        }
        tag = (String) chosenItem.getTag();

    }
}

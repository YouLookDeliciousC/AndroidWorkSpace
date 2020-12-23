package com.example.accounting;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/* *
 *Author: Goat Chen
 */

public class ExpenseFragment extends Fragment implements View.OnClickListener{

    String tag;
    DatabaseHelper db;
    ImageView iconImg1;
    ImageView iconImg2;
    ImageView iconImg3;
    ImageView iconImg4;
    Button btnConfirm;
    Button btnGetDate;
    TextView tvAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expense,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new DatabaseHelper(getContext());
        iconImg1 = getActivity().findViewById(R.id.icons1);
        iconImg2 = getActivity().findViewById(R.id.icons2);
        iconImg3 = getActivity().findViewById(R.id.icons3);
        iconImg4 = getActivity().findViewById(R.id.icons4);

        btnGetDate = getActivity().findViewById(R.id.getDate);
        tvAmount = getActivity().findViewById(R.id.getAmount);
        btnConfirm = getActivity().findViewById(R.id.btnExpenseConfirm);

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
                showDatePickerDialog(getActivity(), calendar);
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

                        Toast.makeText( getContext(), year + "年" + monthOfYear
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
                    Toast.makeText(getContext(), "Please choose one category for your bill", Toast.LENGTH_LONG).show();
                    return;
                }

                if(amount.isEmpty()){
                    Toast.makeText(getContext(),"Please enter your bill", Toast.LENGTH_LONG).show();
                    return;
                }
                String s = db.getItems(MainActivity.GLOBAL_ID);
                String currentItem = tag + "杰" + date + "杰" + "-" + amount + "鑫";
                String newGroup = s + currentItem;
                boolean flag = db.addItems(MainActivity.GLOBAL_ID,newGroup);
                if(!flag){
                    Toast.makeText(getContext(),"Error to log, please retry", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(),"Log successfully", Toast.LENGTH_LONG).show();
                System.out.println(newGroup);
                getActivity().finish();

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

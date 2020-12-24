package com.example.accounting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.CALL_PHONE;


/* *
 *Author: Goat Chen
 */

public class ProfileFragment extends Fragment {

    DatabaseHelper db;
    LinearLayout linearLayoutTop;
    TextView textViewUserName;

    TextView tvMonth;
    TextView tvIncome;
    TextView tvExpense;
    TextView tvBalance;

    TextView tvMBudget;
    TextView tvMExpense;
    TextView tvRBudget;
    TextView tvTitle;

    LinearLayout llClickAtt;
    TextView tvContAtt;
    TextView tvOverallAtt;

    LinearLayout llContact;

    Button btnSetBudget;
    ProgressBar circleBar;


    public static String OFFICE_PHONE = "0172331061";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelper(getContext());
        linearLayoutTop = getActivity().findViewById(R.id.llUserProfile1);

        textViewUserName = getActivity().findViewById(R.id.tvProfileUserName);
        textViewUserName.setText(MainActivity.GLOBAL_USERNAME);
        tvMonth = getActivity().findViewById(R.id.tvProfileMonth);
        tvIncome = getActivity().findViewById(R.id.tvProfileIncome);
        tvExpense = getActivity().findViewById(R.id.tvProfileExpense);
        tvBalance = getActivity().findViewById(R.id.tvProfileBalance);

        tvMBudget = getActivity().findViewById(R.id.tvProfileMBudget);
        tvMExpense = getActivity().findViewById(R.id.tvProfileMExpense);
        tvRBudget = getActivity().findViewById(R.id.tvProfileRBudget);
        tvTitle = getActivity().findViewById(R.id.tvProfileTitle);

        llClickAtt = getActivity().findViewById(R.id.llckAttendance);
        tvContAtt = getActivity().findViewById(R.id.tvContinuousAtt);
        tvOverallAtt = getActivity().findViewById(R.id.tvOverallAtt);

        llContact = getActivity().findViewById(R.id.llUserProfileContact);

        circleBar = getActivity().findViewById(R.id.progressBar);

        btnSetBudget = getActivity().findViewById(R.id.btnSetBudget);

        tvMBudget.setText(db.getBudget(MainActivity.GLOBAL_ID));

        circleBar.setMax(100);
        circleBar.setIndeterminate(false);


        EnterUserProfileDetail();

        contactUs();
        showBill();
        setBudget();
        attendanceFun();

        String longSen = db.getAttendance(MainActivity.GLOBAL_ID);
        if(longSen.isEmpty()){
            return;
        }
        String[] roughData = longSen.split("鑫");
        tvContAtt.setText(roughData[0] + " Days");
        tvOverallAtt.setText(roughData[1] + " Days");




    }

    private void contactUs() {
        llContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:" + OFFICE_PHONE));
                // check permission
                if(ContextCompat.checkSelfPermission(getContext(),CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(i);
                }else{
                    requestPermissions(new String[]{CALL_PHONE},1);
                }
            }
        });
    }

    private void attendanceFun() {
        llClickAtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String longSen = db.getAttendance(MainActivity.GLOBAL_ID);
                if(longSen.isEmpty()){
                    return;
                }
                String[] roughData = longSen.split("鑫");

                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String result = format.format(today);
                if(roughData[2].equals(result)){
                    showMessage("Double Attendance", "You already signed attendance today...");
                    return;
                }
                int con = Integer.parseInt(roughData[0]) + 1;
                int overall = Integer.parseInt(roughData[1]) + 1;
                String input = con + "鑫" + overall + "鑫" + result;
                boolean flag = db.setAttendance(MainActivity.GLOBAL_ID,input);
                if(!flag){
                    showMessage("Attendance Error", "Something Wrong");
                    return;
                }
                tvContAtt.setText(con + " Days");
                tvOverallAtt.setText(overall + " Days");

                showMessage("Attendance Success", "You have kept keeping accounts for 1 consecutive days! Keep Going!!");
            }
        });
    }

    private void setBudget() {
        btnSetBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(getContext());

                AlertDialog.Builder inputDialog = new AlertDialog.Builder(getContext());
                inputDialog.setTitle("New Budget").setView(editText);
                inputDialog.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newBudget = editText.getText().toString().trim();
                                String oldBudget = tvMBudget.getText().toString().trim();
                                if(newBudget.isEmpty() || newBudget.equals(oldBudget)){
                                    return;
                                }

                                boolean flag = db.setBudget(MainActivity.GLOBAL_ID, newBudget);
                                if(!flag){
                                    showMessage("Error","Fail to set new budget");
                                }
                                tvMBudget.setText(newBudget);
                                showBill();
                                Toast.makeText(getContext(), "You got new budget!", Toast.LENGTH_LONG).show();

                            }
                        }).show();
            }
        });
    }

    private void showBill() {
        Date now = new Date();
        String nowMonthYear = now.toString();
        String month = nowMonthYear.split(" ")[1];
        String year = nowMonthYear.split(" ")[5];
        tvMonth.setText(month);
        tvTitle.setText(month + " Overall Budget");



        String longSen = db.getItems(MainActivity.GLOBAL_ID);
        if(longSen.isEmpty()){
            return;
        }
        String[] roughData = longSen.split("鑫");
        int lengthOfRough = roughData.length;

        List<String> list = new LinkedList<>();

        String monthStr = tvMonth.getText().toString().trim();
        String monthDig = convertStrToDig(monthStr);

        for(int i = 0; i <lengthOfRough; ++ i){
            String[] temp = roughData[i].split("杰");
            System.out.println(temp[1].split("/")[0]);
            if(temp[1].split("/")[0].equals(year)){
                if(temp[1].split("/")[1].equals(monthDig)){
                    list.add(roughData[i]);
                }
            }
        }

        String[] eachItem =(String[])list.toArray(new String[list.size()]);


        int negative = 0;
        int positive = 0;
        for(int i = 0; i < eachItem.length; ++ i){
            int num = Integer.parseInt(eachItem[i].split("杰")[2]);
            if(num>= 0){
                positive += num;
            }
            else{
                negative += num;
            }
        }

        tvIncome.setText(String.valueOf(positive));
        tvExpense.setText(String.valueOf(negative));
        tvBalance.setText(String.valueOf(positive+negative));
        tvMExpense.setText(String.valueOf(negative));

        String mb = tvMBudget.getText().toString().trim();
        String ex = tvExpense.getText().toString().trim();
        int rb = Integer.parseInt(mb) + Integer.parseInt(ex);
        tvRBudget.setText(String.valueOf(rb));
        Double progress = rb / Double.parseDouble(mb) * 100;

        circleBar.setProgress(progress.intValue());


    }

    private String convertDigToStr(String dig){
        final Map<String,String> digiToString = new HashMap<>();

        digiToString.put("1","Jan");
        digiToString.put("2","Feb");
        digiToString.put("3","Mar");
        digiToString.put("4","Apr");
        digiToString.put("5","May");
        digiToString.put("6","Jun");
        digiToString.put("7","Jul");
        digiToString.put("8","Aug");
        digiToString.put("9","Sep");
        digiToString.put("10","Oct");
        digiToString.put("11","Nov");
        digiToString.put("12","Dec");
        return digiToString.get(dig);
    }

    private String convertStrToDig(String str){
        final Map<String,String> strToDig = new HashMap<>();

        strToDig.put("Jan","1");
        strToDig.put("Feb","2");
        strToDig.put("Mar","3");
        strToDig.put("Apr","4");
        strToDig.put("May","5");
        strToDig.put("Jun","6");
        strToDig.put("Jul","7");
        strToDig.put("Aug","8");
        strToDig.put("Sep","9");
        strToDig.put("Oct","10");
        strToDig.put("Nov","11");
        strToDig.put("Dec","12");
        return strToDig.get(str);
    }

    private void EnterUserProfileDetail() {
        linearLayoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UserProfileDetailActivity.class);
                startActivity(i);
            }
        });
    }
    private void showMessage(String title, String buffer) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(buffer);
        builder.show();
    }
}

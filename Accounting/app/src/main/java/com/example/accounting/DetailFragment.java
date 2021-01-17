package com.example.accounting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/* *
 *Author: Goat Chen
 */

public class DetailFragment extends Fragment {

    DatabaseHelper db;
    List<String> itemList;
    List<String> itemDateList;
    List<String> itemAmountList;
    ListView lvItems;

    Button btnMonthPicker;

    TextView tvYear;
    TextView tvDetailIncome;
    TextView tvDetailExpense;

    CustomListAdapter adapter;
    String[] itemArray;
    String[] dateArray;
    String[] amountArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvItems = getActivity().findViewById(R.id.lvDetailShowItem);
        btnMonthPicker = getActivity().findViewById(R.id.btnMonthSelect);

        tvYear = getActivity().findViewById(R.id.tvYearShow);
        tvDetailIncome = getActivity().findViewById(R.id.tvDetailIncome);
        tvDetailExpense = getActivity().findViewById(R.id.tvDetailExpense);

        itemList = new ArrayList<>();
        itemDateList = new ArrayList<>();
        itemAmountList = new ArrayList<>();

        db = new DatabaseHelper(getContext());

        Date now = new Date();
        String nowMonthYear = now.toString();
        String month = nowMonthYear.split(" ")[1];
        String year = nowMonthYear.split(" ")[5];
        btnMonthPicker.setText(month);
        tvYear.setText(year);

        showItems();
        deleteItems();
        chooseMonth();

    }

    private void deleteItems() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Alert");
                alert.setMessage("Do you really want to delete this entry?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Ttag = itemArray[position];
                        String Tdate = dateArray[position];
                        String Tamount = amountArray[position];
                        String roughData = db.getItems(MainActivity.GLOBAL_ID);
                        String[] eachItem = roughData.split("鑫");
                        String input = "";
                        for(int j = 0; j < eachItem.length; j++){
                            String[] temp = eachItem[j].split("杰");
                            if(temp[0].equals(Ttag) && temp[1].equals(Tdate) && temp[2].equals(Tamount)){
                                continue;
                            }
                            input += eachItem[j];
                            input += "鑫";
                        }
                        boolean flag = db.addItems(MainActivity.GLOBAL_ID, input);
                        if(!flag){
                            showMessage("Error", "Error to delete entry");
                        }
                        showItems();
                        Toast.makeText(getContext(), "you have deleted this entry", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert.show();
                return false;
            }
        });
    }

    private void chooseMonth() {
        btnMonthPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar today = Calendar.getInstance();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(getContext(),
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) { // on date set
                                btnMonthPicker.setText(convertDigToStr(String.valueOf(selectedMonth+1)));
                                tvYear.setText(String.valueOf(selectedYear));
                                showItems();
                                Toast.makeText(getContext(),(selectedMonth+1)+"/" + selectedYear, Toast.LENGTH_LONG).show();
                                 }
                            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(1990)
                        .setActivatedYear(today.get(Calendar.YEAR))
                        .setActivatedMonth(today.get(Calendar.MONTH))
                        .setMaxYear(2030)
                        .setTitle("Select month & year")
                        .build().show();
            }
        });
    }

    private void showItems() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String longSen = db.getItems(MainActivity.GLOBAL_ID);
        if(longSen.isEmpty()){
            return;
        }
        String[] roughData = longSen.split("鑫");
        int lengthOfRough = roughData.length;
        System.out.println(longSen);
        List<String> list = new LinkedList<>();

        String year = tvYear.getText().toString().trim();
        String monthStr = btnMonthPicker.getText().toString().trim();
        String monthDig = convertStrToDig(monthStr);
        System.out.println(year + " = " + monthStr + " = " + monthDig);
        for(int i = 0; i <lengthOfRough; ++ i){
            String[] temp = roughData[i].split("杰");
            System.out.println(temp[1].split("/")[0]);
            if(temp[1].split("/")[0].equals(year)){
                if(temp[1].split("/")[1].equals(monthDig)){
                    list.add(roughData[i]);
                }
            }
        }
        for(int i = 0; i < list.size(); i ++){
            System.out.println(list.get(i)+"+++++++++++++++");
        }
        String[] eachItem =list.toArray(new String[list.size()]);

        int amountOfItems = eachItem.length;

        String tempDate;

        for (int i = 1; i < amountOfItems; ++ i) {
            for (int j = 0; j < amountOfItems-i; ++j) {
                String str1 = eachItem[j].split("杰")[1];
                String str2 = eachItem[j+1].split("杰")[1];

                Date date1 = null;
                try {
                    date1 = sdf.parse(str1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date date2 = null;
                try {
                    date2 = sdf.parse(str2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(date1.getTime()<date2.getTime()){
                    tempDate = eachItem[j];
                    eachItem[j] = eachItem[j+1];
                    eachItem[j+1] = tempDate;
                }
            }
        }
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

        tvDetailIncome.setText(String.valueOf(positive));
        tvDetailExpense.setText(String.valueOf(negative));

        String[] tempp;
        itemList.clear();
        itemDateList.clear();
        itemAmountList.clear();

        for(String item : eachItem){
            tempp = item.split("杰");
            itemList.add(tempp[0]);
            itemDateList.add(tempp[1]);
            itemAmountList.add(tempp[2]);
        }

        itemArray = itemList.toArray(new String[itemList.size()]);
        dateArray = itemDateList.toArray(new String[itemDateList.size()]);
        amountArray = itemAmountList.toArray(new String[itemAmountList.size()]);
        Integer[] imgIDArray = new Integer[itemArray.length];
        for(int i = 0; i < itemArray.length; ++ i){
            imgIDArray[i] = imgStrToID(itemArray[i]);
        }

        adapter = new CustomListAdapter(getActivity(), itemArray, dateArray, amountArray, imgIDArray);

        lvItems.setAdapter(adapter);
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

        strToDig.put("Jan","01");
        strToDig.put("Feb","02");
        strToDig.put("Mar","03");
        strToDig.put("Apr","04");
        strToDig.put("May","05");
        strToDig.put("Jun","06");
        strToDig.put("Jul","07");
        strToDig.put("Aug","08");
        strToDig.put("Sep","09");
        strToDig.put("Oct","10");
        strToDig.put("Nov","11");
        strToDig.put("Dec","12");
        return strToDig.get(str);
    }
    private Integer imgStrToID(String str){
        final Map<String,Integer> strToDig = new HashMap<>();

        strToDig.put("Food",R.drawable.food);
        strToDig.put("Beverage",R.drawable.beverage);
        strToDig.put("Shopping",R.drawable.shopping);
        strToDig.put("Bus",R.drawable.bus);
        strToDig.put("Salary",R.drawable.iconsalary);
        strToDig.put("Part time job",R.drawable.iconparttime);
        strToDig.put("Investment",R.drawable.iconinvest);
        strToDig.put("Reward",R.drawable.iconreward);

        return strToDig.get(str);
    }

    private void showMessage(String title, String buffer) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(buffer);
        builder.show();
    }
}

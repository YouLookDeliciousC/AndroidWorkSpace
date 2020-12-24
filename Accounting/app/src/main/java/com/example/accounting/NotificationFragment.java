package com.example.accounting;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/* *
 *Author: Goat Chen
 */

public class NotificationFragment extends Fragment {
    DatabaseHelper db;
    ListView lvItems;
    TextView tvOverallIncome;
    TextView tvOverallExpense;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lvItems = getActivity().findViewById(R.id.lvStatisticsShowItem);
        tvOverallIncome = getActivity().findViewById(R.id.tvStatIncome);
        tvOverallExpense = getActivity().findViewById(R.id.tvStatExpense);


        db = new DatabaseHelper(getContext());

        String longSen = db.getItems(MainActivity.GLOBAL_ID);
        if(longSen.isEmpty()){
            return;
        }
        String[] roughData = longSen.split("鑫");
        int lengthOfRough = roughData.length;
        Map<String, Integer> itemMap = new HashMap<>();
        for(int i = 0; i < lengthOfRough; ++ i){
            String[] itemEle = roughData[i].split("杰");
            String itemName = itemEle[0];
            if(itemMap.containsKey(itemName)){
                Integer amount = itemMap.get(itemName);
                amount += Integer.parseInt(itemEle[2]);
                itemMap.put(itemName, amount);
            }else {
                itemMap.put(itemName,Integer.parseInt(itemEle[2]));
            }
        }
        Set<String> key = itemMap.keySet();
        String[] keySet = key.toArray(new String[key.size()]);
        int len = keySet.length;
        String[] amount = new String[len];
        Integer[] imgID = new Integer[len];
        String[] percentage = new String[len];

        Double inc = 0.0;
        Double exp = 0.0;

        for(int i = 0; i < len; ++ i){
            amount[i] = String.valueOf(itemMap.get(keySet[i]));
        }
        for (int i = 0; i < len; ++ i){
            imgID[i] = imgStrToID(keySet[i]);
        }
        for(int i = 0; i < len; ++ i){
            Double temp = Double.parseDouble(String.valueOf(itemMap.get(keySet[i])));
            if(itemMap.get(keySet[i]) >= 0){
                inc += temp;
            }else{
                exp += temp;
            }
        }

        for(int i = 0; i < len; ++ i){
            DecimalFormat df = new DecimalFormat("0.00");
            double ans;
            Double temp = Double.parseDouble(String.valueOf(itemMap.get(keySet[i])));
            if(itemMap.get(keySet[i]) >= 0){
                ans = temp/inc*100;
                String d = df.format(ans);
                percentage[i] = d + " %";
            }else{
                ans = temp / exp*100;
                String d = df.format(ans);
                percentage[i] = d + " %";
            }
        }

        StatisticsListAdapter adapter = new StatisticsListAdapter(getActivity(), keySet, amount, percentage, imgID);

        lvItems.setAdapter(adapter);
        tvOverallIncome.setText(String.valueOf(inc));
        tvOverallExpense.setText(String.valueOf(exp));


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
}

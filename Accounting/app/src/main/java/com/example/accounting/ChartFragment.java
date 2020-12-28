package com.example.accounting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

import lecho.lib.hellocharts.model.Axis;

import lecho.lib.hellocharts.model.LineChartData;

import lecho.lib.hellocharts.model.Viewport;


/* *
 *Author: Goat Chen
 */

public class ChartFragment extends Fragment {
    DatabaseHelper db;
    Button btnWeek;
    Button btnMonth;
    Button btnYear;
    LineChartView lineChartView;

    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec"};
    int[] yAxisData = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    int maxOfChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new DatabaseHelper(getContext());
        btnWeek = getActivity().findViewById(R.id.btnBalWeek);
        btnMonth = getActivity().findViewById(R.id.btnBalMonth);
        btnYear = getActivity().findViewById(R.id.btnBalYear);

        pressWeek();
        weeklyChart();
        monthlyChart();
        yearlyChart();
        showChart();
    }

    private void pressWeek() {
        btnWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weeklyChart();
            }
        });
    }

    private void showChart() {
        lineChartView = getActivity().findViewById(R.id.lineChart);

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#F44336"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Amount of Money");
        yAxis.setTextColor(Color.parseColor("#F44336"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = maxOfChart + 10;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }

    private void yearlyChart() {
        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] x = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
                        "Oct", "Nov", "Dec"};
                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String result = format.format(today);
                String[] resultEle = result.split("/");
                String year = resultEle[0];

                String[] xTemp = {"01","02","03","04","05","06","07","08","09","10","11","12"};
                int[] y = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

                String longSen = db.getItems(MainActivity.GLOBAL_ID);
                if(longSen.isEmpty()){
                    return;
                }
                String[] eachItem = longSen.split("鑫");
                maxOfChart = Integer.MIN_VALUE;

                for(String item : eachItem){
                    String[] temp = item.split("杰");
                    String[] nowTemp = temp[1].split("/");

                    if(nowTemp[0].equals(year)){
                        for(int i = 0; i < 12; ++ i){
                            if(nowTemp[1].equals(xTemp[i])){
                                y[i] += Integer.parseInt(temp[2]);
                                if(maxOfChart < y[i]){
                                    maxOfChart = y[i];
                                }
                            }
                        }
                    }
                }
                axisData= x;
                yAxisData = y;
                showChart();
            }
        });
    }

    private void monthlyChart() {
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> daysOfMonth;
                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                String result = format.format(today);
                String[] resultEle = result.split("/");
                String month = resultEle[1];
                String year = resultEle[0];
                daysOfMonth = getMonthFullDay(Integer.parseInt(resultEle[0]),Integer.parseInt(resultEle[1]));
                String[] daysEle = daysOfMonth.toArray(new String[daysOfMonth.size()]);
                int[] daysInt = new int[daysEle.length];
                String[] daysStr = new String[daysEle.length];
                for(int i = 0; i < daysEle.length; i++){
                    daysInt[i] = Integer.parseInt(daysEle[i].split("/")[2]);
                }
                for(int i = 0; i < daysInt.length; ++ i){
                    daysStr[i] = String.valueOf(daysInt[i]);
                }

                int[] y = new int[daysInt.length];
                for(int i = 0; i < daysInt.length; i ++){
                    y[i] = 0;
                }
                String longSen = db.getItems(MainActivity.GLOBAL_ID);
                if(longSen.isEmpty()){
                    return;
                }
                String[] eachItem = longSen.split("鑫");
                maxOfChart = Integer.MIN_VALUE;
                for(String item : eachItem){
                    String[] temp = item.split("杰");
                    String[] nowTemp = temp[1].split("/");
                    if(nowTemp[0].equals(year) && nowTemp[1].equals(month)){
                        y[Integer.parseInt(nowTemp[2])-1] += Integer.parseInt(temp[2]);
                        if(maxOfChart < y[Integer.parseInt(nowTemp[2])-1]){
                            maxOfChart = y[Integer.parseInt(nowTemp[2])-1];
                        }
                    }
                }
                axisData = daysStr;
                yAxisData = y;
                showChart();
            }
        });

    }

    private void weeklyChart() {
        List<String> pastDaysList = new ArrayList<>();
        for(int i = 0; i < 7; i ++){
            pastDaysList.add(getPastDate(i));
        }
        String[] originPast = pastDaysList.toArray(new String[pastDaysList.size()]);
        List<String> trimList = new ArrayList<>();

        for(int i = 0; i < 7; i ++){
            String temp = pastDaysList.get(i);
            String[] a = temp.split("/");
            String addIn = a[1] + "/" + a[2];
            trimList.add(addIn);
        }

        String[] newList = trimList.toArray(new String[trimList.size()]);

        String longSen = db.getItems(MainActivity.GLOBAL_ID);
        if(longSen.isEmpty()){
            return;
        }
        axisData = newList;
        String[] roughData = longSen.split("鑫");
        int lengthOfRough = roughData.length;
        String min = originPast[6];
        String max = originPast[0];


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date dateMin = null;
        Date dateMax = null;
        try {
            dateMin = format.parse(min);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            dateMax = format.parse(max);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<String> d = new ArrayList<>();

        for(int i = 0; i < lengthOfRough; i ++){
            String[] eachItem = roughData[i].split("杰");

            try {
                Date temp = format.parse(eachItem[1]);

                if(temp.getTime() <= dateMax.getTime() && temp.getTime() >= dateMin.getTime()){
                    d.add(roughData[i]);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        Map<String, Integer> xy = new HashMap<>();
        String[] inWeek = d.toArray(new String[d.size()]);
        for(int i = 0; i < 7; i ++){
            xy.put(originPast[i], 0);
        }
        System.out.println("进入");
        for(int i = 0; i < inWeek.length; ++ i){
            System.out.println(inWeek[i]);
        }
        System.out.println("出来");
        for(int i = 0; i < inWeek.length; i++){
            String[] temp = inWeek[i].split("杰");
            int value = xy.get(temp[1]);
            value += Integer.parseInt(temp[2]);
            xy.put(temp[1],value);
        }
        int[] data = new int[7];
        maxOfChart = Integer.MIN_VALUE;
        for(int i = 0; i < 7; ++ i){
            data[i] = xy.get(originPast[i]);
            if(maxOfChart < data[i]){
                maxOfChart = data[i];
            }
        }
        yAxisData = data;
        showChart();
    }

    private  String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String result = format.format(today);
        return result;
    }
    public  List<String> getMonthFullDay(int year , int month){
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy/MM/dd");
        List<String> fullDayList = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1 );
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        return fullDayList;
    }
}

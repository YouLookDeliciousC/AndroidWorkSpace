package com.example.accounting;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/* *
 *Author: Goat Chen
 */

public class ChartFragment extends Fragment {
    DatabaseHelper db;

    LineChartView lineChartView;
    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec"};
    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        db = new DatabaseHelper(getContext());
        weeklyChart();
        monthlyChart();
        yearlyChart();

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
        yAxis.setName("Sales in millions");
        yAxis.setTextColor(Color.parseColor("#F44336"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }

    private void yearlyChart() {

    }

    private void monthlyChart() {

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
        System.out.println("=====================");
        for(int i = 0; i <originPast.length; ++ i){
            System.out.println(originPast[i]);
        }


        axisData = newList;

        String longSen = db.getItems(MainActivity.GLOBAL_ID);
        if(longSen.isEmpty()){
            return;
        }
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
        for(int i = 0; i < 7; ++ i){
            data[i] = xy.get(originPast[i]);
        }
        yAxisData = data;
    }

    private static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String result = format.format(today);
        /*Log.e(null, result);*/
        return result;
    }
}

package com.example.accounting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/* *
 *Author: Goat Chen
 */

public class StatisticsListAdapter extends ArrayAdapter {
    private final Activity context;
    private final Integer[] imageIDarray;
    private final String[] itemNameArray;
    private final String[] amountArray;
    private final String[] percentageArray;

    public StatisticsListAdapter(Activity context, String[] itemNameArrayParam, String[] amountArrayParam, String[] percentageArrayParam, Integer[] imageIDArrayParam){

        super(context,R.layout.statistics_list_row , itemNameArrayParam);

        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.itemNameArray = itemNameArrayParam;
        this.percentageArray = percentageArrayParam;
        this.amountArray = amountArrayParam;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.statistics_list_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.rowSItem);
        TextView amountTextField = rowView.findViewById(R.id.rowSAmount);
        TextView percentageTextField = rowView.findViewById(R.id.rowSPercent);
        ImageView imageView = rowView.findViewById(R.id.rowSImageView1ID);

        //this code sets the values of the objects to values from the arrays
        nameTextField.setText(itemNameArray[position]);
        percentageTextField.setText(percentageArray[position]);
        amountTextField.setText(amountArray[position]);
        imageView.setImageResource(imageIDarray[position]);

        return rowView;
    }
}

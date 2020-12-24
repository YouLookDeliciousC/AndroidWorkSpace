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

public class CustomListAdapter extends ArrayAdapter {
    private final Activity context;

    //to store the animal images
    private final Integer[] imageIDarray;

    //to store the list of countries
    private final String[] itemNameArray;

    //to store the list of countries
    private final String[] dateArray;

    private final String[] amountArray;

    public CustomListAdapter(Activity context, String[] itemNameArrayParam, String[] dateArrayParam, String[] amountArrayParam, Integer[] imageIDArrayParam){

        super(context,R.layout.listview_row , itemNameArrayParam);

        this.context=context;
        this.imageIDarray = imageIDArrayParam;
        this.itemNameArray = itemNameArrayParam;
        this.dateArray = dateArrayParam;
        this.amountArray = amountArrayParam;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View rowView=inflater.inflate(R.layout.listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = rowView.findViewById(R.id.rowItem);
        TextView dateTextField = rowView.findViewById(R.id.rowDate);
        TextView amountTextField = rowView.findViewById(R.id.rowAmount);
        ImageView imageView = rowView.findViewById(R.id.rowImageView1ID);

        //this code sets the values of the objects to values from the arrays

        nameTextField.setText(itemNameArray[position]);
        dateTextField.setText(dateArray[position]);
        amountTextField.setText(amountArray[position]);
        imageView.setImageResource(imageIDarray[position]);

        return rowView;

    }
}

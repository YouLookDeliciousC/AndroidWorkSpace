package com.example.listviewtut;

import android.app.Activity;
import android.widget.ArrayAdapter;


/* *
 *Author: Goat Chen
 */

public class CustomListAdapter extends ArrayAdapter {
    public CustomListAdapter(Activity context, String[] nameArrayParam, String[] infoArrayParam, Integer[] imageIDArrayParam){

        super(context,R.layout.listview_row , nameArrayParam);

    }

    private final Activity context;

    //to store the animal images
    private final Integer[] imageIDarray;

    //to store the list of countries
    private final String[] nameArray;

    //to store the list of countries
    private final String[] infoArray;
}

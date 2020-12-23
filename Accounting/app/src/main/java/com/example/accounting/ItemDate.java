package com.example.accounting;

/* *
 *Author: Goat Chen
 */

public class ItemDate {
    private String datetime;
    /*private String date;
    private String amount;*/

    public ItemDate (String datetime){
        this.datetime = datetime;
        /*this.date = date;
        this.amount = amount;*/
    }
    public String toString(){
        return datetime;
    }
}

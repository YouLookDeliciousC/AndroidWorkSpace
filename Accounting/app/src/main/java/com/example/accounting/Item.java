package com.example.accounting;

/* *
 *Author: Goat Chen
 */


public class Item {
    private String tag;
    /*private String date;
    private String amount;*/

    public Item (String tag){
        this.tag = tag;
        /*this.date = date;
        this.amount = amount;*/
    }
    public String toString(){
        return tag;
    }

}

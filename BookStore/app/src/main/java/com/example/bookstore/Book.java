package com.example.bookstore;

/* *
 *Author: Goat Chen
 */

public class Book {
    private String _id;
    private String name;
    private String author;
    private String loca;
    private String price;

    public Book (String id,String name,String author, String location,String price){
        this._id = id;
        this.name = name;
        this.author = author;
        this.loca = location;
        this.price = price;
    }
    public String toString(){
        return "Book ID: " + _id + " Name: " + name + " Author: " + author + " Location: " + loca + " Price: " + price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoca() {
        return loca;
    }

    public void setLoca(String loca) {
        this.loca = loca;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

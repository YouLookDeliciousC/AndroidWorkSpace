package com.example.bookstore;


/* *
 *Author: Goat Chen
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Bookstore.db";
    public static final String TABLE_NAME_1 = "User_Info"; // Table 1 for user Info
    public static final String COLUMN_1_1 = "ID";
    public static final String COLUMN_1_2 = "NAME";
    public static final String COLUMN_1_3 = "NICKNAME";
    public static final String COLUMN_1_4 = "PHONE";
    public static final String COLUMN_1_5 = "PASSWORD";

    public static final String TABLE_NAME_2 = "Book_Info"; // Table 2 for Book Info
    public static final String COLUMN_2_1 = "ID";
    public static final String COLUMN_2_2 = "NAME";
    public static final String COLUMN_2_3 = "AUTHOR";
    public static final String COLUMN_2_4 = "LOCATION";
    public static final String COLUMN_2_5 = "PRICE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3); //version3 means change the database twice
        //SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME_1+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, NICKNAME TEXT, PHONE INTEGER, PASSWORD TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE "+ TABLE_NAME_2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AUTHOR TEXT, LOCATION TEXT, PRICE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_2);
        onCreate(sqLiteDatabase);

    }

    public boolean insertUser(String name, String nickname,String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_2,name);
        contentValues.put(COLUMN_1_3,nickname);
        contentValues.put(COLUMN_1_4,phone);
        contentValues.put(COLUMN_1_5,password);
        long res = db.insert(TABLE_NAME_1,null,contentValues);
        return res != -1;
    }

    //Update UserInfo
    public boolean updateUser(String id, String name, String nickname,String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_1,id);
        contentValues.put(COLUMN_1_2,name);
        contentValues.put(COLUMN_1_3,nickname);
        contentValues.put(COLUMN_1_4,phone);
        contentValues.put(COLUMN_1_5,password);
        int flag = db.update(TABLE_NAME_1,contentValues,"ID = ?",new String[]{id});
        return flag != -1;
    }

    //Delete User
    public Integer deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_1,"NICKNAME=?",new String[]{username});
    }

    //Compare the password user input with password in db
    public boolean findPassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE NICKNAME = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_5);
        String res="";
        while (cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            return password.equals(res);
        }
        return false;
    }

    //findId by username
    public String findId(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE NICKNAME = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_1);
        String res="";
        while (cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public String findRealName(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE NICKNAME = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_2);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public String findPhone(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE NICKNAME = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_4);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    //Insert book
    public boolean insertBook(String name, String author,String location, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2_2,name);
        contentValues.put(COLUMN_2_3,author);
        contentValues.put(COLUMN_2_4,location);
        contentValues.put(COLUMN_2_5,price);
        long res = db.insert(TABLE_NAME_2,null,contentValues);
        return res != -1;
    }

    public Cursor refreshBook(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_2,null);
        return res;
    }

    public boolean updateBook(String id,String name, String author,String location, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_2_1,id);
        contentValues.put(COLUMN_2_2,name);
        contentValues.put(COLUMN_2_3,author);
        contentValues.put(COLUMN_2_4,location);
        contentValues.put(COLUMN_2_5,price);
        int flag = db.update(TABLE_NAME_2,contentValues,"ID = ?",new String[]{id});

        return flag != -1;

    }

    public String findBookName(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE ID = ?",new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_2_2);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public String findBookAuthor(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE ID = ?",new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_2_3);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public String findBookLocation(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE ID = ?",new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_2_4);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public String findBookPrice(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE ID = ?",new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_2_5);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }


    public Integer deleteBook(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_2,"NAME=?",new String[]{name});
    }

    public Cursor findBookByName(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME_2+" WHERE NAME =? ",new String[]{name});
        return res;
    }

    public Integer countItem(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME_2 + " WHERE ID=?",new String[]{id});
        Integer res=-1;
        while (cursor.moveToNext()){
            res = cursor.getInt(0);
            break;
        }
        return res;
    }









}

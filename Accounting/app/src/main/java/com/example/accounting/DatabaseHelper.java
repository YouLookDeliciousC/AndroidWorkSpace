package com.example.accounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;


/* *
 *Author: Goat Chen
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Accounting.db";
    public static final String TABLE_NAME_1 = "User_Info"; // Table 1 for user Info
    public static final String COLUMN_1_1 = "ID";
    public static final String COLUMN_1_2 = "NAME";
    public static final String COLUMN_1_3 = "NICKNAME";
    public static final String COLUMN_1_4 = "PHONE";
    public static final String COLUMN_1_5 = "PASSWORD";

    public DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, NICKNAME TEXT, PHONE INTEGER, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
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
    boolean hasSameNick(String nick){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE NICKNAME = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{nick});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_3);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            return res.equals(nick);
        }
        return false;
    }

    boolean hasSamePhone(String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE PHONE = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{phone});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_4);
        String res = "";
        while(cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            return res.equals(phone);
        }
        return false;
    }

    boolean setNewNick(String id, String nick){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_3, nick);
        int flag = db.update(TABLE_NAME_1,contentValues,"ID = ?",new String[]{id});
        return flag != -1;
    }

    boolean setNewPhone(String id, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_4, phone);
        int flag = db.update(TABLE_NAME_1,contentValues,"ID = ?",new String[]{id});
        return flag != -1;
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




}

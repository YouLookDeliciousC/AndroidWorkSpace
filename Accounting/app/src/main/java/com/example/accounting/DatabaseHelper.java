package com.example.accounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
    public static final String COLUMN_1_6 = "GENDER";
    public static final String COLUMN_1_7 = "ITEMS";
    public static final String COLUMN_1_8 = "BUDGET";
    public static final String COLUMN_1_9 = "ATTENDANCE";
    public static final String COLUMN_1_10 = "AVATAR";

    public DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME_1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, NICKNAME TEXT, PHONE INTEGER, PASSWORD TEXT, GENDER TEXT, ITEMS TEXT, BUDGET TEXT, ATTENDANCE TEXT, AVATAR TEXT)");
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
        contentValues.put(COLUMN_1_6, "Secret");
        contentValues.put(COLUMN_1_7, "");
        contentValues.put(COLUMN_1_8, "0");
        contentValues.put(COLUMN_1_9, "0鑫0鑫2020/01/05");
        contentValues.put(COLUMN_1_10,"1");
        long res = db.insert(TABLE_NAME_1,null,contentValues);
        return res != -1;
    }

    public String getAvatar(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE ID = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_10);
        String res="";
        while (cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public boolean setAvatar(String id, String avatar){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_10, avatar);
        int flag = db.update(TABLE_NAME_1, contentValues, "ID = ?", new String[]{id});
        return flag != -1;
    }

    public String getAttendance(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE ID = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_9);
        String res="";
        while (cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public boolean setAttendance(String id, String attendance){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_9, attendance);
        int flag = db.update(TABLE_NAME_1, contentValues, "ID = ?", new String[]{id});
        return flag != -1;
    }

    public String getBudget(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE ID = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_8);
        String res="";
        while (cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    public boolean setBudget(String id, String budget){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_8, budget);
        int flag = db.update(TABLE_NAME_1, contentValues, "ID = ?", new String[]{id});
        return flag != -1;
    }

    public String getItems(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE ID = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_7);
        String res="";
        while (cursor.moveToNext()){
            res = cursor.getString(columnIndex);
            break;
        }
        return res;
    }

    boolean addItems(String id, String items){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_7, items);
        int flag = db.update(TABLE_NAME_1, contentValues, "ID = ?", new String[]{id});
        return flag != -1;
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

    String findGender(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME_1 + " WHERE ID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        int columnIndex = cursor.getColumnIndex(COLUMN_1_6);
        String res = "";
        while(cursor.moveToNext()){
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

    boolean setNewGender(String id, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_1_6, gender);
        int flag = db.update(TABLE_NAME_1, contentValues, "ID = ?", new String[]{id});
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

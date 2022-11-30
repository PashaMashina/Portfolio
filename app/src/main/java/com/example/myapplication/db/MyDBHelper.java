package com.example.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.LearnPrice;

public class MyDBHelper  extends SQLiteOpenHelper {

    private Context context;

    public MyDBHelper(@Nullable Context context) {
        super(context, MyConstants.DB_NAME, null, MyConstants.DB_VERSION);
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreate = "CREATE TABLE IF NOT EXISTS " +
                MyConstants.TABLE_NAME + "(" +
                MyConstants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MyConstants.TITLE + " TEXT," +
                MyConstants.COST_NOW + " DOUBLE," +
                MyConstants.COST_BUY + " DOUBLE,"+
                MyConstants.AMOUNT + " DOUBLE," +
                MyConstants.SUM + " DOUBLE," +
                MyConstants.PROFIT_FROM_BUY + " DOUBLE," +
                MyConstants.TYPE_ASSET + " TEXT)";
        db.execSQL(queryCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String queryDrop = ("DROP TABLE IF EXISTS " + MyConstants.TABLE_NAME);
        db.execSQL(queryDrop);
        onCreate(db);
    }

    public void addAsset(String title, double costNow, double amount, String typeAsset){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        final double priceNow = LearnPrice.priceNow;
        System.out.println(priceNow);
        cv.put(MyConstants.TITLE, title);
        cv.put(MyConstants.COST_NOW, costNow + " руб.");
        cv.put(MyConstants.COST_BUY, costNow + " руб.");
        cv.put(MyConstants.AMOUNT, amount);
        cv.put(MyConstants.SUM, (costNow * amount) + " руб.");
        cv.put(MyConstants.PROFIT_FROM_BUY, "0%" );
        cv.put(MyConstants.TYPE_ASSET, typeAsset);
        long result = db.insert(MyConstants.TABLE_NAME, null, cv);
        if (result == -1){
            System.out.println("Не добавлено!");
        }
        else {
            System.out.println("Добавлено!");
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + MyConstants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateData(String row_id, String title, String costBuy, String amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MyConstants.TITLE, title);
        cv.put(MyConstants.COST_BUY, costBuy + " руб.");
        cv.put(MyConstants.AMOUNT, amount);
        cv.put(MyConstants.PROFIT_FROM_BUY, MyConstants.ProfitFromBuy(Double.parseDouble(costBuy), 100));
        cv.put(MyConstants.SUM, Double.parseDouble(costBuy) * Double.parseDouble(amount));

        long result = db.update(MyConstants.TABLE_NAME, cv, "id=?", new String[]{row_id});

        if(result == -1){
            System.out.println();
        }else {
            System.out.println(result);
        }
    }

    public void  deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(MyConstants.TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            System.out.println("Failed");
        }else{
            System.out.println("Success");
        }
    }

    public  void  deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + MyConstants.TABLE_NAME);
    }
}
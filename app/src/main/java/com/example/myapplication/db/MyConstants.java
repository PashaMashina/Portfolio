package com.example.myapplication.db;

public class MyConstants {
    public static final  String TABLE_NAME = "Portfolio";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String COST_NOW = "costNow";
    public static final String COST_BUY = "costBuy";
    public static final String AMOUNT = "amount";
    public static final String SUM = "sum";
    public static final String PROFIT_FROM_BUY = "profitFromBuy";
    public static final String TYPE_ASSET = "typeAsset";
    public static final String DB_NAME = "my_DB.db";
    public static final int DB_VERSION = 5;

    public static double ProfitFromBuy(double costBuy, double costNow){
        double profitFromBuy;
            profitFromBuy = Math.round(((costNow - costBuy)/costBuy*100) * 100.0) / 100.0;
        return profitFromBuy;
    }
}
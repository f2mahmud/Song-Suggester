package com.example.gui.myapplication;


import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Azraful on 2016-04-10.
 */


public class DBHandler extends SQLiteAssetHelper {

    //Database Name
    private static final String DB_NAME = "songPersonality.db";

    private static final int DATABASE_VERSION = 3;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

}
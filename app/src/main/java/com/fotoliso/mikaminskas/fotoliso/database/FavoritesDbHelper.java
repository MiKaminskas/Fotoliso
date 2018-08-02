package com.fotoliso.mikaminskas.fotoliso.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fotoliso.mikaminskas.fotoliso.database.FavoritesDbSchema.FavoritesTable;


public class FavoritesDbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "favoritesBase.db";

    public FavoritesDbHelper(Context context){
        super(context, DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + FavoritesTable.NAME  + "("
                + " _id integer primary key autoincrement, "
                + FavoritesTable.Cols.PERFORMER_NAME + ", "
                + FavoritesTable.Cols.AVA_THUMB + ", "
                + FavoritesTable.Cols.ID + ", "
                + FavoritesTable.Cols.RATING + ", "
                + FavoritesTable.Cols.REVIEWS +")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.fotoliso.mikaminskas.fotoliso.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fotoliso.mikaminskas.fotoliso.Performer;

import java.util.ArrayList;
import java.util.List;

import static com.fotoliso.mikaminskas.fotoliso.database.FavoritesDbSchema.*;

public class PerformerLabManipulator {
    private static PerformerLabManipulator lab;
    private static SQLiteDatabase database;
    private static Context context;
    private static List<Performer> performers;

    private PerformerLabManipulator(Context c){
        context = c.getApplicationContext();
        database = new FavoritesDbHelper(context).getWritableDatabase();
        performers = getPerformers();

    }
    public static PerformerLabManipulator get(Context c){
        context = c.getApplicationContext();
        if (lab == null){
            lab = new PerformerLabManipulator(context);
        }
        return lab;
    }


    public void addFavorites(Performer performer){
        ContentValues values = getContentValues(performer);
        database.insert(FavoritesTable.NAME, null, values);
    }

    private ContentValues getContentValues(Performer performer){
        ContentValues values = new ContentValues();

        values.put(FavoritesTable.Cols.ID, performer.getId());
        values.put(FavoritesTable.Cols.AVA_THUMB, performer.getAva_thumb());
        values.put(FavoritesTable.Cols.PERFORMER_NAME, performer.getName());
        values.put(FavoritesTable.Cols.RATING, performer.getRating());
        values.put(FavoritesTable.Cols.REVIEWS, performer.getReviews());

        /*values.put();*/
        return values;
    }


    private PerformerCursorWraper queryFavoritePerformers(String whereClause, String[] whereArgs){
        Cursor cursor = database.query(FavoritesTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        return new PerformerCursorWraper(cursor);
    }


    public List<Performer> getPerformers(){


        List<Performer> performers = new ArrayList<>();
        PerformerCursorWraper cursor = queryFavoritePerformers(null,null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                performers.add(cursor.getPerformer());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return performers;
    }

    public List<Performer> returnPerformers(){
        return performers;
    }

    // TODO delete from favorites
    public void deleteFavoritPerformer(Performer performer){

    }
}

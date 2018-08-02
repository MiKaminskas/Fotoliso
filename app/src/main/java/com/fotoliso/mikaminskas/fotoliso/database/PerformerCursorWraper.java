package com.fotoliso.mikaminskas.fotoliso.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.fotoliso.mikaminskas.fotoliso.Performer;
import com.fotoliso.mikaminskas.fotoliso.database.FavoritesDbSchema.FavoritesTable;

public class PerformerCursorWraper extends CursorWrapper {
    public PerformerCursorWraper(Cursor cursor) {
        super(cursor);
    }
    public Performer getPerformer(){
        String name = getString(getColumnIndex(FavoritesTable.Cols.PERFORMER_NAME));
        String reviews = getString(getColumnIndex(FavoritesTable.Cols.REVIEWS));
        String ava_thumb = getString(getColumnIndex(FavoritesTable.Cols.AVA_THUMB));
        String id = getString(getColumnIndex(FavoritesTable.Cols.ID));
        String rating = getString(getColumnIndex(FavoritesTable.Cols.RATING));

        Performer performer = new Performer();
        performer.setName(name);
        performer.setReviews(reviews);
        performer.setAva_thumb(ava_thumb);
        performer.setId(id);
        performer.setRating(rating);

        return performer;
    }

}

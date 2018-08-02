package com.fotoliso.mikaminskas.fotoliso;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.fotoliso.mikaminskas.fotoliso.database.PerformerLabManipulator;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends PerformerListFragment{
    private static List<Performer> mPerformers;
    private String TAG = "FavoritesFragment";
    private static Context context;
    private PerformerLabManipulator lab;

    public static Fragment newInstance(Context c){
        context = c;
        /*mPerformers = lab.getPerformers();*/ /*lab.returnPerformers();*/

        return new FavoritesFragment();
    }

    @Override
    List<Performer> setPerformersList() {
        lab = PerformerLabManipulator.get(context);
        mPerformers = lab.getPerformers();
        Log.d(TAG, "size: " +  mPerformers.size() );
        return mPerformers;
    }






}

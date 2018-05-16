package com.fotoliso.mikaminskas.fotoliso;

import android.support.v4.app.Fragment;

import java.util.List;

public class PostSearchList extends PerformerListFragment {
    private static List<Performer> mPerformers;

    public static Fragment newInstance(List<Performer> performers){
        mPerformers = performers;
        return new PostSearchList();
    }

    @Override
    List<Performer> setPerformersList() {
        return mPerformers;
    }
}

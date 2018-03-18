package com.fotoliso.mikaminskas.fotoliso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by misha on 2/2/2018.
 */

public class SearchFragment extends Fragment {
    private final String TAG = "SearchFragment";
    public static SearchFragment newInstance(){
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchTest().execute();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.search_fragment , container,false);
    }
    private class FetchTest extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            new FotolisoFetchr().fetchCountries();
            return null;
        }
    }
}

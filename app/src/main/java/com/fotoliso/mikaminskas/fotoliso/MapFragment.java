package com.fotoliso.mikaminskas.fotoliso;


import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by misha on 3/10/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnCameraMoveListener {
    private final String TAG = "MapFragment";
    private GoogleMap mMap;
    private MapView mapView;
    private HashMap<String, String> mCountriesIdHashMap;
    public static LatLng mCameraPositonCoordinates;


    public static Fragment newInstance(LatLng cameraPositionCoordinates) {
        mCameraPositonCoordinates = cameraPositionCoordinates;
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_field_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, " Text change");
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mCameraPositonCoordinates, 4.5f));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        addMarkers();

        mMap.setOnCameraMoveListener(this);


    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.d(TAG, "marker in work " + marker.getTitle() + "  " + mCountriesIdHashMap.get(marker.getTitle()));
        new FetchPerformers(mCountriesIdHashMap.get(marker.getTitle())).execute();

    }

    //TODO opt marker drawing throw this method
    @Override
    public void onCameraMove() {
        Log.d(TAG, "Camera moved");

    }

    private class FetchPerformers extends AsyncTask<Void, Void, List<Performer>> {
        private String mCountryID;

        FetchPerformers(String countryID) {
            this.mCountryID = countryID;
        }

        @Override
        protected List<Performer> doInBackground(Void... voids) {
            Log.d(TAG, " FetchPerformerS in WORK");
            try {
                return new FotolisoFetchr().fetchPerformers(mCountryID);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Performer> performers) {
            super.onPostExecute(performers);
            ((MainActivity) getActivity()).showFragment(PostSearchList.newInstance(performers), "PERFORMERS");
        }
    }

    private class FetchPerformer extends AsyncTask<Void, Void, Performer> {
        private String mPerformerID;

        FetchPerformer(String performerID) {
            this.mPerformerID = performerID;
        }

        @Override
        protected Performer doInBackground(Void... voids) {
            Log.d(TAG, " FetchPerformer in WORK");
            try {
                return new FotolisoFetchr().fetchPerformer(mPerformerID);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Performer performersList) {
            super.onPostExecute(performersList);


        }
    }

    private void addMarkers() {
        List<Country> mCountryList = CountrySingleton.getCountryList();
        mCountriesIdHashMap = new HashMap<>(mCountryList.size());

        for (int i = 0; i < mCountryList.size(); i++) {
            Country curr = mCountryList.get(i);
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(curr.getLatitude(), curr.getLongitude()))
                    .title(curr.getName())
                    .snippet("Исполнителей: " + curr.getPerformersCount())
            );
            mCountriesIdHashMap.put(curr.getName(), curr.getId());
        }
        mMap.setOnInfoWindowClickListener(this);
    }

}

package com.fotoliso.mikaminskas.fotoliso;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

/**
 * Created by misha on 3/10/2018.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private final String TAG = "MapFragment";
    private GoogleMap mMap;
    private MapView mapView;
    private List<Country> mCountryList;
    //last know location
    private FusedLocationProviderClient mFusedLocationClient;

    public static Fragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.map_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        /*new FetchTest(mMap).execute();*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //mover camera to last known location
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 4.5f ));

                            Log.d("map_fragment", location.getAltitude() + " " +  location.getLongitude());
                        }
                    }
                });



        /*double longitude = 50*//*address.getLongitude()*//*;
        double latitude = 25 *//*address.getLatitude()*//*;*/

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
/*
        LatLng currLock = new LatLng(longitude,latitude);*/
        /*mMap.addMarker(new MarkerOptions().position(new LatLng(30, 30)).title("Marker in Sydney"));*/
        new FetchTest(mMap).execute();

    }

    private class FetchTest extends AsyncTask<Void,Void,List<Country>> {
        private GoogleMap mMap;
        public FetchTest(GoogleMap mMap) {
            this.mMap = mMap;
        }

        @Override
        protected List<Country> doInBackground(Void... voids) {
            return new FotolisoFetchr().fetchCountries();
        }

        @Override
        protected void onPostExecute(List<Country> countries) {
            super.onPostExecute(countries);
            mCountryList = countries;
            for (int i =0; i< mCountryList.size();i++){
                Country curr = mCountryList.get(i);
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(curr.getLatitude(),curr.getLongitude())).title(curr.getName()));
                marker.setSnippet("Исполнителей: "+ curr.getPerformersCount());
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener(){
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Toast.makeText(getActivity(),"Hello",Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "marker in work " + marker.getTitle());
                    }
                });
            }


        }
    }
}

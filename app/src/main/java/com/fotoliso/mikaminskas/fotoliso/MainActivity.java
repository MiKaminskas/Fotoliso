package com.fotoliso.mikaminskas.fotoliso;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.fotoliso.mikaminskas.fotoliso.database.FavoritesDbHelper;
import com.fotoliso.mikaminskas.fotoliso.database.FavoritesDbSchema;
import com.fotoliso.mikaminskas.fotoliso.database.PerformerLabManipulator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private Context context;

    //last know location
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng cameraPositionCoordinates;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.recent_viewed:
                    //TODO create recent reciclerview + db clase
                    fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("SEARCH");
                    if (fragment == null || !fragment.isVisible()) {
                        showFragment(SearchFragment.newInstance(), "SEARCH");
                    }
                    break;
                case R.id.navigation_home:
                    fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("MAP");
                    if (fragment == null || !fragment.isVisible()) {
                        showFragment(MapFragment.newInstance(cameraPositionCoordinates), "MAP");
                    }
                    break;
                case R.id.navigation_favorites:

                    /*Intent intent = new Intent(MainActivity.this, PerformerActivity.class);
                    startActivity(intent);*/
                    fragment = (Fragment) getSupportFragmentManager().findFragmentByTag("FAVORITES");
                    if (fragment == null || !fragment.isVisible()) {
                        Log.d(TAG," Favorites work!");
                        showFragment(FavoritesFragment.newInstance(context),"FAVORITES");
                    }
                    break;
            }
            return true;
        }
    };

    protected void showFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment, tag);
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        PerformerLabManipulator lab = PerformerLabManipulator.get(this);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        //mover camera to last known location
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling permission access

            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            cameraPositionCoordinates = new LatLng(54, 25);
            Log.d(TAG, " no permisson to last location");
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                cameraPositionCoordinates = new LatLng(location.getLatitude(), location.getLongitude());


                                Log.d(TAG, location.getAltitude() + " " + location.getLongitude());
                            }
                        }
                    });
        }
        cameraPositionCoordinates = new LatLng(54, 25);
        new FetchCountries().execute();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);


    }
    //TODO make navigation on back press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkPlayServices()) finish();
    }

    private class FetchCountries extends AsyncTask<Void, Void, List<Country>> {
        @Override
        protected List<Country> doInBackground(Void... voids) {
            Log.d(TAG, " FetchCountries in WORK");
            return new FotolisoFetchr().fetchCountries();
        }

        @Override
        protected void onPostExecute(List<Country> countries) {
            super.onPostExecute(countries);
            CountrySingleton.get(countries);
            showFragment(MapFragment.newInstance(cameraPositionCoordinates), "MAP");

        }
    }


    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }
}

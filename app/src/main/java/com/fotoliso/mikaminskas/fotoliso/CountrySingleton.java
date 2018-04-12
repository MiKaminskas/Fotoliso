package com.fotoliso.mikaminskas.fotoliso;

import android.content.Context;

import java.util.List;

/**
 * Created by misha on 3/21/2018.
 */

public class CountrySingleton {
    private static CountrySingleton sCountrySingleton;
    private static List<Country> countryList;

    public static CountrySingleton get(/*Context context,*/ List<Country> countryList) {
        if (sCountrySingleton == null) {
            sCountrySingleton = new CountrySingleton(/*context,*/ countryList);
        }
        return sCountrySingleton;
    }
    public static List<Country> getCountryList(){
        return countryList;
    }
    // TODO context?!
    private CountrySingleton(/*Context context, */List<Country> countryList) {
        this.countryList = countryList;
    }
}

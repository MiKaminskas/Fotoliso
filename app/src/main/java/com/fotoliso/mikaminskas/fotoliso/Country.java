package com.fotoliso.mikaminskas.fotoliso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by misha on 3/18/2018.
 */

public class Country {
    private String name;
    private int performersCount;
    private String id;
    private double latitude;
    private double longitude;
   private int citiesCount;
   private int filledCities;
   private String code;


    Country(JSONObject country) throws JSONException {
        this.name = country.getString("name");
        this.latitude = country.getDouble("latitude");
        this.longitude = country.getDouble("longitude");
        this.performersCount = country.getInt("performersCount");
        this.citiesCount = country.getInt("citiesCount");
        this.filledCities = country.getInt("filledCities");
        this.code = country.getString("code");
    }

    public String getName() {
        return name;
    }

    public int getPerformersCount() {
        return performersCount;
    }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getCitiesCount() {
        return citiesCount;
    }

    public int getFilledCities() {
        return filledCities;
    }

    public String getCode() {
        return code;
    }
}

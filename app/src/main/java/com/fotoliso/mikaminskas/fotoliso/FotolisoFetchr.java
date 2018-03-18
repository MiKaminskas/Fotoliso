package com.fotoliso.mikaminskas.fotoliso;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by misha on 3/18/2018.
 */

public class FotolisoFetchr {
    private static final String TAG = "FotolisoFetcher";

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with" + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }
    public String getUrlString(String urlSpec) throws IOException{
        return new String((getUrlBytes(urlSpec)));
    }

    public List<Country> fetchCountries(){
        try {
            String base = new String("mappoints");
            String url = Uri.parse("http://appsrvr.fotoliso.com/")
                    .buildUpon()
                    .appendQueryParameter("get",base)
                    .appendQueryParameter("sum",md5("1.00" + "AhHooNgoh5ie"+ base.toLowerCase()  + "irei8ooPaero"))
                    .build().toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, jsonString);
            return parseCountry(jsonString);
        } catch (IOException e) {
            Log.e(TAG,"Failed to fetch URL:", e);
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //some magic here
    private String md5(String encTarget){
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        }
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while ( md5.length() < 32 ) {
            md5 = "0"+md5;
        }
        Log.d(TAG,md5);
        return md5;
    }

    public List<Country> parseCountry(String jsonString) throws JSONException {
        List<Country> countryList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject data = jsonObject.getJSONObject("data");
        String countriesCount = data.getString("countriesCount");
        JSONArray countries = data.getJSONArray("countries");
        for (int i = 0; i<Integer.parseInt(countriesCount); i++){
                JSONObject currCountry = countries.getJSONObject(i);
                Country newCountry = new Country(currCountry);
                countryList.add(newCountry);
        }
        return countryList;
    }
    public List<Perfmormer> fetchPerformers(){
        List<Perfmormer> perormersList = new ArrayList<>();
        String base = "performers ";
        String url = Uri.parse("http://appsrvr.fotoliso.com/")
                .buildUpon()
                .appendQueryParameter("get",base)
                .appendQueryParameter("sum",md5("1.00" + "AhHooNgoh5ie"+ base.toLowerCase()  + "irei8ooPaero"))
                .build().toString();

        return perormersList;
    }
    public List<Perfmormer> parsePerformer(String jsonString) throws JSONException {
        List<Perfmormer> performerList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);

        return  performerList;
    }
}

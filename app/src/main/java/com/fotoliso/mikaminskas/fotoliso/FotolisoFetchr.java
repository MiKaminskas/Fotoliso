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


    public List<Performer> fetchPerformers(String countryID) throws IOException, JSONException {
        String base = "performers";
        String url = Uri.parse("http://appsrvr.fotoliso.com/")
                .buildUpon()
                .appendQueryParameter("get", base)
                .appendQueryParameter("sum",md5("1.00" + "AhHooNgoh5ie"+ base.toLowerCase()  + "irei8ooPaero"))
                .appendQueryParameter("country", countryID)
                .build().toString();
        String jsonString = getUrlString(url);
        Log.d(TAG,jsonString);

        return parsePerformers(jsonString);
    }

    public List<Performer> parsePerformers(String jsonString) throws JSONException {
        List<Performer> performerList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject data = jsonObject.getJSONObject("data");
        int usersCount = Integer.parseInt(data.getString("usersCount"));
        JSONArray users = data.getJSONArray("users");
        for (int i = 0; i<usersCount; i++){
            JSONObject currUser = users.getJSONObject(i);
            Performer newUser = new Performer();/* = parsePerformer(currUser);*/

            newUser.setId(currUser.getString("id"));
            newUser.setLogin(currUser.getString("login"));
            newUser.setFio(currUser.getString("fio"));
            newUser.setContacts(currUser.getString("contacts"));
            newUser.setAvatar(currUser.getString("avatar"));
            newUser.setRegistered(currUser.getString("registered"));
            newUser.setName(currUser.getString("name"));
            newUser.setSurname(currUser.getString("surname"));
            newUser.setCity(currUser.getString("city"));
            newUser.setAva_thumb(currUser.getString("ava_thumb"));
            newUser.setRating(currUser.getString("rating"));
            newUser.setReviews(currUser.getString("reviews"));

            performerList.add(newUser);
        }


        return  performerList;
    }

    public Performer fetchPerformer(String performerID) throws IOException, JSONException {
        String base = "performer";
        String url = Uri.parse("http://appsrvr.fotoliso.com/")
                .buildUpon()
                .appendQueryParameter("get", base)
                .appendQueryParameter("sum", md5("1.00" + "AhHooNgoh5ie" + base.toLowerCase() + "irei8ooPaero"))
                .appendQueryParameter("id", performerID)
                .build().toString();
        String jsonString = getUrlString(url);

        Log.d(TAG, jsonString);

        return parsePerformer(jsonString);
    }

    private Performer parsePerformer(String jsonString) throws JSONException {
        Performer user = new Performer();
        Log.d(TAG,"parsePerformer, String = " + jsonString);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONObject performer = data.getJSONObject("performer");
        user.setId(performer.getString("id"));
        user.setLogin(performer.getString("login"));
        user.setFio(performer.getString("fio"));
        user.setContacts(performer.getString("contacts"));
        user.setRegistered(performer.getString("registered"));
        user.setName(performer.getString("name"));
        user.setSurname(performer.getString("surname"));
        user.setStringid(performer.getString("stringid"));
        user.setCity(performer.getString("city"));
        user.setAva_thumb(performer.getString("ava_thumb"));
        user.setRating(performer.getString("rating"));
        user.setReviews(performer.getString("reviews"));
        user.setVk_url(performer.getString("vk_url"));
        user.setFb_url(performer.getString("fb_url"));
        user.setGp_url(performer.getString("gp_url"));
        user.setYt_url(performer.getString("yt_url"));
        user.setV1_url(performer.getString("v1_url"));
        user.setV2_url(performer.getString("v2_url"));
        user.setV3_url(performer.getString("v3_url"));
        user.setLanguages(performer.getString("languages"));
        user.setAbout(performer.getString("about"));

        return user;
    }
}

package com.fotoliso.mikaminskas.fotoliso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

public class PerformerActivity extends AppCompatActivity {
    private final String TAG = "PerformerActivity";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
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
}

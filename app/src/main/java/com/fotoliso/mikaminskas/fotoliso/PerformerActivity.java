package com.fotoliso.mikaminskas.fotoliso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;

public class PerformerActivity extends AppCompatActivity {
    private final String TAG = "PerformerActivity";
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    // TODO set Image
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar2);
        linearLayout = findViewById(R.id.performer_main_linear);

        new FetchPerformer().execute(getIntent().getExtras().getString("ID"));

    }

    private class FetchPerformer extends AsyncTask<String, Void, Performer> {
        /*private String mPerformerID;

        FetchPerformer(String performerID) {
            this.mPerformerID = performerID;
        }*/

        @Override
        protected Performer doInBackground(String... mPerformerID) {
            Log.d(TAG, " FetchPerformer in WORK");
            try {
                return new FotolisoFetchr().fetchPerformer(mPerformerID[0]);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Performer performer) {
            super.onPostExecute(performer);
            progressBar.setVisibility(View.GONE);

            linearLayout.setVisibility(View.VISIBLE);


            performer.printObjectInLog();
            renderPerformerFields(performer);

        }
    }

    private void renderPerformerFields(Performer performer) {
        if (!performer.getName().equals("")){
            ((CardView)findViewById(R.id.fio_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_fio)).setText(performer.getName());
        }

        //TODO convert from id to name
        if (!performer.getCity().equals("")){
            ((CardView)findViewById(R.id.city_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_city)).setText(performer.getCity());
        }

        /*if (!performer.getSpecialyty().equals("")){
            ((CardView)findViewById(R.id.speciality_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_speciality)).setText(performer.getSpeciality());
        }*/
        /*if (!performer.getGenre().equals("")){
            ((CardView)findViewById(R.id.genre_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_genre)).setText(performer.getGenre());
        }*/
        if (!performer.getLanguages().equals("")){
            ((CardView)findViewById(R.id.language_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_langluage_list)).setText(performer.getName());
        }
        if (!performer.getAbout().equals("")){
            ((CardView)findViewById(R.id.about_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_about_my_self)).setText(performer.getAbout());
        }

        if (!performer.getVk_url().equals("")) {
            ((CardView)findViewById(R.id.vk_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_vk_link)).setText(performer.getVk_url());
        }
        if (!performer.getFb_url().equals("")) {
            ((CardView)findViewById(R.id.fb_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_fb_link)).setText(performer.getFb_url());

        }
        if (!performer.getGp_url().equals("")) {
            ((CardView)findViewById(R.id.instagram_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_instagram_link)).setText(performer.getGp_url());

        }
        /*if (!performer.getSite().equals("")){
            ((CardView)findViewById(R.id.site_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_site_link)).setText(performer.getSite());
        }*/
        /*if (!performer.getVimeo().equals("")){
            ((CardView)findViewById(R.id.vimeo_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_vimeo_link)).setText(performer.getVimeo());
        }*/
        if (!performer.getYt_url().equals("")) {
            ((CardView)findViewById(R.id.youtube_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_youtube_link)).setText(performer.getYt_url());
        }
        if (!performer.getGp_url().equals("")) {
            ((CardView)findViewById(R.id.gp_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_googleplus_link)).setText(performer.getGp_url());
        }
        /*if (!performer.getPhone().equals("")) {
            ((CardView)findViewById(R.id.phone_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_phone_number)).setText(performer.gePhone());
        } */
        /*if (!performer.getPrice().equals("")) {
            ((CardView)findViewById(R.id.price_container)).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.performer_price)).setText(performer.getPrice());
        } */
    }
}

package com.fotoliso.mikaminskas.fotoliso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by misha on 3/22/2018.
 */

abstract class PerformerListFragment extends Fragment {
    private static final String TAG = "PerformerListFragment";
    private RecyclerView mPerformersView;
    private PerformerAdapter mAdapter;
    private ThumbnailDownloader<PerformerHolder> thumbnailDownloader;
    /*private static List<Performer> mPerformers;*/

    /*abstract PerformerListFragment createPerformerListFragment();*/

    abstract List<Performer> setPerformersList();

/*
    private PerformerListFragment newInstance(List<Performer> performers) {
        mPerformers = performers;
        PerformerListFragment fragment = createPerformerListFragment();
        return fragment;
    }
*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.performers_list_fragment, container, false);
        mPerformersView = (RecyclerView) view.findViewById(R.id.performers_recycler_view);
        mPerformersView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Handler responseHandler = new Handler();
        thumbnailDownloader = new ThumbnailDownloader<>(responseHandler);
        thumbnailDownloader.setThumbnailDownloadListener(new ThumbnailDownloader.ThumbnailDownloadListener<PerformerHolder>() {
            @Override
            public void onThumbnailDownloaded(PerformerHolder performerHolder, Bitmap bitmap) {
                Drawable drawable = new BitmapDrawable(getResources(),bitmap);
                performerHolder.bindDrawable(drawable);
            }
        });
        thumbnailDownloader.start();
        thumbnailDownloader.getLooper();

        updateUI();

        return view;
    }

    private void updateUI() {
        mAdapter = new PerformerAdapter(setPerformersList());
        mPerformersView.setAdapter(mAdapter);
    }

    private class PerformerHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public TextView mReviewCountTextView;
        public RatingBar mPerformerRating;
        private ImageView mAvaterView;

        public PerformerHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.performer_name_textview);
            mReviewCountTextView = (TextView) itemView.findViewById(R.id.performer_review_counter_textview);
            mPerformerRating = (RatingBar) itemView.findViewById(R.id.performer_ratingbar);
            mAvaterView = (ImageView)itemView.findViewById(R.id.performer_avatar_imageview);

        }
        public void bindDrawable(Drawable drawable){
            mAvaterView.setImageDrawable(drawable);
        }
    }

    private class PerformerAdapter extends RecyclerView.Adapter<PerformerHolder> {
        private List<Performer> mPerformers;
        private Performer performer;

        public PerformerAdapter(List<Performer> performers) {
            this.mPerformers = setPerformersList();

        }

        @Override
        public PerformerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.performer_card, parent, false);
            return new PerformerHolder(view);
        }

        @Override
        public void onBindViewHolder(PerformerHolder holder, final int position) {
            Performer performer = mPerformers.get(position);
            holder.mNameTextView.setText(performer.getName());
            holder.mReviewCountTextView.setText(getString(R.string.review) + " " + performer.getReviews());
            holder.mPerformerRating.setRating(Float.parseFloat(performer.getRating()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG,"trying to start new activity");
                    Context context =  view.getContext();
                    Intent intent = new Intent(context,PerformerActivity.class);
                    Bundle passId = new Bundle();
                    passId.putString("ID", mPerformers.get(position).getId());
                    intent.putExtras(passId);
                    context.startActivity(intent);
                }
            });

            thumbnailDownloader.queueThumbnail(holder, performer.getAva_thumb());

            Drawable image = getResources().getDrawable(R.drawable.ic_launcher_background);
            holder.bindDrawable(image);
        }

        @Override
        public int getItemCount() {
            return mPerformers.size();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        thumbnailDownloader.clearQueue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        thumbnailDownloader.quit();
        Log.d(TAG,"Background thread destroyed");
    }
}

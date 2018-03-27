package com.fotoliso.mikaminskas.fotoliso;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by misha on 3/22/2018.
 */

public class PerformerListFragment extends Fragment {
    private RecyclerView mPerformersView;
    private PerformerAdapter mAdapter;
    private static List<Performer> mPerformers;


    public static PerformerListFragment newInstance(List<Performer> performers){
        mPerformers = performers;
        PerformerListFragment fragment = new PerformerListFragment();
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.performers_list_fragment,container,false);
        mPerformersView = (RecyclerView) view.findViewById(R.id.performers_recycler_view);
        mPerformersView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }
    private void updateUI(){
        /*List<Performer> performers = new ArrayList<>();*/
        /*for (int i =0; i<100; i++){
            Performer performer = new Performer();
            performer.setName(Integer.toString(i));
            performers.add(performer);
        }*/
        mAdapter = new PerformerAdapter(mPerformers);
        mPerformersView.setAdapter(mAdapter);
    }

    private class PerformerHolder extends RecyclerView.ViewHolder{
        public TextView mNameTextView;
        public TextView mReviewCountTextView;

        public PerformerHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(R.id.performer_name_textview);
            mReviewCountTextView = (TextView) itemView.findViewById(R.id.performer_review_counter_textview);
        }
    }
    private class PerformerAdapter extends RecyclerView.Adapter<PerformerHolder>{
        private List<Performer> mPerformers;
        public PerformerAdapter(List<Performer> performers){
            this.mPerformers = performers;

        }
        @Override
        public PerformerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.performer_card_experement,parent,false);
            return new PerformerHolder(view);
        }

        @Override
        public void onBindViewHolder(PerformerHolder holder, int position) {
            Performer performer = mPerformers.get(position);
            holder.mNameTextView.setText(performer.getName());
            holder.mReviewCountTextView.setText(getString(R.string.review)+ " " + performer.getReviews());

        }

        @Override
        public int getItemCount() {
            return mPerformers.size();
        }
    }
}

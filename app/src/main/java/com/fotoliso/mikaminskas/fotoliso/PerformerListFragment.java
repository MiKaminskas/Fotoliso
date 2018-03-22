package com.fotoliso.mikaminskas.fotoliso;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by misha on 3/22/2018.
 */

public class PerformerListFragment extends Fragment {
    private RecyclerView mPerformersView;
    private PerformerAdapter mAdapter;

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
        List<Performer> performers = new ArrayList<>();
        for (int i =0; i<100; i++){
            Performer performer = new Performer();
            performer.setName(Integer.toString(i));
            performers.add(performer);
        }
        mAdapter = new PerformerAdapter(performers);
        mPerformersView.setAdapter(mAdapter);
    }

    private class PerformerHolder extends RecyclerView.ViewHolder{
        public TextView mTitleTextView;

        public PerformerHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView)itemView;
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
            View view = layoutInflater.inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
            return new PerformerHolder(view);
        }

        @Override
        public void onBindViewHolder(PerformerHolder holder, int position) {
            Performer performer = mPerformers.get(position);
            holder.mTitleTextView.setText(performer.getName());

        }

        @Override
        public int getItemCount() {
            return mPerformers.size();
        }
    }
}

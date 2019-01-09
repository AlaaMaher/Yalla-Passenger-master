package com.asi.yalla_passanger_eg.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.asi.yalla_passanger_eg.Models.PassengerCompletedTripsModel;
import com.asi.yalla_passanger_eg.R;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by ASI on 4/3/2017.
 */

public class tripsHisAdpter extends RecyclerView.Adapter<tripsHisAdpter.StatisticHolder> {

    private final LayoutInflater mInflater;
    private final List<PassengerCompletedTripsModel> mModels;
    private int lastPosition=-1;
    Context context;

    public tripsHisAdpter(Context context, List<PassengerCompletedTripsModel> models) {
        mInflater = LayoutInflater.from(context);
        mModels = models;
        this.context=context;
    }

    @Override
    public StatisticHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.tripshistoryitem, parent, false);
        return new StatisticHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final StatisticHolder holder, int position) {
        final PassengerCompletedTripsModel model = mModels.get(position);
        holder.bind(model);



    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<PassengerCompletedTripsModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<PassengerCompletedTripsModel> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final PassengerCompletedTripsModel model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(List<PassengerCompletedTripsModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final PassengerCompletedTripsModel model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }
    private void applyAndAnimateMovedItems(List<PassengerCompletedTripsModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final PassengerCompletedTripsModel model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
    public PassengerCompletedTripsModel removeItem(int position) {
        final PassengerCompletedTripsModel model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }
    public void addItem(int position, PassengerCompletedTripsModel model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }
    public void moveItem(int fromPosition, int toPosition) {
        final PassengerCompletedTripsModel model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }





    class StatisticHolder extends RecyclerView.ViewHolder {

        public TextView date,ditance,cost;

        ImageView passengerpic;
        RatingBar ratingBar;



        public StatisticHolder(final View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.tvdate);
            ratingBar= (RatingBar) itemView.findViewById(R.id.review_ratingBar);
            ditance= (TextView) itemView.findViewById(R.id.tvDistace);
            passengerpic= (ImageView) itemView.findViewById(R.id.ivPassanger);
            cost= (TextView) itemView.findViewById(R.id.tvCost);

        }

        public void bind(PassengerCompletedTripsModel model) {

            date.setText(model.getDate());
            ratingBar.setRating(Float.parseFloat(model.getTripRate()));
            ditance.setText(model.getTripDistance());
            cost.setText(model.getTripFare());
            Picasso.with(context).load(model.getDriverImage()).error(R.drawable.user).placeholder(R.drawable.user);
            cost.setText(model.getTripFare()+" SAR");


        }


    }


}
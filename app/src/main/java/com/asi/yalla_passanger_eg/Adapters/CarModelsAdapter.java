package com.asi.yalla_passanger_eg.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asi.yalla_passanger_eg.Models.CarModelsModel;
import com.asi.yalla_passanger_eg.R;

import java.util.List;


/**
 * Created by ASI on 4/3/2017.
 */

public class CarModelsAdapter extends RecyclerView.Adapter<CarModelsAdapter.StatisticHolder> {

    private final LayoutInflater mInflater;
    private final List<CarModelsModel> mModels;
    private int lastPosition=-1;
    Context context;
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;

    public CarModelsAdapter(Context context, List<CarModelsModel> models) {
        mInflater = LayoutInflater.from(context);
        mModels = models;
        this.context=context;
        mPref = context.getSharedPreferences("person", Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    @Override
    public StatisticHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.list_item_car_model, parent, false);
        return new StatisticHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final StatisticHolder holder, int position) {
        final CarModelsModel model = mModels.get(position);
        holder.bind(model);
        if (mModels.get(position).isSelected()) {
            holder.taxitype.setBackgroundColor(Color.parseColor("#7d12a8"));
            holder.tv_model_name.setTextColor(context.getResources().getColor(R.color.white));
            holder.ivCartype.setColorFilter(ContextCompat.getColor(context,R.color.white));
        } else {
            holder.taxitype.setBackgroundColor(Color.TRANSPARENT);
            holder.tv_model_name.setTextColor(context.getResources().getColor(R.color.gray));
            holder.ivCartype.setColorFilter(ContextCompat.getColor(context,R.color.gray));
        }



    }
    public void setSelected(int pos) {
        try {
            if (mModels.size() > 1) {
                mModels.get(mPref.getInt("position", 0)).setSelected(false);
                mEditor.putInt("position", pos);
                mEditor.commit();
            }
            mModels.get(pos).setSelected(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<CarModelsModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<CarModelsModel> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final CarModelsModel model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(List<CarModelsModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final CarModelsModel model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }
    private void applyAndAnimateMovedItems(List<CarModelsModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final CarModelsModel model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
    public CarModelsModel removeItem(int position) {
        final CarModelsModel model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }
    public void addItem(int position, CarModelsModel model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }
    public void moveItem(int fromPosition, int toPosition) {
        final CarModelsModel model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }





    class StatisticHolder extends RecyclerView.ViewHolder {

        public TextView tv_model_name;

        LinearLayout taxitype;
        ImageView ivCartype;




        public StatisticHolder(final View itemView) {
            super(itemView);

            tv_model_name = (TextView) itemView.findViewById(R.id.tv_model_name);
            taxitype= (LinearLayout) itemView.findViewById(R.id.taxitype);
            ivCartype= (ImageView) itemView.findViewById(R.id.iv_car);


        }

        public void bind(CarModelsModel model) {


            tv_model_name.setText(model.getModelName());
            if (model.getModelId().equals("1"))
            {
                ivCartype.setImageDrawable(context.getResources().getDrawable(R.drawable.car1_unfocus));
            }else if (model.getModelId().equals("2"))
            {
                ivCartype.setImageDrawable(context.getResources().getDrawable(R.drawable.car22_focus));
            }else if (model.getModelId().equals("3"))
            {
                ivCartype.setImageDrawable(context.getResources().getDrawable(R.drawable.car3_unfocus));
            }else if (model.getModelId().equals("4"))
            {
                ivCartype.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_delivery_truck));
            }



        }


    }



}
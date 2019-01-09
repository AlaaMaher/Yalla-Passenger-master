package com.asi.yalla_passanger_eg.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.asi.yalla_passanger_eg.AppController;
import com.asi.yalla_passanger_eg.Constants;
import com.asi.yalla_passanger_eg.EditFavPlaceActivity;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.Models.FavTripsModel;
import com.asi.yalla_passanger_eg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;


/**
 * Created by ASI on 4/3/2017.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.StatisticHolder> {

    private final LayoutInflater mInflater;
    private final List<FavTripsModel> mModels;
    private int lastPosition=-1;
    Context context;

    public FavAdapter(Context context, List<FavTripsModel> models) {
        mInflater = LayoutInflater.from(context);
        mModels = models;
        this.context=context;
    }

    @Override
    public StatisticHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.fav_list_item, parent, false);
        return new StatisticHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final StatisticHolder holder, final int position) {
        final FavTripsModel model = mModels.get(position);

        holder.bind(model);
        holder.tvDropPlace.setText(model.getDropPlace());
        holder.tvPicUpPlace.setText(model.getPickupPlace());
        holder.tvComments.setText(model.getComments());
        holder.tvNotes.setText(model.getNotes());
        holder.ivDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                deleteFavItem(model.getFavouriteId(),position);
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(context, EditFavPlaceActivity.class);
                intent.putExtra("dropPlace",model.getDropPlace());
                intent.putExtra("pickuoPlace",model.getPickupPlace());
                intent.putExtra("type",model.getLocationType());
                intent.putExtra("plat",model.getPickupLatitude());
                intent.putExtra("plng",model.getPickLongitude());
                intent.putExtra("dlat",model.getDropLatitude());
                intent.putExtra("dlng",model.getDropLongitude());
                intent.putExtra("FavouriteId",model.getFavouriteId());
                intent.putExtra("PassengerId",model.getPassengerId());
                intent.putExtra("comment",model.getComments());
                intent.putExtra("notes",model.getNotes());
                context.startActivity(intent);
            }
        });

        if (model.getLocationType().equals("1"))
        {
            holder.ivlocationType.setImageDrawable(context.getResources().getDrawable(R.drawable.home));
        }else   if (model.getLocationType().equals("2"))
        {
            holder.ivlocationType.setImageDrawable(context.getResources().getDrawable(R.drawable.officebag));
        }else   if (model.getLocationType().equals("3"))
        {
            holder.ivlocationType.setImageDrawable(context.getResources().getDrawable(R.drawable.airplane));
        }else {
            holder.ivlocationType.setImageDrawable(context.getResources().getDrawable(R.drawable.stared));
        }


    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<FavTripsModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<FavTripsModel> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final FavTripsModel model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }
    private void applyAndAnimateAdditions(List<FavTripsModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final FavTripsModel model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }
    private void applyAndAnimateMovedItems(List<FavTripsModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final FavTripsModel model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }
    public FavTripsModel removeItem(int position) {
        final FavTripsModel model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }
    public void addItem(int position, FavTripsModel model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }
    public void moveItem(int fromPosition, int toPosition) {
        final FavTripsModel model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }





    class StatisticHolder extends RecyclerView.ViewHolder {

        public TextView tvPicUpPlace,tvDropPlace,tvComments,tvNotes;
        ImageView ivDelete,ivEdit,ivlocationType;





        public StatisticHolder(final View itemView) {
            super(itemView);

            tvPicUpPlace = (TextView) itemView.findViewById(R.id.tvPicUpPlace);
            tvDropPlace= (TextView) itemView.findViewById(R.id.tvDropPlace);
            tvComments= (TextView) itemView.findViewById(R.id.tvComments);
            tvNotes= (TextView) itemView.findViewById(R.id.tvNotes);
            ivDelete= (ImageView) itemView.findViewById(R.id.ivDelete);
            ivEdit= (ImageView) itemView.findViewById(R.id.ivEdit);
            ivlocationType= (ImageView) itemView.findViewById(R.id.ivlocationType);


        }

        public void bind(FavTripsModel model) {

        }


    }

    public void deleteFavItem(String favId, final int postion)
    {
        final ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", new SQLiteHandler(context).getUserDetails().get("uid"));
        postParam.put("FavouriteId",favId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "DeleteFavourite", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {



                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + "USER Deleted  DATA", response.toString());
                        progressDialog.dismiss();


                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.SUCCSESS))
                            {
                                removeItem(postion);
                                notifyDataSetChanged();
                                Toasty.success(context,context.getResources().getString(R.string.itemdeleted),Toast.LENGTH_LONG,true).show();
                            }
                            else if (flag.equals(Constants.INVALID_REQUEST))
                            {
                                //invalid request
                            }
                            else if (flag.equals(Constants.NO_FAV_PLACE))
                            {
//                                //no fav place
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e(TAG, "Error, " + error.getMessage());

                progressDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError)
                {
                    //Toasty.error(Login.this,"TimeoutError",Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    //Toasty.error(Login.this,"AuthFailureError",Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof ServerError)
                {
                    //Toasty.error(Login.this,"ServerError",Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof NetworkError)
                {
                    //Toasty.error(Login.this,"NetworkError",Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof ParseError)
                {
                    //Toasty.error(Login.this,"ParseError",Toast.LENGTH_LONG,true).show();
                }
            }
        })
        {


            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "TAG");
    }



}
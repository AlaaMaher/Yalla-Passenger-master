package com.asi.yalla_passanger_eg;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
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
import com.asi.yalla_passanger_eg.Models.NewTripDataModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

public class TripCompletedActivity extends AppCompatActivity
{
    RatingBar review_ratingBar;
    public int Rate;
    private Dialog dialog;
    TextView tvTripId,tvTotalCost;

    private String tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_completed);
        ini();
    }
    private void ini() {

        getTripDetial(Constants.getNewTrip(TripCompletedActivity.this));
        tvTripId= (TextView) findViewById(R.id.tvtripid);
        tvTripId.setText(tripId);
        tvTotalCost= (TextView) findViewById(R.id.tvtotalcost);
        tvTotalCost.setText(getIntent().getStringExtra("totalcost")+" "+"SAR");

    }

    public void BackToMain(View view) {
        startActivity(new Intent(TripCompletedActivity.this,MainActivity.class));
        finish();
    }

    public void RatePassanger(View view) {

        // custom dialog
        dialog = new Dialog(TripCompletedActivity.this);
        dialog.setContentView(R.layout.customratedialoge);
        dialog.setTitle(getResources().getString(R.string.rateDriver));
        FancyButton Btn_rate= (FancyButton) dialog.findViewById(R.id.btn_rate);
        review_ratingBar= (RatingBar) dialog.findViewById(R.id.review_ratingBar);
        review_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Rate= (int) v;

            }
        });


        dialog.show();
        Btn_rate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                rateDriver(String.valueOf(Rate));
            }
        });}

    private void rateDriver(String s)
    {
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("Ratings", s);
        postParam.put("TripId",tripId);
        postParam.put("Comments","");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "DriverRating", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {



                    @Override
                    public void onResponse(JSONObject response)
                    {

                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.RATE_UPADTED))
                            {
                                Toasty.info(TripCompletedActivity.this,getResources().getString(R.string.rateupdated), Toast.LENGTH_LONG,false).show();
                                dialog.dismiss();
                            }
                            else if (flag.equals(Constants.INVALID_REQUEST))
                            {
                                //invalid request
                            }
                            else if (flag.equals(Constants.INVALID_TRIP))
                            {
//                                //invalid_trip
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

    /**
     * get the detail from shared pref
     * @param response
     * */
    public  void getTripDetial(String response)
    {

        NewTripDataModel newTripDataModel = new Gson().fromJson(response, NewTripDataModel.class);

        tripId=newTripDataModel.getTripId();

    }



}

package com.asi.yalla_passanger_eg;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.asi.yalla_passanger_eg.Serveces.DriverUpdatesService;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RequestCarActivity extends AppCompatActivity {


    RotateLoading rotateLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_car);

        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);
        rotateLoading.start();
        SaveBooking();
    }

    public void CloseRequest(View view) {
        finish();
    }


    /**
     * Save booking this method will be used when user click #bookNow btn
     */

    public void SaveBooking() {
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("Latitude", getIntent().getStringExtra("Latitude"));
        postParam.put("Longitude", getIntent().getStringExtra("Longitude"));
        postParam.put("PickupPlace", getIntent().getStringExtra("PickupPlace"));
        postParam.put("DropPlace", getIntent().getStringExtra("DropPlace"));
        postParam.put("DropLatitude", getIntent().getStringExtra("DropLatitude"));
        postParam.put("DropLongitude", getIntent().getStringExtra("DropLongitude"));
        postParam.put("PickUpTime", getIntent().getStringExtra("PickUpTime"));
        postParam.put("CityName", getIntent().getStringExtra("CityName"));
        postParam.put("ModelId", getIntent().getStringExtra("ModelId"));
        postParam.put("DistanceAway", getIntent().getStringExtra("DistanceAway"));
        postParam.put("PassengerId", getIntent().getStringExtra("PassengerId"));
        postParam.put("RequestType", getIntent().getStringExtra("RequestType"));
        postParam.put("PromoCode", getIntent().getStringExtra("PromoCode"));
        postParam.put("NowAfter", getIntent().getStringExtra("NowAfter"));     //→ 0=Now, 1=Future
        postParam.put("Notes", getIntent().getStringExtra("Notes"));
        postParam.put("RoundTrip", getIntent().getStringExtra("RoundTrip"));
        JSONObject obj = new JSONObject(postParam);
        Log.e(Constants.TAG, obj.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "SaveBooking", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    //   56 - 57 - 58 - 59 - 81 - 9 - 43

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(Constants.TAG + "USER DATA", response.toString());
                        try {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.SUCCSESS)) {
                                //===== save trip data ======
                                Constants.SaveTrip(response.toString(), RequestCarActivity.this);
                                PackageManager pm = getApplicationContext().getPackageManager();
                                ComponentName componentName = new ComponentName(getApplicationContext(), DriverUpdatesService.class);
                                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                        PackageManager.DONT_KILL_APP);
                                startService(new Intent(RequestCarActivity.this, DriverUpdatesService.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            } else if (flag.equals(Constants.INVALID_REQUEST)) {
                                Log.e(Constants.TAG, "inavlid request");
                                finish();
                                // invalid request
                            } else if (flag.equals(Constants.NO_VIC_AVILABLE)) {
                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.nocaravalible), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                finish();
                            } else if (flag.equals(Constants.FOR_BOOK_LATER)) {
                                Log.e(Constants.TAG, "inavlid request");
                                //Booking request sent to dispatcher. Dispatcher will assign you driver soon
                            } else if (flag.equals(Constants.INVALID_PROMOCODE)) {

                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.invalidpromocode), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class));
                                finish();
                            } else if (flag.equals(Constants.PROMOCODE_NOT_STARTED_YET)) {
                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.Promocodenotstarted), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class));
                                finish();
                            } else if (flag.equals(Constants.PROMOCODE_LIMIT_EXCEED)) {
                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.promocodelimitexceed), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class));
                                finish();
                            } else if (flag.equals(Constants.YOUR_PROMOCODE_IS_EXPIRED)) {
                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.promocodeexpired), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class));
                                finish();
                            } else if (flag.equals(Constants.INVALID_PROMOCODE_CITY)) {
                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.incalidpromocodecity), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class));
                                finish();
                            } else if (flag.equals(Constants.USER_BLOCKED)) {
                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.userblocked), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class));
                                finish();
                            } else if (flag.equals(Constants.PASSENGER_IN_TRIP)) {
                                Toasty.info(RequestCarActivity.this, getResources().getString(R.string.youareintripnow), Toast.LENGTH_LONG, true).show();
                                startActivity(new Intent(RequestCarActivity.this, MainActivity.class));
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error, " + error.getLocalizedMessage());

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toasty.error(RequestCarActivity.this, "TimeoutError", Toast.LENGTH_LONG, true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(RequestCarActivity.this, "AuthFailureError", Toast.LENGTH_LONG, true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(RequestCarActivity.this, "ServerError", Toast.LENGTH_LONG, true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(RequestCarActivity.this, "NetworkError", Toast.LENGTH_LONG, true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(RequestCarActivity.this, "ParseError", Toast.LENGTH_LONG, true).show();
                }
            }
        }) {


            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }


        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "TAG");
    }


}

package com.asi.yalla_passanger_eg;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.Models.NewTripDataModel;
import com.asi.yalla_passanger_eg.Serveces.DriverReplayReciver;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;

import static com.asi.yalla_passanger_eg.R.id.map;

public class OnGoingActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, View.OnClickListener {
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    FancyButton btn_cancel_trip;
    TextView tvDriverName, tvTimeToReach;
    String tripId, Display, MobileNum;
    private SweetAlertDialog sweetAlertDialog;
    private String picLat, picLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_going);
        ini();
    }

    private void ini() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)        // 1 seconds, in milliseconds
                .setFastestInterval(1000); // 1 second, in milliseconds
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);
        tvDriverName = (TextView) findViewById(R.id.tvDriverName);
        tvTimeToReach = (TextView) findViewById(R.id.tvTimeToReach);
        getTripDetial(Constants.getNewTrip(OnGoingActivity.this));
        btn_cancel_trip = (FancyButton) findViewById(R.id.btn_cancel_trip);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        mLastLocation = location;

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onLocationChanged(Location location) {


        if (mCurrLocationMarker != null) mCurrLocationMarker.remove();

        mMap.setMyLocationEnabled(true);
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.moveCamera(center);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15));

        getDriverReplay();
        GetTimeAndDistance();


    }


    /**
     * get the detail from shared pref
     *
     * @param response
     */
    public void getTripDetial(String response) {

        NewTripDataModel newTripDataModel = new Gson().fromJson(response, NewTripDataModel.class);
        if (newTripDataModel != null) {
            tvDriverName.setText(newTripDataModel.getDriverName());
            tripId = newTripDataModel.getTripId();
            picLat = newTripDataModel.getPickupLatitude();
            picLng = newTripDataModel.getPickupLongitude();
            tvTimeToReach.setText(getResources().getString(R.string.esTripTime) + " " + newTripDataModel.getTimeToReachPassenger() + " " +
                    getResources().getString(R.string.min));
        }

    }


    /***
     *
     * if passenger want to cancel the trip this method will do that
     */

    public void cancelTrip(View view) {

        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);

        sweetAlertDialog.setTitleText(getResources().getString(R.string.areyousure))
                .setContentText(getResources().getString(R.string.youwanttocancelthetrip))
                .setConfirmText(getResources().getString(R.string.yes))
                .setCancelText(getResources().getString(R.string.no))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, OnGoingActivity.this);
                        CancelTrip();

                    }
                }).show();

    }


    /**
     * cancel the trip by the passenger
     */
    private void CancelTrip() {
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", new SQLiteHandler(OnGoingActivity.this).getUserDetails().get("uid"));
        postParam.put("TripId", tripId);
        postParam.put("Remarks", "change plan");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "CancelTripPassenger", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.PASSENGER_CANCEL_THE_TRIP)) {
                                Toasty.info(OnGoingActivity.this, getResources().getString(R.string.passengercancelthetrip), Toast.LENGTH_LONG, false).show();
                                sweetAlertDialog.dismiss();
                                startActivity(new Intent(OnGoingActivity.this, MainActivity.class));
                                finish();
                            } else if (flag.equals(Constants.INVALID_REQUEST)) {
                                //invalid request
                            } else if (flag.equals(Constants.INVALID_TRIP)) {
//                                //invalid_trip
                            } else if (flag.equals(Constants.PASSENGER_IN_TRIP)) {
                                Toasty.info(OnGoingActivity.this, getResources().getString(R.string.passengerintrip), Toast.LENGTH_LONG, false).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error, " + error.getMessage());

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //Toasty.error(Login.this,"TimeoutError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof AuthFailureError) {
                    //Toasty.error(Login.this,"AuthFailureError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ServerError) {
                    //Toasty.error(Login.this,"ServerError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof NetworkError) {
                    //Toasty.error(Login.this,"NetworkError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ParseError) {
                    //Toasty.error(Login.this,"ParseError",Toast.LENGTH_LONG,true).show();
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


    /**
     * get driver replay
     */

    public void getDriverReplay() {
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", new SQLiteHandler(OnGoingActivity.this).getUserDetails().get("uid"));
        postParam.put("TripId", tripId);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "GetPassengerUpdate", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + "Driver Replay --->", response.toString());

                        try {
                            String flag = response.getString("Flag");
                            Display = response.getString("Display");

                            if (Display.equals("1")) {
                                if (flag.equals(Constants.YOUR_BOOKING_CONFIRMED)) {
                                    ComponentName component = new ComponentName(OnGoingActivity.this, DriverReplayReciver.class);
                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                                    PackageManager.DONT_KILL_APP);
                                    Intent intent = new Intent();
                                    intent.setComponent(component);
                                    intent.putExtra("type", "1");
                                    sendBroadcast(intent);
                                    // =========  stop the BC receiver  component   =============


                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                                    PackageManager.DONT_KILL_APP);


                                } else if (flag.equals(Constants.YOUR_TAXI_HAS_BEEN_ARRIVED)) {

                                    ComponentName component = new ComponentName(OnGoingActivity.this, DriverReplayReciver.class);
                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                                    PackageManager.DONT_KILL_APP);
                                    Intent intent = new Intent();
                                    intent.setComponent(component);
                                    intent.putExtra("type", "2");

                                    sendBroadcast(intent);
                                    // =========  stop the BC receiver  component   =============
                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                                    PackageManager.DONT_KILL_APP);


                                } else if (flag.equals(Constants.YOUR_TRIP_HAS_BEEN_STARTED)) {

                                    btn_cancel_trip.setVisibility(View.GONE);
                                    tvTimeToReach.setVisibility(View.GONE);
                                    ComponentName component = new ComponentName(OnGoingActivity.this, DriverReplayReciver.class);
                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                                    PackageManager.DONT_KILL_APP);
                                    Intent intent = new Intent();
                                    intent.setComponent(component);
                                    intent.putExtra("type", "3");
                                    sendBroadcast(intent);
                                    // =========  stop the BC receiver  component   =============

                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                                    PackageManager.DONT_KILL_APP);

                                } else if (flag.equals(Constants.YOUR_TRIP_HAS_BEEN_COMPLETED)) {
                                    ComponentName component = new ComponentName(OnGoingActivity.this, DriverReplayReciver.class);
                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                                    PackageManager.DONT_KILL_APP);
                                    Intent intent = new Intent();
                                    intent.putExtra("type", "4");
                                    sendBroadcast(intent);
                                    // =========  stop the BC receiver  component   =============

                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                                    PackageManager.DONT_KILL_APP);
                                } else if (flag.equals(Constants.TRIP_FARE_UPDATED)) {
                                    ComponentName component = new ComponentName(OnGoingActivity.this, DriverReplayReciver.class);
                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                                                    PackageManager.DONT_KILL_APP);
                                    Intent intent = new Intent();
                                    intent.setComponent(component);
                                    intent.putExtra("type", "5");
                                    intent.putExtra("totalcost", response.getString("Fare"));
                                    sendBroadcast(intent);

                                    //=======  stop location updates ============
                                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, OnGoingActivity.this);
                                    //===========================================

                                    getPackageManager()
                                            .setComponentEnabledSetting(component, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                                    PackageManager.DONT_KILL_APP);
                                } else if (flag.equals(Constants.PASSENGER_CANCEL_THE_TRIP_)) {
                                    Intent intent = new Intent(OnGoingActivity.this, DriverReplayReciver.class);
                                    intent.putExtra("type", "6");
                                    sendBroadcast(intent);
                                } else if (flag.equals(Constants.Driver_CANCEL_THE_TRIP)) {
                                    Intent intent = new Intent(OnGoingActivity.this, DriverReplayReciver.class);
                                    intent.putExtra("type", "7");
                                    sendBroadcast(intent);
                                } else if (flag.equals(Constants.TRIP_NOT_YET_STARTED)) {
                                    Intent intent = new Intent(OnGoingActivity.this, DriverReplayReciver.class);
                                    intent.putExtra("type", "8");
                                    sendBroadcast(intent);
                                } else if (flag.equals(Constants.TRIP_ALREADY_CANCELLED)) {
                                    Intent intent = new Intent(OnGoingActivity.this, DriverReplayReciver.class);
                                    intent.putExtra("type", "9");
                                    sendBroadcast(intent);
                                } else if (flag.equals(Constants.TRIP_ALREADY_CANCELLED)) {
                                    Intent intent = new Intent(OnGoingActivity.this, DriverReplayReciver.class);
                                    intent.putExtra("type", "10");
                                    sendBroadcast(intent);
                                } else if (flag.equals(Constants.YOUR_REQUEST_HAS_BEEN_CANCELLED_BY_DRIVER_PLEASE_TRY_BOOKING_AGAIN)) {
                                    Intent intent = new Intent(OnGoingActivity.this, DriverReplayReciver.class);
                                    intent.putExtra("type", "11");
                                    sendBroadcast(intent);
                                } else if (flag.equals(Constants.INVALID_REQUEST)) {
                                    //invalid request
                                } else if (flag.equals(Constants.INVALID_TRIP)) {
//                                //invalid_trip
                                } else if (flag.equals(Constants.PASSENGER_IN_TRIP)) {
                                    Toasty.info(OnGoingActivity.this, getResources().getString(R.string.passengerintrip), Toast.LENGTH_LONG, false).show();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error, " + error.getMessage());

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //Toasty.error(Login.this,"TimeoutError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof AuthFailureError) {
                    //Toasty.error(Login.this,"AuthFailureError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ServerError) {
                    //Toasty.error(Login.this,"ServerError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof NetworkError) {
                    //Toasty.error(Login.this,"NetworkError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ParseError) {
                    //Toasty.error(Login.this,"ParseError",Toast.LENGTH_LONG,true).show();
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

    public void CallDriver(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + MobileNum));
        startActivity(callIntent);
    }

    /**
     * get distance and
     */
    private void GetTimeAndDistance() {

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + String.valueOf(mLastLocation.getLatitude()) + "," + String.valueOf(mLastLocation.getLongitude()) + "&destinations=" + picLat + "," + picLng + "&language=" + Constants.getChangeLang(OnGoingActivity.this) + "&key=AIzaSyCffGbuScXeTbN5mUxA3X1YS-DzZVujAq8";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONCE --->", response);
                JSONObject jsonRespRouteDistance, jsonRespRouteTime = null;
                try {
                    jsonRespRouteTime = new JSONObject(response)
                            .getJSONArray("rows")
                            .getJSONObject(0)
                            .getJSONArray("elements")
                            .getJSONObject(0)
                            .getJSONObject("duration");
                    String time = jsonRespRouteTime.get("text").toString();
                    tvTimeToReach.setText(getResources().getString(R.string.esTripTime) + " " + time);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ;
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        // Adding request to request queue
        int socketTimeout = 50000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }


}

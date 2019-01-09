package com.asi.yalla_passanger_eg;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.asi.yalla_passanger_eg.Models.TripDitalModel;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

import static com.asi.yalla_passanger_eg.Fragments.Home.autocomplete;


public class EditFavPlaceActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, AdapterView.OnClickListener
{
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private CameraPosition camPos;
    LocationRequest mLocationRequest;
    private TextView tvCurrentlocation;
    private AutoCompleteTextView edDropPlace;
    double droplat,droplng,piclat,piclang;
    String picPlace,dropPlace,placeType,pLat,pLng,dLat,dLng,favplaceId,passId,comment,notes;
    ImageView ivAddTofavPlaced;
    LinearLayout lay_fav_res1, lay_fav_res2, lay_fav_res3, lay_fav_res4, other_details;
    private Dialog r_mDialog;
    private EditText et_others;
    private TextView ok_others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fav_place);
        picPlace=getIntent().getStringExtra("pickuoPlace");
        dropPlace =getIntent().getStringExtra("dropPlace");
        placeType= getIntent().getStringExtra("type");
        pLat=getIntent().getStringExtra("plat");
        pLng=getIntent().getStringExtra("plng");
        dLat=getIntent().getStringExtra("dlat");
        dLng=getIntent().getStringExtra("dlng");
        favplaceId=getIntent().getStringExtra("FavouriteId");
        passId=getIntent().getStringExtra("PassengerId");
        comment=getIntent().getStringExtra("comment");
        notes=getIntent().getStringExtra("notes");
        ini();
    }

    private void ini() {
        tvCurrentlocation = (TextView) findViewById(R.id.autocurrentPlace);
        tvCurrentlocation.setText(picPlace);
        //tvChooseLocationType = (TextView) findViewById(R.id.tvChooseLocationType);
       // tvChooseLocationType.setVisibility(View.GONE);
        ivAddTofavPlaced= (ImageView) findViewById(R.id.ivAddTofavPlaced);
        ivAddTofavPlaced.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editPassFavPlace();
            }
        });
        edDropPlace = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        edDropPlace.setFocusable(false);
        edDropPlace.setAdapter(new GooglePlacesAutocompleteAdapter(EditFavPlaceActivity.this, R.layout.list_item_location));
        edDropPlace.setText(dropPlace);
        edDropPlace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                edDropPlace.setFocusable(true);
                tvCurrentlocation.setFocusable(false);
                //tvChooseLocationType.setVisibility(View.VISIBLE);
                //tvChooseLocationType.setText(getResources().getString(R.string.chooseDropLocation));
                droplat=mLastLocation.getLatitude();
                droplng=mLastLocation.getLongitude();

            }
        });
        tvCurrentlocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                edDropPlace.setFocusable(false);
                tvCurrentlocation.setFocusable(true);
//                tvChooseLocationType.setVisibility(View.VISIBLE);
//                tvChooseLocationType.setText(getResources().getString(R.string.choosepickLocation));
                piclat=mLastLocation.getLatitude();
                piclang=mLastLocation.getLongitude();
            }
        });

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
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        final View r_view = View.inflate(EditFavPlaceActivity.this, R.layout.fav_list, null);
        r_mDialog = new Dialog(EditFavPlaceActivity.this, R.style.dialogwinddow);
        r_mDialog.setContentView(r_view);
        r_mDialog.setCancelable(true);
        ivAddTofavPlaced.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


                r_mDialog.show();
                lay_fav_res1 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res1);
                lay_fav_res2 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res2);
                lay_fav_res3 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res3);
                lay_fav_res4 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res4);

                other_details = (LinearLayout) r_mDialog.findViewById(R.id.other_details);
                other_details.setVisibility(View.GONE);
                et_others = (EditText) r_mDialog.findViewById(R.id.et_others);
                ok_others = (TextView) r_mDialog.findViewById(R.id.ok_others);
                lay_fav_res1.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        placeType = "1";
                        editPassFavPlace();
                        r_mDialog.dismiss();
                    }
                });
                lay_fav_res2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        placeType = "2";
                        editPassFavPlace();
                        r_mDialog.dismiss();
                    }
                });
                lay_fav_res3.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        placeType = "3";
                        editPassFavPlace();
                        r_mDialog.dismiss();
                    }
                });

                lay_fav_res4.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View view)
                    {
                        other_details.setVisibility(View.VISIBLE);
                        ok_others.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                placeType=et_others.getText().toString();
                                editPassFavPlace();
                            }
                        });

                    }
                });



           }


    });

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
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
        mMap.moveCamera(center);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15));


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

        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener()
        {
            @Override
            public void onCameraMove()
            {


            }
        });
        googleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener()
        {
            @Override
            public void onCameraChange(final CameraPosition cameraPosition)
            {


                camPos = cameraPosition;
                GetAddress(String.valueOf(cameraPosition.target.latitude), String.valueOf(cameraPosition.target.longitude));

            }
        });

        googleMap.setMyLocationEnabled(true);

    }

    @Override
    public void onLocationChanged(Location location) {


//        if (mCurrLocationMarker != null) mCurrLocationMarker.remove();
//
//        mMap.setMyLocationEnabled(true);
//        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
//        mMap.moveCamera(center);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15));

    }

    public  void getTripDetial(String tripId)
    {
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("TripId", tripId);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "GetTripDetails", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + "USER DATA", response.toString());


                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.SUCCSESS))
                            {
                                //success
                                TripDitalModel tripDitalModel = new Gson().fromJson(response.toString(), TripDitalModel.class);

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
     * get address from  Lat and lng using google map api using
     *
     * @param lat,lng
     */
    private void GetAddress(String lat, String lng)
    {
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + lat + "," + lng + "&sensor=true", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {


                List<Address> res = new ArrayList<Address>();
                try
                {
                    Double lon = new Double(0);
                    Double lat = new Double(0);
                    String name = "";
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = (JSONArray) jsonObject.get("results");
                    lon = array.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                    lat = array.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    name = array.getJSONObject(0).getString("formatted_address");
                    Log.e("FOR MATED ADDRES IS ==", name);
                    Address addr = new Address(Locale.getDefault());

                    if (tvCurrentlocation.isFocusable())
                    {
                        tvCurrentlocation.setText(name);
                        picPlace=name;
                        //tvChooseLocationType.setVisibility(View.GONE);
                    }
                    else if(edDropPlace.isFocusable())
                    {
                        edDropPlace.setText(name);
                        dropPlace=name;
                        //tvChooseLocationType.setVisibility(View.GONE);
                    }else {
                        tvCurrentlocation.setText(name);
                    }


//                    for (int i = 0; i < array.length(); i++)
//                    {
//                        Double lon = new Double(0);
//                        Double lat = new Double(0);
//                        String name = "";
//                        try
//                        {
//                            lon = array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
//
//                            lat = array.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
//                            name = array.getJSONObject(i).getString("formatted_address");
//                            Log.e("FOR MATED ADDRES IS ==",name);
//                            Address addr = new Address(Locale.getDefault());
//                            addr.setLatitude(lat);
//                            addr.setLongitude(lon);
//                            addr.setAddressLine(0, name != null ? name , "");
//
//                            res.add(addr);
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//
//                        }
//
//
//                    }

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


            }
        });
        // Adding request to request queue
        int socketTimeout = 50000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable
    {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId)
        {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount()
        {
            return resultList.size();
        }

        @Override
        public String getItem(int index)
        {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter()
        {
            Filter filter = new Filter()
            {
                @Override
                protected FilterResults performFiltering(CharSequence constraint)
                {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null)
                    {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());


                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results)
                {
                    if (results != null && results.count > 0)
                    {
                        notifyDataSetChanged();
                    }
                    else
                    {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }


    }

    /**
     * edit place from fav
     *
     * @param
     */
    public void editPassFavPlace()
    {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(EditFavPlaceActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(EditFavPlaceActivity.this.getResources().getString(R.string.LOADING));
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("FavouriteId",favplaceId);
        postParam.put("PassengerId",passId);
        postParam.put("PickupPlace",picPlace);
        postParam.put("DropPlace",dropPlace);
        postParam.put("PickupLatitude", pLat);
        postParam.put("PickLongitude", pLng);
        postParam.put("DropLatitude", dLat);
        postParam.put("DropLongitude", dLng);
        postParam.put("Comments",comment);
        postParam.put("LocationType",placeType);
        postParam.put("Notes",notes);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "EditFavourite", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {


                    @Override
                    public void onResponse(JSONObject response)
                    {

                        progressDialog.dismiss();
                        try
                        {

                            //   76
                            String Flag = response.getString("Flag");
                            if (Flag.equals(Constants.SUCCSESS))
                            {
                                Toasty.success(EditFavPlaceActivity.this,getResources().getString(R.string.Placesaved), Toast.LENGTH_LONG,true).show();
                                ivAddTofavPlaced.setImageDrawable(EditFavPlaceActivity.this.getResources().getDrawable(R.drawable.stared));
                            }
                            else if (Flag.equals(Constants.INVALID_REQUEST))
                            {
                                // invalid request
                                Toasty.error(EditFavPlaceActivity.this,getResources().getString(R.string.thereisanerror), Toast.LENGTH_LONG,true).show();

                            }
                            else if (Flag.equals(Constants.FAV_PLACE_EXIST))
                            {
                                // place exist
                                Toasty.info(EditFavPlaceActivity.this,getResources().getString(R.string.Favouriteplacealreadyexists), Toast.LENGTH_LONG,true).show();

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
                Log.d(TAG, "Error" + error.getMessage());
                progressDialog.dismiss();
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

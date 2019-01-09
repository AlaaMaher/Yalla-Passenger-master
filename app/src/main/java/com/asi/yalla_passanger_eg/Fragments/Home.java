package com.asi.yalla_passanger_eg.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.asi.yalla_passanger_eg.Adapters.CarModelsAdapter;
import com.asi.yalla_passanger_eg.AppController;
import com.asi.yalla_passanger_eg.Constants;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.MAP.DownloadTask;
import com.asi.yalla_passanger_eg.Models.CarModelsModel;
import com.asi.yalla_passanger_eg.Models.NearestCarsModel;
import com.asi.yalla_passanger_eg.R;
import com.asi.yalla_passanger_eg.RequestCarActivity;
import com.asi.yalla_passanger_eg.Util.itemclickforRecycler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, AdapterView.OnItemClickListener {
    private View view;
    ImageView ivMini, ivMax, ivBus, ivDele;
    TextView tvMini, tvMax, tvBus, tvDele;
    LinearLayout linear_mini, linear_max, linear_bus, linear_dele, lineartaxitype;
    // LogCat tag
    private static final String TAG = "Home=======>";

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 1; // 10 meters

    MapView mMapView;
    private GoogleMap googleMap;
    ArrayList<CarModelsModel> carModelsModels = new ArrayList<>();


    double lat;
    double longee;

    double droplat;
    double droplng;
    double piclat;
    double piclang;
    String picPlaceName, dropPlacename;
    CardView cardFromTo;


    ImageView ivAddTofavPlaced;
    private static final String LOG_TAG = "Google";
    private static final String PLACES_API_BASE = "https,//maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyAyKWcogS2vgE52G5ZBj9IXtgqQ7n3cP5A";
    private TextView tvCurrentlocation, tvChooseLocationType;
    Button bookNowBtn, bookLaterBtn;
    private Dialog dt_mDialog;
    private DatePicker _datePicker;
    private TimePicker _timePicker;
    private AutoCompleteTextView edDropPlace;
    private ArrayList<NearestCarsModel> addressArrayList = new ArrayList<>();

    String car_model_type = "1"; // to hold the type of car selected
    private CameraPosition camPos;
    private CarModelsModel ModelsData;
    RecyclerView rv_Car_Models;
    CarModelsAdapter carModelsAdapter;
    private NearestCarsModel nearestCarsModel;
    RotateLoading rotateloading;
    private Dialog r_mDialog;
    private EditText et_others, etPromoCode;
    private TextView ok_others;
    private String placeType;
    LinearLayout lay_fav_res1, lay_fav_res2, lay_fav_res3, lay_fav_res4, other_details;
    private String time;
    private String approxDistanceValueInMeter;
    private double expectedTripCost;
    private String cityName;
    private double newDisInKm;
    private String approxDistance;
    private LatLng mPosition;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        lineartaxitype = (LinearLayout) view.findViewById(R.id.taxitype);
        ivAddTofavPlaced = (ImageView) view.findViewById(R.id.ivAddTofavPlaced);
        etPromoCode = (EditText) view.findViewById(R.id.etPromoCode);
        bookNowBtn = (Button) view.findViewById(R.id.bookNowBtn);
        bookLaterBtn = (Button) view.findViewById(R.id.bookLaterBtn);
        bookLaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View r_view = View.inflate(getActivity(), R.layout.date_time_picker_dialog, null);
                dt_mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
                dt_mDialog.setContentView(r_view);
                dt_mDialog.setCancelable(true);
                dt_mDialog.show();
                _datePicker = (DatePicker) dt_mDialog.findViewById(R.id.datePicker1);
                _timePicker = (TimePicker) dt_mDialog.findViewById(R.id.timePicker1);
                Calendar cal = Calendar.getInstance();
                _timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY) + 1);
                _timePicker.setCurrentMinute(cal.get(Calendar.MINUTE) + 1);


            }
        });


        cardFromTo = (CardView) view.findViewById(R.id.cardFromTo);
        tvCurrentlocation = (TextView) view.findViewById(R.id.autocurrentPlace);
        tvChooseLocationType = (TextView) view.findViewById(R.id.tvChooseLocationType);
        tvChooseLocationType.setVisibility(View.GONE);
        edDropPlace = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        edDropPlace.setFocusable(false);
        edDropPlace.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item_location));
        edDropPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edDropPlace.setFocusable(true);
                tvCurrentlocation.setFocusable(false);
                tvChooseLocationType.setVisibility(View.VISIBLE);
                tvChooseLocationType.setText(getActivity().getResources().getString(R.string.chooseDropLocation));
                // droplat = camPos.target.latitude;
                //droplng = camPos.target.longitude;


            }
        });
        tvCurrentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edDropPlace.setFocusable(false);
                tvCurrentlocation.setFocusable(true);
                tvChooseLocationType.setVisibility(View.VISIBLE);
                tvChooseLocationType.setText(getActivity().getResources().getString(R.string.choosepickLocation));
                //piclat = camPos.target.latitude;
                // piclang = camPos.target.longitude;

            }
        });
        edDropPlace.setOnItemClickListener(this);


        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
            createLocationRequest();
        }
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO, Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }


                googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
                    @Override
                    public void onCameraMove() {
                        //bookNowBtn.setVisibility(View.GONE);
                        bookNowBtn.setBackground(new ColorDrawable(Color.TRANSPARENT));
                        bookNowBtn.setTextColor(Color.TRANSPARENT);

                        bookLaterBtn.setBackground(new ColorDrawable(Color.TRANSPARENT));
                        bookLaterBtn.setTextColor(Color.TRANSPARENT);

                        cardFromTo.setVisibility(View.GONE);
                        lineartaxitype.setVisibility(View.GONE);


                    }
                });
                googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                    @Override
                    public void onCameraIdle() {

                        mPosition = googleMap.getCameraPosition().target;

                        GetAddress(mPosition.latitude, mPosition.longitude);
                        bookNowBtn.setBackground(getResources().getDrawable(R.drawable.round_back_h));
                        bookNowBtn.setTextColor(Color.WHITE);
                        bookLaterBtn.setBackground(getResources().getDrawable(R.drawable.round_back_hr));
                        bookLaterBtn.setTextColor(Color.WHITE);
                        cardFromTo.setVisibility(View.VISIBLE);
                        lineartaxitype.setVisibility(View.VISIBLE);
                    }
                });
                googleMap.setMyLocationEnabled(true);
            }
        });

        bookNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cityName = Constants.getCityName(getActivity(), lat, longee);
                    }
                });
                //Log.e("CITY IS --->",cityName);
                if (edDropPlace.getText().toString().equals("")) {
                    edDropPlace.setError("please select destination");
                } else if (tvCurrentlocation.getText().toString().equals("")) {
                    tvCurrentlocation.setError("please select destination");
                } else {
                    if (edDropPlace.getText().toString().equals("")) {
                        Intent intent = new Intent(getActivity(), RequestCarActivity.class);
                        intent.putExtra("Latitude", String.valueOf(lat));
                        intent.putExtra("Longitude", String.valueOf(longee));
                        intent.putExtra("PickupPlace", tvCurrentlocation.getText().toString());
                        intent.putExtra("DropPlace", edDropPlace.getText().toString());
                        intent.putExtra("DropLatitude", "");
                        intent.putExtra("DropLongitude", "");
                        intent.putExtra("PickUpTime", "12:00 am");
                        intent.putExtra("CityName", cityName);
                        Log.e("CITY IS --->", cityName);
                        intent.putExtra("ModelId", car_model_type);
                        intent.putExtra("DistanceAway", "10");
                        intent.putExtra("PassengerId", new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                        intent.putExtra("RequestType", "1");
                        intent.putExtra("PromoCode", etPromoCode.getText().toString());
                        intent.putExtra("NowAfter", "0");     //→ 0=Now, 1=Future
                        intent.putExtra("Notes", "");
                        intent.putExtra("RoundTrip", "0");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {

                        double toCoast = expectedTripCost + expectedTripCost * .2;
                        double fromCoast = expectedTripCost - expectedTripCost * .2;


                        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText(getActivity().getResources().getString(R.string.expectedtype));
                        pDialog.setContentText(getActivity().getResources().getString(R.string.expectedtimeis) + " " + time + " " + approxDistance + "\n" + "trip cost :" + "\n" + "from : " + " " + String.valueOf(Constants.round(fromCoast, 2)) + " " + "SAR" + "\n" + "to : " + String.valueOf(Constants.round(toCoast, 2)) + " " + "SAR");
                        pDialog.setCancelText(getActivity().getResources().getString(R.string.dialog_cancel));
                        pDialog.setCancelable(false);
                        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Intent intent = new Intent(getActivity(), RequestCarActivity.class);
                                intent.putExtra("Latitude", String.valueOf(lat));
                                intent.putExtra("Longitude", String.valueOf(longee));
//                            intent.putExtra("PickupPlace", Constants.getCompleteAddressString(getActivity(), lat, longee));
                                intent.putExtra("PickupPlace", tvCurrentlocation.getText().toString());
                                intent.putExtra("DropPlace", edDropPlace.getText().toString());
                                intent.putExtra("DropLatitude", String.valueOf(droplat));
                                intent.putExtra("DropLongitude", String.valueOf(droplng));
                                intent.putExtra("PickUpTime", "12:00 am");
                                intent.putExtra("CityName", cityName);
                                intent.putExtra("ModelId", car_model_type);
                                intent.putExtra("DistanceAway", "10");
                                intent.putExtra("PassengerId", new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                                intent.putExtra("RequestType", "1");
                                if (etPromoCode.getText().toString().isEmpty()) {
                                    intent.putExtra("PromoCode", "");
                                } else {
                                    intent.putExtra("PromoCode", etPromoCode.getText().toString());
                                }

                                intent.putExtra("NowAfter", "0");     //→ 0=Now, 1=Future
                                intent.putExtra("Notes", "");
                                intent.putExtra("RoundTrip", "0");
                                startActivity(intent);
                                pDialog.dismiss();
                            }
                        });
                        pDialog.show();
                    }
                }


            }
        });
        rotateloading = (RotateLoading) view.findViewById(R.id.rotateloading);
        //============= view of the fragment =============
        ivMini = (ImageView) view.findViewById(R.id.iv_mini);
        tvMini = (TextView) view.findViewById(R.id.tv_mini);
        linear_mini = (LinearLayout) view.findViewById(R.id.linear_mini);

        ivMax = (ImageView) view.findViewById(R.id.iv_max);
        tvMax = (TextView) view.findViewById(R.id.tv_max);
        linear_max = (LinearLayout) view.findViewById(R.id.linear_max);

        ivBus = (ImageView) view.findViewById(R.id.iv_bus);
        tvBus = (TextView) view.findViewById(R.id.tv_bus);
        linear_bus = (LinearLayout) view.findViewById(R.id.linear_bus);

        ivDele = (ImageView) view.findViewById(R.id.iv_dele);
        tvDele = (TextView) view.findViewById(R.id.tv_dele);
        linear_dele = (LinearLayout) view.findViewById(R.id.linear_dele);
        setSelectedType(tvMini, ivMini);


        linear_mini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedType(tvMini, ivMini);
                unSelectedType(tvMax, ivMax);
                unSelectedType(tvBus, ivBus);
                unSelectedType(tvDele, ivDele);
                car_model_type = "1";
                getNearestDriversList(car_model_type);

            }
        });


        linear_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedType(tvMax, ivMax);
                unSelectedType(tvMini, ivMini);
                unSelectedType(tvBus, ivBus);
                unSelectedType(tvDele, ivDele);
                car_model_type = "2";
                getNearestDriversList(car_model_type);
            }
        });

        linear_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedType(tvBus, ivBus);
                unSelectedType(tvMini, ivMini);
                unSelectedType(tvMax, ivMax);
                unSelectedType(tvDele, ivDele);
                car_model_type = "3";
                getNearestDriversList(car_model_type);
            }
        });

        linear_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectedType(tvBus, ivBus);
                unSelectedType(tvMini, ivMini);
                unSelectedType(tvMax, ivMax);
                setSelectedType(tvDele, ivDele);
                car_model_type = "4";
                getNearestDriversList(car_model_type);
            }
        });


        setUpRecycelerView(view);
        JSONArray array = null;
        try {
            array = new JSONArray(Constants.getCarModels(getActivity()));
            // Loop through the array elements
            for (int i = 0; i < array.length(); i++) {
                // Get current json object
                JSONObject modelObject = array.getJSONObject(i);
                ModelsData = new Gson().fromJson(modelObject.toString(), CarModelsModel.class);
                carModelsModels.add(ModelsData);
            }

            carModelsAdapter = new CarModelsAdapter(getActivity(), carModelsModels);
            rv_Car_Models.setAdapter(carModelsAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvMini.setText(carModelsModels.get(0).getModelName());
        tvMax.setText(carModelsModels.get(1).getModelName());
        tvBus.setText(carModelsModels.get(2).getModelName());
        tvDele.setText(carModelsModels.get(3).getModelName());

        //============ pop up dialog ==============
        final View r_view = View.inflate(getActivity(), R.layout.fav_list, null);
        r_mDialog = new Dialog(getActivity(), R.style.dialogwinddow);
        r_mDialog.setContentView(r_view);
        r_mDialog.setCancelable(true);
        //==============  add place to fav ============
        ivAddTofavPlaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r_mDialog.show();
                lay_fav_res1 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res1);
                lay_fav_res2 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res2);
                lay_fav_res3 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res3);
                lay_fav_res4 = (LinearLayout) r_mDialog.findViewById(R.id.lay_fav_res4);

                other_details = (LinearLayout) r_mDialog.findViewById(R.id.other_details);
                other_details.setVisibility(View.GONE);
                et_others = (EditText) r_mDialog.findViewById(R.id.et_others);
                ok_others = (TextView) r_mDialog.findViewById(R.id.ok_others);
                lay_fav_res1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        placeType = "1";
                        addPlaceToFav();
                        r_mDialog.dismiss();
                    }
                });
                lay_fav_res2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        placeType = "2";
                        addPlaceToFav();
                        r_mDialog.dismiss();
                    }
                });
                lay_fav_res3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        placeType = "3";
                        addPlaceToFav();
                        r_mDialog.dismiss();
                    }
                });

                lay_fav_res4.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        other_details.setVisibility(View.VISIBLE);
                        ok_others.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                placeType = et_others.getText().toString();
                                addPlaceToFav();
                                r_mDialog.dismiss();
                            }
                        });

                    }
                });
            }
        });
        carModelsAdapter.setSelected(0);
        return view;
    }


    public void setUpRecycelerView(final View view) {
        rv_Car_Models = (RecyclerView) view.findViewById(R.id.rv_Car_Models);
        rv_Car_Models.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        itemclickforRecycler.addTo(rv_Car_Models).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Log.e("POS----->", String.valueOf(position));
                addressArrayList.clear();
                getNearestDriversList(carModelsModels.get(position).getModelId());
                carModelsAdapter.setSelected(position);
                if (!edDropPlace.getText().toString().equals("") && carModelsModels != null && carModelsModels.size() > 0) {
                    // TODO: 31/03/2018 error null pointer exeption 
                    expectedTripCost = ApproximateCost(Integer.parseInt(carModelsModels.get(position).getModelId()));
                }

            }
        });


    }


    public void setSelectedType(TextView textView, ImageView imageView) {
        textView.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    }

    public void unSelectedType(TextView textView, ImageView imageView) {
        textView.setTextColor(getActivity().getResources().getColor(R.color.gray));
        imageView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.gray));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    /**
     * Method to display the location on UI
     */
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO, Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            lat = latitude;
            longee = longitude;
            // For dropping a marker at a point on the Map
            LatLng sydney = new LatLng(lat, longee);

            googleMap.addMarker(new MarkerOptions().
                    position(sydney).title("Marker Title").
                    snippet("Marker Description")).setIcon(BitmapDescriptorFactory.
                    fromResource(R.drawable.ic_currentlocation));
            lat = latitude;
            longee = longitude;
            // For dropping a marker at a point on the Map
            LatLng loc1 = new LatLng(lat + .0412, longee + .112);
            LatLng loc2 = new LatLng(lat + .0114, longee + .2121);
            LatLng loc3 = new LatLng(lat + .0115, longee + .114);
            LatLng loc4 = new LatLng(lat + .0134, longee + .1410);


            //For zooming automatically to the location of the marker
            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            // ======= get model 1 cars ==========
            getNearestDriversList("1");

        } else {

            Toast.makeText(getActivity(), "(Couldn't get the location. Make sure location is enabled on the device)", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Creating google api client object
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Method to verify google play services on the device
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

    /**
     * Starting the location updates
     */
    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO, Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    mGoogleApiClient, this);
        }

    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed, ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {


        // Once connected with google api, get the location
        displayLocation();

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(getActivity(), "Location changed!",
                Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
        displayLocation();


    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        googleMap.clear();
        String str = (String) adapterView.getItemAtPosition(position);
        Constants constants = new Constants();
        LatLng place = constants.getLatLongFromPlace(getActivity(), str);
        googleMap.addMarker(new MarkerOptions().position(place).title("").snippet("Marker Description")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_currentlocation));
        CameraPosition cameraPosition = new CameraPosition.Builder().target(place).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        /**
         * Draw line between my current location and the drop location
         * */
        LatLng org = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        String url = getDirectionsUrl(org, place);
        DownloadTask downloadTask = new DownloadTask(googleMap, getActivity());
        downloadTask.execute(url);
        double latitude = mLastLocation.getLatitude();
        double longitude = mLastLocation.getLongitude();

        lat = latitude;
        longee = longitude;
        // For dropping a marker at a point on the Map
        LatLng sydney = new LatLng(lat, longee);

        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description")).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_currentlocation


        ));


    }

    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country,eg");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL, " + url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());


                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }


    }


    /**
     * get Road
     */
    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        String url = "https,//maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }


    /**
     * get address from  Lat and lng using google map api using
     *
     * @param lat,lng
     */
    private void GetAddress(double lat, final double lng) {

        String lang = Constants.getChangeLang(getActivity());
        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(lat) + "," + String.valueOf(lng) + "&sensor=true&language=" + lang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                List<Address> res = new ArrayList<Address>();
                try {
                    double lon;
                    double lat;
                    String name = "";
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = (JSONArray) jsonObject.get("results");
                    lon = array.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

                    lat = array.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    name = array.getJSONObject(0).getString("formatted_address");
                    Log.e("FOR MATED ADDRES IS ==", name);
                    Address addr = new Address(Locale.getDefault());
                    if (tvCurrentlocation.isFocusable()) {
                        tvCurrentlocation.setText(name);
                        tvChooseLocationType.setVisibility(View.GONE);
                        piclat = lat;
                        piclang = lon;

                    } else if (edDropPlace.isFocusable()) {
                        edDropPlace.setText(name);
                        tvChooseLocationType.setVisibility(View.GONE);
                        droplat = lat;
                        droplng = lon;
                    } else if (tvCurrentlocation.isCursorVisible()) {
                        tvCurrentlocation.setText(name);
                        piclat = lat;
                        piclang = lon;
                    }
                    GetTimeAndDistance();
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

                } catch (JSONException e) {
                    e.printStackTrace();

                }

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


    // ============= convert address to lng lat and add markers to map ==========
    public void addMarkersToMap() {
        googleMap.clear();

        if (addressArrayList != null && addressArrayList.size() > 0) {
            for (int i = 0; i < addressArrayList.size(); i++) {
                try {


                    System.out.println("latitude = " + addressArrayList.get(i).getLatitude() + " longitude = " + addressArrayList.get(i).getLongitude());
                    googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(addressArrayList.get(i).getLatitude()), Double.parseDouble(addressArrayList.get(i).getLongitude())))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_taxi))
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                } // end catch
            }
        }

    } //end addMarkersToMap


    /**
     * get the nearest car using
     *
     * @param car_model_type
     */
    public void getNearestDriversList(final String car_model_type) {
        rotateloading.start();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + "NearestDriversList", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //here is the response of server

                rotateloading.stop();
                Log.e("getNearestDriversList", response);

                JSONArray array = null;
                try {
                    JSONObject object = new JSONObject(response);
                    String flag = object.getString("Flag");
                    if (flag.equals(Constants.SUCCSESS)) {
                        array = object.getJSONArray("DriverDetails");
                        for (int n = 0; n < array.length(); n++) {

                            JSONObject nearestCarobject = array.getJSONObject(n);
                            nearestCarsModel = new Gson().fromJson(nearestCarobject.toString(), NearestCarsModel.class);
                            addressArrayList.add(nearestCarsModel);
                            addMarkersToMap();
//
                        }
                    } else if (flag.equals(Constants.NO_VIC_AVILABLE)) {
                        Toasty.info(getActivity(), getResources().getString(R.string.nocaravalible), Toast.LENGTH_LONG, true).show();
                        addressArrayList.clear();
                        addMarkersToMap();
                    } else if (flag.equals(Constants.INVALID_REQUEST)) {

                        Toast.makeText(getActivity(), "invalid request", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (array == null) {
                    // linear_no_new_notifications.setVisibility(View.VISIBLE);
                } else {
//                    for (int n = 0; n < array.length(); n++)
//                    {
//                        try
//                        {
//                            JSONObject object = array.getJSONObject(n);
//                            String msgId = object.getString("msgId");
//                            String message_title = object.getString("message_title");
//                            String message_details = object.getString("message_details");
//                            String sent_date=object.getString("sent_date");
//                            notificationModels.add(new notificationModel(msgId, message_title, message_details,sent_date));
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//                        // do some stuff....
//                    }
//                    notificationAdapter = new notificationAdapter(getActivity(), notificationModels);
//                    rvNotification.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//                    rvNotification.setAdapter(notificationAdapter);
//                    linearLoading = (LinearLayout) view.findViewById(R.id.loadingLayout);
                }
            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                rotateloading.stop();
                Log.d("ErroeVolley", error.getMessage());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();

                params.put("ModelId", car_model_type);
                params.put("Latitude", String.valueOf(lat));
                params.put("Longitude", String.valueOf(longee));
                params.put("PassengerId", new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                Log.e("UID ", new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                return params;
            }

        };
        // Adding request to request queue
        int socketTimeout = 50000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, "tag");
    }


    /**
     * Add place to fav
     *
     * @param
     */
    public void addPlaceToFav() {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(getActivity().getResources().getString(R.string.LOADING));
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
        postParam.put("PickupPlace", tvCurrentlocation.getText().toString());
        postParam.put("DropPlace", edDropPlace.getText().toString());
        postParam.put("PickupLatitude", String.valueOf(piclat));
        postParam.put("PickLongitude", String.valueOf(piclang));
        postParam.put("DropLatitude", String.valueOf(droplat));
        postParam.put("DropLongitude", String.valueOf(droplng));
        postParam.put("Comments", "ss");
        postParam.put("LocationType", placeType);
        postParam.put("Notes", "ss");
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "AddFavourite", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();
                        try {

                            //   76
                            String Flag = response.getString("Flag");
                            if (Flag.equals(Constants.SUCCSESS)) {
                                Toasty.success(getActivity(), getResources().getString(R.string.Placesaved), Toast.LENGTH_LONG, true).show();
                                ivAddTofavPlaced.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.stared));
                            } else if (Flag.equals(Constants.INVALID_REQUEST)) {
                                // invalid request
                                Toasty.error(getActivity(), getResources().getString(R.string.thereisanerror), Toast.LENGTH_LONG, true).show();

                            } else if (Flag.equals(Constants.FAV_PLACE_EXIST)) {
                                // place exist
                                Toasty.info(getActivity(), getResources().getString(R.string.Favouriteplacealreadyexists), Toast.LENGTH_LONG, true).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error" + error.getMessage());
                progressDialog.dismiss();
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
     * get distance and
     */
    private void GetTimeAndDistance() {

        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + piclat + "," + piclang + "&destinations=" + droplat + "," + droplng + "&language=en&key=AIzaSyCffGbuScXeTbN5mUxA3X1YS-DzZVujAq8";
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

                    jsonRespRouteDistance = new JSONObject(response)
                            .getJSONArray("rows")
                            .getJSONObject(0)
                            .getJSONArray("elements")
                            .getJSONObject(0)
                            .getJSONObject("distance");

                    time = jsonRespRouteTime.get("text").toString();


                    approxDistance = jsonRespRouteDistance.get("text").toString();
                    approxDistanceValueInMeter = jsonRespRouteDistance.get("value").toString();


                    if (!edDropPlace.getText().toString().equals("")) {
                        expectedTripCost = ApproximateCost(1);
                    }

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


    public double ApproximateCost(int modelId) {
        modelId = modelId - 1;
        if (carModelsModels.size() < modelId) {
            return -44;
        }
        double basefare = Double.parseDouble(carModelsModels.get(modelId).getBaseFare());
        double abovekm = Double.parseDouble(carModelsModels.get(modelId).getAboveKmRange());

// TODO: 31/03/2018 null pointer exception
        newDisInKm = Double.parseDouble(approxDistanceValueInMeter) / 1000;
        double tripCoast = basefare + newDisInKm * abovekm;
        if (tripCoast <= Double.parseDouble(carModelsModels.get(modelId).getMinFare())) {
            tripCoast = Double.parseDouble(carModelsModels.get(modelId).getMinFare());
            Log.e("Trip Coast---->", String.valueOf(tripCoast) + " // MIN --> " + carModelsModels.get(modelId).getMinFare());
        }
        return tripCoast;

    }
}






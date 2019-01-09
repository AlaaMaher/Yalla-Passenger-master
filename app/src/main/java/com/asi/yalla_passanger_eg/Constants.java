package com.asi.yalla_passanger_eg;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.asi.yalla_passanger_eg.MAP.fetchLatLongFromService;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;
import static com.asi.yalla_passanger_eg.LoginActivity.BUTTON_DELAY;
import static com.asi.yalla_passanger_eg.LoginActivity.EDITTEXT_DELAY;
import static com.asi.yalla_passanger_eg.LoginActivity.VIEW_DELAY;

/**
 * Created by ASI on 4/5/2017.
 */

 public class Constants {

    public static final String TAG = "YALLA_PASS_TAG" ;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String VOUCHER_NUMBER_NOT_FOUND ="85";
    public static final String VOUCHER_EXPIRED ="84";
    public static final String VOUCHER_ALREADY_USED = "83";
    public static final String VOUCHER_BLOCKED = "82";
    /*
    * registeration Constants Api replies*/
    public static String EMAIL_EXIST="52";
    public static String PHONE_EXIST="53";
    public static String REFFERAL_CODE_NOT_EXIST="54";
    public static String USER_CREATED="55";




/*
*  constants Api replies*/
    public static final String SUCCSESS="0";
    public static final String INVALID_REQUEST="1";
    public static final String INVALID_USER="2";
    public static final String PASSWORD_CHANGED="3";
    public static final String OLD_PASS_INCORRECT="4";
    public static final String PHONE_NOT_EXISTS="5";
    public static final String PASSWORD_FAILED="6";
    public static final String ACCOUNT_DEACTIVTE="7";
    public static final String ALREADY_LOGIN="8";
    public static final String USER_BLOCKED="9";




    public static final String DRIVER_NOT_LOGIN="10";
    public static final String DRIVER_IN_TRIP="13";
    public static final String DRIVER_SHIFT="15";
    public static final String DRIVER_SHIFT_OUT="16";
    public static final String TAXI_NOT_ASSIGNED="18";
    public static final String REQUEST_REJECTED_BY_DRIVER="21";
    public static final String DRIVER_REPLY_TIMEOUT="22";
    public static final String INVALID_TRIP="25";
    public static final String DRIVER_ACCEPTED_THE_TRIP_REQUEST="26";
    public static final String TRIP_ALREADY_CONFIRMED="27";
    public static final String MESSAGE_SEND_WITH_New_PASS="48";
    public static final String AMOUNT_ADD_SUCESSFULLY="51";
    public static final String INSUFFICIENT_WALLET_AMOUNT="40";
    public static String NO_VIC_AVILABLE="61";
    public static String FOR_BOOK_LATER="62";
    public static String ALL_DRIVERS_BUSY="65";
    public static String REQUEST_ACCEPTED_BY_DRIVER="63";
    public static String REQUEST_REJECTED_BY_DRIVER_PASS_FLAG="64";
    public static String LOGOUT_SUCCESS="14";
    public static String INVALID_VERVICATION_CODE="78";
    public static String FAV_PLACE_EXIST="76";
    public static String NO_TRIPO_FOUND="79";
    public static String NO_FAV_PLACE="77";
    public static String REQUESTING="80";
    public static String PASSENGER_IN_TRIP="43";
    public static String PASSENGER_CANCEL_THE_TRIP="37";
    public static String YOUR_BOOKING_CONFIRMED="66";
    public static String YOUR_TAXI_HAS_BEEN_ARRIVED="69";
    public static String YOUR_TRIP_HAS_BEEN_STARTED="70";
    public static String YOUR_TRIP_HAS_BEEN_COMPLETED="71";
    public static String TRIP_FARE_UPDATED="72";
    public static String PASSENGER_CANCEL_THE_TRIP_="73";
    public static String Driver_CANCEL_THE_TRIP="74";
    public static String TRIP_NOT_YET_STARTED="75";
    public static String TRIP_ALREADY_CANCELLED="67";
    public static String YOUR_REQUEST_HAS_BEEN_CANCELLED_BY_DRIVER_PLEASE_TRY_BOOKING_AGAIN="68";
    public static String RATE_UPADTED="41";
    public static String NO_PENDING_TRIP="45";
    public static String MONEY_ADDED_TO_WALLET="51";
    public static String INVALID_PROMOCODE="56";
    public static String PROMOCODE_NOT_STARTED_YET="57";
    public static String YOUR_PROMOCODE_IS_EXPIRED="58";
    public static String PROMOCODE_LIMIT_EXCEED="59";
    public static String INVALID_PROMOCODE_CITY="81";
    private Address location;
    private double lat;
    private double lon;
    private LatLng returnedlatLong;

    //public final static String BASE_URL= "http://5.42.254.19/YallaWebSaudi/api/yallaservice/";
    public final static String BASE_URL= "http://197.161.95.169:81/YallaWebSaudi/api/yallaservice/";

    public  static  void ChangeLang(String value, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
        editor.putString("lang", value);
        editor.apply();
    }
    public  static  String getChangeLang( Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return prefs.getString("lang", "");
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public  LatLng getLatLongFromPlace(final Context context, final String place) {
        Thread thread = new Thread() {
            @Override
            public void run() {
        try {
            Geocoder selected_place_geocoder = new Geocoder(context);
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(place, 5);

            if (address == null) {

            } else {
                location = address.get(0);
                lat= location.getLatitude();
                lon=location.getLongitude();
                 returnedlatLong = new LatLng(lat,lon);

            }

        } catch (Exception e) {
            e.printStackTrace();
            fetchLatLongFromService fetch_latlng_from_service_abc = new fetchLatLongFromService(
                    place.replaceAll("\\s+", ""));
            fetch_latlng_from_service_abc.execute();

        }
            }
        };
        thread.start();
        return  returnedlatLong;
    }



    /**
     * This is method for convert the string value into MD5
     */
    public static String convertPassMd5(String pass) {

        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        }
        catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }

    /**
     * this method return address from latlang
     * */
    public static String getCompleteAddressString(Context context,double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }
                strAdd = strReturnedAddress.toString();
                Log.e("Addrtess --->" ,strReturnedAddress.toString());
            } else {
                // Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
             Log.e("My loction address", "Canont get Address!");
        }
        return strAdd;
    }

    public  static  void saveCarModels(String value, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
        editor.putString("modelsData", value);
        editor.apply();
    }
    public  static  String getCarModels( Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return prefs.getString("modelsData", "");
    }

    public  static  void saveUserProfileData(String value, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
        editor.putString("user", value);
        editor.apply();
    }
    public  static  String getUserProfileData( Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return prefs.getString("user", "");
    }

    public  static  void SaveTrip(String value, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
        editor.putString("trip", value);
        editor.apply();
    }
    public  static  String getTrip( Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return prefs.getString("trip", "");
    }


    /**
     * in order to save the new trip received
     * */
    public  static  void SaveNewTrip(String value, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
        editor.putString("newTrip", value);
        editor.apply();
    }
    public  static  String getNewTrip( Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return prefs.getString("newTrip", "");
    }

    /**
     * save pending trip for this user
     * @param value,context
     * */
    public  static  void SavePendingTrip(String value, Context context)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyPREFERENCES,MODE_PRIVATE).edit();
        editor.putString("pendingTrip", value);
        editor.apply();
    }
    public  static  String getPendingTrip( Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        return prefs.getString("pendingTrip", "");
    }

    /**
     * get city name
     * */
    public  static String  getCityName(Context context,double lat,double lng)
    {
        Geocoder geocoder = new Geocoder(context, Locale.ENGLISH);
        List<Address> addresses = null;
        String cityName = null;
        try
        {
            addresses = geocoder.getFromLocation(lat,lng, 1);
             cityName = addresses.get(0).getAdminArea();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return cityName;
    }


}

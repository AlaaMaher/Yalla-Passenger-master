package com.asi.yalla_passanger_eg;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
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
import com.asi.yalla_passanger_eg.LoingSession.SessionManager;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class SplachScreen extends AwesomeSplash
{


    @Override
    public void initSplash(ConfigSplash configSplash)
    {


            /* you don't have to override every property */
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.white); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(1000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.loooooosogooo); //or any other drawable
        configSplash.setAnimLogoSplashDuration(1000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeInLeft); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Title
        configSplash.setTitleSplash("YALLA-PASSANGER");
        configSplash.setTitleTextColor(R.color.colorPrimary);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(2000);
        configSplash.setAnimTitleTechnique(Techniques.FadeInRight);
        //configSplash.setTitleFont("fonts/myfont.ttf"); //provide string to your font located in assets/fonts/
        Locale locale = new Locale(Constants.getChangeLang(SplachScreen.this));
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);

//        /**
//         * run the service for tracing the location of the driver
//         * */
//        Intent servIntent = new Intent(SplachScreen.this, LocationTrack.class);
//        startService(servIntent);
//
//    }
        changeLang(SplachScreen.this);

    }

    @Override
    public void animationsFinished()
    {
        // =========== if user is login and no pending trips ===================
        if (new SessionManager(SplachScreen.this).isLoggedIn())
        {
            getPendingTrip();
        }
        else
        {
            startActivity(new Intent(SplachScreen.this,LoginSignup.class));
            finish();
        }

    }

    /**
     * Change the app lang
     * @param context
     */
    public void changeLang(Context context)
    {
        if (Constants.getChangeLang(context).equals(""))
        {

            Constants.ChangeLang("ar", context);
            String languageToLoad = Constants.getChangeLang(SplachScreen.this);
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }
        else
        {
            /**
             * if you want to update the map lang uncomment this code
             * */
            String languageToLoad = Constants.getChangeLang(SplachScreen.this);
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        getCarModels();
    }

    public void  getCarModels()
{

    StringRequest strReq = new StringRequest(Request.Method.POST,
            Constants.BASE_URL + "GetModelFareDetails", new Response.Listener<String>() {
        @Override
        public void onResponse(String response)
        {
            Constants.saveCarModels(response,SplachScreen.this);
            Log.e("MODEL DATA -->",response);
        }


    }, new Response.ErrorListener()
    {

        @Override
        public void onErrorResponse(VolleyError error)
        {


            Log.d("ErroeVolley", error.getMessage());

        }
    });
    // Adding request to request queue
    int socketTimeout = 50000;//30 seconds - change to what you want
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    strReq.setRetryPolicy(policy);
    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, "tag");




}



    public void  getPendingTrip()
    {

        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId",new SQLiteHandler(SplachScreen.this).getUserDetails().get("uid"));


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "PassengerPendingBooking", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {


                    @Override
                    public void onResponse(JSONObject response)
                    {

                        Log.e("PendingTrip--->",response.toString());
                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.SUCCSESS))
                            {


                            }
                            else if (flag.equals(Constants.INVALID_REQUEST))
                            {

                            }
                            else if (flag.equals(Constants.PASSENGER_IN_TRIP))
                            {
                                Constants.SavePendingTrip(response.toString(),SplachScreen.this);
                                startActivity(new Intent(SplachScreen.this,OnGoingActivity.class));
                                finish();
                            }else if(flag.equals(Constants.NO_PENDING_TRIP))
                            {
                                // =========== if user is login and no pending trips ===================
                                if (new SessionManager(SplachScreen.this).isLoggedIn())
                                {
                                    startActivity(new Intent(SplachScreen.this,MainActivity.class));
                                    finish();
                                }
                                else
                                {
                                    startActivity(new Intent(SplachScreen.this,LoginSignup.class));
                                    finish();
                                }
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
                    Toasty.error(SplachScreen.this,"TimeoutError", Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof AuthFailureError)
                {
                    Toasty.error(SplachScreen.this,"AuthFailureError",Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof ServerError)
                {
                    Toasty.error(SplachScreen.this,"ServerError",Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof NetworkError)
                {
                    Toasty.error(SplachScreen.this,"NetworkError",Toast.LENGTH_LONG,true).show();
                }
                else if (error instanceof ParseError)
                {
                    Toasty.error(SplachScreen.this,"ParseError",Toast.LENGTH_LONG,true).show();
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

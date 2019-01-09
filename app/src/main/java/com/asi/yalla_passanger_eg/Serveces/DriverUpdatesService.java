package com.asi.yalla_passanger_eg.Serveces;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
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
import com.asi.yalla_passanger_eg.MainActivity;
import com.asi.yalla_passanger_eg.OnGoingActivity;
import com.asi.yalla_passanger_eg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

/**
 * Created by m.khalid on 7/12/2017.
 */

public class DriverUpdatesService extends Service
{

    Handler h = new Handler();
    int delay = 10000; //10 seconds
    Runnable runnable;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.e("ON onBind","ON onBind ");
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);
        Log.e("ON STARET","ON START ");

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    /**
     * get the driver Updates by sending the trip id
     * @param  tripId
     * */
    public void getDriverUpdates(final String tripId)
    {
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("TripId", tripId);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "GetDriverUpdate", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {

                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + "DRIVER UPDATE DATA -->", response.toString());


                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.REQUEST_ACCEPTED_BY_DRIVER))
                            {
                                //Request accepted by driver
                                h.removeCallbacks(runnable); //stop handler when activity not visible
                                Toasty.success(getApplicationContext(),getApplicationContext().getResources().getString(R.string.driverAcceptthetrip),Toast.LENGTH_LONG,true).show();
                                Constants.SaveNewTrip(response.toString(),getApplicationContext());
                                startActivity(new Intent(getApplicationContext(), OnGoingActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                PackageManager pm = getApplicationContext().getPackageManager();
                                ComponentName componentName = new ComponentName(getApplicationContext(), DriverUpdatesService.class);
                                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);
                            }
                            else if (flag.equals(Constants.ALL_DRIVERS_BUSY))
                            {

                                h.removeCallbacks(runnable); //stop handler when activity not visible
                                Toasty.info(getApplicationContext(),getApplicationContext().getResources().getString(R.string.AllDriversbusy),Toast.LENGTH_LONG,true).show();
                                PackageManager pm = getApplicationContext().getPackageManager();
                                ComponentName componentName = new ComponentName(getApplicationContext(), DriverUpdatesService.class);
                                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                                //Request rejected by driver
                            }else if (flag.equals(Constants.REQUESTING))
                            {


                                //Request rejected by driver
                            }
                            else if (flag.equals(Constants.INVALID_REQUEST))
                            {
                                //invalid request
                                h.removeCallbacks(runnable);
                                PackageManager pm = getApplicationContext().getPackageManager();
                                ComponentName componentName = new ComponentName(getApplicationContext(), DriverUpdatesService.class);
                                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);
                            }
                            else if (flag.equals(Constants.INVALID_TRIP))
                            {
                               //invalid_trip
                                h.removeCallbacks(runnable); PackageManager pm = getApplicationContext().getPackageManager();
                                ComponentName componentName = new ComponentName(getApplicationContext(), DriverUpdatesService.class);
                                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                                        PackageManager.DONT_KILL_APP);

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate()
    {
        h.postDelayed(new Runnable()
        {
            public void run()
            {
                //do something

                try
                {
                    JSONObject object=new JSONObject(Constants.getTrip(getApplicationContext()));
                    getDriverUpdates(object.getString("TripId"));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
                runnable = this;

                h.postDelayed(runnable, delay);
            }
        }, delay);
    }
}

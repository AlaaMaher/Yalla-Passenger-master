package com.asi.yalla_passanger_eg.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.asi.yalla_passanger_eg.Adapters.tripsHisAdpter;
import com.asi.yalla_passanger_eg.AppController;
import com.asi.yalla_passanger_eg.CompletedTripDetial;
import com.asi.yalla_passanger_eg.Constants;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.Models.PassengerCompletedTripsModel;
import com.asi.yalla_passanger_eg.R;
import com.asi.yalla_passanger_eg.Util.itemclickforRecycler;
import com.google.gson.Gson;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YourTripsFragment extends Fragment {


    private View view;
    RecyclerView rvHistory;
    ArrayList<PassengerCompletedTripsModel> tripshistorymodels=new ArrayList<>();
    tripsHisAdpter tripsHisAdpter;
    RotateLoading rotateloading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_your_trips, container, false);
        ini(view);
        return view;
    }

    private void ini(View view) {
        rvHistory= (RecyclerView) view.findViewById(R.id.rvTripHis);
        rotateloading= (RotateLoading) view.findViewById(R.id.rotateloading);
        getCompletedTrips();
        itemclickforRecycler.addTo(rvHistory).setOnItemClickListener(new itemclickforRecycler.OnItemClickListener()
        {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v)
            {
                Intent intent=new Intent(getActivity(), CompletedTripDetial.class);
                intent.putExtra("tid",tripshistorymodels.get(position).getTripId());
                startActivity(intent);
            }
        });


    }



    /**
     * get passenger Completed trips
     * */
    public void getCompletedTrips()
    {
        rotateloading.start();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + "PassengerCompletedTrips", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //here is the response of server

                rotateloading.stop();
                Log.e("DATA OF FAV IS --->",response);
                JSONArray array = null;
                try
                {
                    JSONObject object = new JSONObject(response);
                    String flag = object.getString("Flag");
                    if (flag.equals(Constants.SUCCSESS))
                    {
                        array = object.getJSONArray("Ptrips");
                        for (int n = 0; n < array.length(); n++)
                        {

                            JSONObject favobj = array.getJSONObject(n);
                            PassengerCompletedTripsModel trip = new Gson().fromJson(favobj.toString(), PassengerCompletedTripsModel.class);
                            tripshistorymodels.add(trip);

                        }

                        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                        tripsHisAdpter=new tripsHisAdpter(getActivity(),tripshistorymodels);
                        rvHistory.setAdapter(tripsHisAdpter);

                    }
                    else if (flag.equals(Constants.NO_TRIPO_FOUND))
                    {
                        Toast.makeText(getActivity(), "No Trip Found", Toast.LENGTH_LONG).show();

                    }
                    else if (flag.equals(Constants.INVALID_REQUEST))
                    {

                        Toast.makeText(getActivity(), "invalid request", Toast.LENGTH_LONG).show();
                    }


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }


                if (array == null)
                {
                    // linear_no_new_notifications.setVisibility(View.VISIBLE);
                }
                else
                {
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


        }, new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error)
            {

                rotateloading.stop();
                Log.d("ErroeVolley", error.getMessage());

            }
        })
        {

            @Override
            protected Map<String, String> getParams()
            {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("PassengerId", new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
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


}

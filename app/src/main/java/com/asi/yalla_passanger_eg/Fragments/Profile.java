package com.asi.yalla_passanger_eg.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.asi.yalla_passanger_eg.EditeProfile;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.Models.ProfileDataModel;
import com.asi.yalla_passanger_eg.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment
{

    TextView tv_fname,tvtitle,tvLname,tvEmail,tvphone;
    private View view;
    ImageView ivEdite,profile_image;
    RotateLoading rotateloading;
    FrameLayout frameLayout;

    public Profile()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        tv_fname = (TextView) view.findViewById(R.id.tv_fname);
        tvLname= (TextView) view.findViewById(R.id.tvLname);
        tvtitle= (TextView) view.findViewById(R.id.tvtitle);
        ivEdite= (ImageView) view.findViewById(R.id.ivEdite);
        tvEmail= (TextView) view.findViewById(R.id.tvEmail);
        tvphone= (TextView) view.findViewById(R.id.tvphone);
        rotateloading= (RotateLoading) view.findViewById(R.id.rotateloading);
        rotateloading.start();
        frameLayout= (FrameLayout) view.findViewById(R.id.frameLayout);
        frameLayout.setVisibility(View.GONE);
        profile_image= (ImageView) view.findViewById(R.id.profile_image);
        ivEdite.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), EditeProfile.class));
            }
        });
        tv_fname.setText(new SQLiteHandler(getActivity()).getUserDetails().get("name"));
        getPassengerProfile(new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
        //setHasOptionsMenu(true);
        return view;
    }


    /**
     * get the passenger profile
     *
     * @param uid
     */
    public void getPassengerProfile(String uid)
    {

        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", uid);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "PassengerProfile", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {



                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + "USER PROFILE  DATA", response.toString());




                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.SUCCSESS))
                            {
                                Constants.saveUserProfileData(response.toString(),getActivity());
                                frameLayout.setVisibility(View.VISIBLE);
                                rotateloading.stop();
                                ProfileDataModel user = new Gson().fromJson(response.toString(), ProfileDataModel.class);
                                tv_fname.setText(user.getName());
                                tvtitle.setText(user.getSalutation());
                                tvLname.setText(user.getLastName());
                                tvEmail.setText(user.getEmail());
                                tvphone.setText(user.getCountryCode()+"-"+user.getPhone());
                                Picasso.with(getActivity()).load(user.getPicture())
                                        .placeholder(R.drawable.user)
                                        .error(R.drawable.user)
                                        .into(profile_image);


                            }
                            else if (flag.equals(Constants.INVALID_REQUEST))
                            {
                                //invalid request
                            }
                            else if (flag.equals(Constants.INVALID_USER))
                            {
//                                //invalid_user
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
                rotateloading.stop();

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

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
//    {
//        inflater.inflate(R.menu.edite, menu);
//        return;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        switch (item.getItemId()) {
//            case R.id.action_edite:
//               // do some thing here
//                return true;
//
//
//        }
//        return onOptionsItemSelected(item);
//    }

}

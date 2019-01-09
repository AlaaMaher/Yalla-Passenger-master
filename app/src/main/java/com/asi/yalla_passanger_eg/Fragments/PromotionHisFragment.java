package com.asi.yalla_passanger_eg.Fragments;

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
import com.asi.yalla_passanger_eg.Adapters.promotionCodeAdpter;
import com.asi.yalla_passanger_eg.AppController;
import com.asi.yalla_passanger_eg.Constants;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.Models.promotionCodeModel;
import com.asi.yalla_passanger_eg.R;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class PromotionHisFragment extends Fragment
{

    private View view;
    RecyclerView recyclerViewPromo;
    promotionCodeAdpter codeAdpter;
    RotateLoading rotateLoading;
    ArrayList<promotionCodeModel> codeModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_promotion_his, container, false);
        ini(view);
        return view;
    }

    private void ini(View view)
    {

        recyclerViewPromo = (RecyclerView) view.findViewById(R.id.RVPromoCode);
        rotateLoading= (RotateLoading) view.findViewById(R.id.rotateloading);

        getPromotionsHis();


    }

    public void getPromotionsHis()
    {
        rotateLoading.start();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                Constants.BASE_URL +"PassengerPromoCodeHistory", new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                //here is the response of server


                try
                {
                    JSONObject objectt=new JSONObject(response);
                    String flag=objectt.getString("Flag");
                    if (flag.equals(Constants.INVALID_REQUEST))
                    {
                        Toasty.error(getActivity(),getActivity().getResources().getString(R.string.thereisanerror), Toast.LENGTH_LONG,true).show();
                    }else {
                        rotateLoading.stop();
                        JSONArray array = null;

                        array = objectt.getJSONArray("PromoLst");

                        for (int n = 0; n < array.length(); n++)
                        {
                            try
                            {
                                JSONObject object = array.getJSONObject(n);
                                String tripid = object.getString("TripId");
                                String Date = object.getString("Date");
                                String Promocode = object.getString("Promocode");
                                String PassengerDiscount = object.getString("PassengerDiscount");
                                codeModels.add(new promotionCodeModel(tripid, Promocode, PassengerDiscount, Date));
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            // do some stuff....
                        }
                        codeAdpter = new promotionCodeAdpter(getActivity(), codeModels);
                        recyclerViewPromo.setAdapter(codeAdpter);
                        recyclerViewPromo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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

                rotateLoading.stop();
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

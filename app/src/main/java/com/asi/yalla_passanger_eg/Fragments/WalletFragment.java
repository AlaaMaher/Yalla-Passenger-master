package com.asi.yalla_passanger_eg.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;



public class WalletFragment extends Fragment
{
    private View view;
    // TODO: Rename parameter arguments, choose names that match


    TextView tvMoney;
    FancyButton btn_addmoney;

    public WalletFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wallet, container, false);
        getPassengerWallet(new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
        tvMoney = (TextView) view.findViewById(R.id.tvMoney);
        btn_addmoney = (FancyButton) view.findViewById(R.id.btn_addmoney);
        btn_addmoney.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Toasty.info(getActivity(),"سيتم توافر هذه الميزه قريبا ", Toast.LENGTH_LONG).show();
                LayoutInflater li = LayoutInflater.from(getActivity());
                View promptsView = li.inflate(R.layout.inputdialoge, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);


                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.dialog_okk),
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {

                                        String rechargeCardNumber = userInput.getText().toString().trim();
                                        AddToPassengerWallet(rechargeCardNumber);


                                    }
                                })
                        .setNegativeButton(getResources().getString(R.string.dialog_cancell),
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int id)
                                    {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        return view;
    }

    /**
     * get the passenger wallet amount
     *
     * @param uid
     */
    public void getPassengerWallet(String uid)
    {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(getActivity().getResources().getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", uid);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "GetPassengerWalletAmount", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {


                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + "USER AMOUNT  DATA", response.toString());
                        progressDialog.dismiss();

                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.SUCCSESS))
                            {

                                tvMoney.setText(response.getString("WalletAmount"));
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
                progressDialog.dismiss();
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
     * Add money to wallet
     *
     * @param num
     */
    public void AddToPassengerWallet(String num)
    {

        final SweetAlertDialog progressDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(getActivity().getResources().getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);
        progressDialog.show();
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
        postParam.put("VoucherNumber", num);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "AddToPassengerWallet", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {


                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + "ADD MONEY TO WALLET", response.toString());


                        progressDialog.dismiss();
                        //82 - 83

                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.MONEY_ADDED_TO_WALLET))
                            {
                                Toasty.success(getActivity(),getActivity().getResources().getString(R.string.MoneyAddedToWallet), Toast.LENGTH_LONG).show();
                                getPassengerWallet(new SQLiteHandler(getActivity()).getUserDetails().get("uid"));
                                progressDialog.dismiss();
                            }
                            else if (flag.equals(Constants.INVALID_REQUEST))
                            {
                                //invalid request
                                progressDialog.dismiss();
                            }
                            else if (flag.equals(Constants.INVALID_USER))
                            {
                               //invalid_user
                                progressDialog.dismiss();
                            }
                            else if (flag.equals(Constants.VOUCHER_NUMBER_NOT_FOUND))
                            {
                                Toasty.info(getActivity(),getActivity().getResources().getString(R.string.voucherNumberNotFound), Toast.LENGTH_LONG).show();
                            }else if (flag.equals(Constants.VOUCHER_EXPIRED))
                            {
                                Toasty.info(getActivity(),getActivity().getResources().getString(R.string.voucherexpired), Toast.LENGTH_LONG).show();
                            }else if (flag.equals(Constants.VOUCHER_ALREADY_USED))
                            {
                                Toasty.info(getActivity(),getActivity().getResources().getString(R.string.voucheralredyused), Toast.LENGTH_LONG).show();
                            }
                            else if (flag.equals(Constants.VOUCHER_BLOCKED))
                            {
                                Toasty.info(getActivity(),getActivity().getResources().getString(R.string.voucherblocked), Toast.LENGTH_LONG).show();
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
                progressDialog.dismiss();

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
}

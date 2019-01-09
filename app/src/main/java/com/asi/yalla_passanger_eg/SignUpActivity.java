package com.asi.yalla_passanger_eg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    LinearLayout signUpLayout,smsCodeLayout;
    EditText input_fname,input_lname,input_email,input_phone,input_password,input_repassword,inputOtp;
    CountryCodePicker tvCountryCode;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        ini();

    }

    private void ini()
    {

        signUpLayout= (LinearLayout) findViewById(R.id.linearSignUp);
        smsCodeLayout= (LinearLayout) findViewById(R.id.layout_otp_sms);
        input_fname= (EditText) findViewById(R.id.input_fname);
        input_lname= (EditText) findViewById(R.id.input_lname);
        input_email= (EditText) findViewById(R.id.input_email);
        input_phone= (EditText) findViewById(R.id.input_phone);
        input_password= (EditText) findViewById(R.id.input_password);
        input_repassword= (EditText) findViewById(R.id.input_repassword);
        inputOtp= (EditText) findViewById(R.id.inputOtp);
        tvCountryCode= (CountryCodePicker) findViewById(R.id.ccp);
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        deviceId = telephonyManager.getDeviceId();
    }

    public void goToCode(View view)
    {

        SignUpRequest();
    }

    public void choosePaymentMethod(View view)
    {
        //startActivity(new Intent(SignUpActivity.this,ChoosePaymentMethod.class));
        CodeVerication();
    }

    private void SignUpRequest() {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(getApplicationContext().getResources().getString(R.string.signup));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String TAG="ASI";
        Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("FirstName",input_fname.getText().toString());
        postParam.put("LastName",input_lname.getText().toString());
        postParam.put("DeviceId",deviceId);
        postParam.put("DeviceType","1"); // 1- Mean android device
        postParam.put("DeviceToken","0");
        postParam.put("BuildVersion","5");
        postParam.put("Email",input_email.getText().toString());
        postParam.put("Phone",input_phone.getText().toString().trim());
        postParam.put("Password",Constants.convertPassMd5(input_password.getText().toString()));
        postParam.put("ConfirmPassword",Constants.convertPassMd5(input_repassword.getText().toString()));
        postParam.put("OrignalPassword",input_password.getText().toString());
        postParam.put("RefferalCode","");
        postParam.put("ProfilePicture","null");
        postParam.put("CountryCode",tvCountryCode.getSelectedCountryCodeWithPlus());
        postParam.put("Salutation","Mr");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL+"PassengerSignUp", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d(TAG+"USER DATA", response.toString());

                        try {

                            String Flag=response.getString("Flag");
                            Log.e("FLAG-->",Flag);
                            if(Flag.equals(Constants.INVALID_REQUEST))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.thereisanerror))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }else if(Flag.equals(Constants.EMAIL_EXIST))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.email_exist))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }else if(Flag.equals(Constants.PHONE_EXIST))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.phone_exist))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }else if(Flag.equals(Constants.REFFERAL_CODE_NOT_EXIST))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.Ref_code_not_exist))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }
                            else if(Flag.equals(Constants.USER_CREATED))
                            {
//                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.SUCCESS_TYPE)
//                                        .setTitleText(getResources().getString(R.string.Message))
//                                        .setContentText(getResources().getString(R.string.signningup_sucess))
//                                        .setConfirmText(getResources().getString(R.string.done))
//                                        .show();
                                signUpLayout.setVisibility(View.GONE);
                                smsCodeLayout.setVisibility(View.VISIBLE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toasty.error(SignUpActivity.this,"TimeoutError", Toast.LENGTH_LONG,true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(SignUpActivity.this,"AuthFailureError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(SignUpActivity.this,"ServerError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(SignUpActivity.this,"NetworkError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(SignUpActivity.this,"ParseError",Toast.LENGTH_LONG,true).show();
                }
            }
        }) {



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
        AppController.getInstance().addToRequestQueue(jsonObjReq,"TAG");
    }
    private void CodeVerication() {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(getApplicationContext().getResources().getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String TAG="ASI";
        Map<String, String> postParam= new HashMap<String, String>();

        postParam.put("VerificationCode",inputOtp.getText().toString().trim());
        postParam.put("Phone",input_phone.getText().toString().trim());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL+"OtpVerify", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d(TAG+"USER DATA", response.toString());

                        try {

                           // 1  -  2  -  0  - 78
                            String Flag=response.getString("Flag");
                            Log.e("FLAG-->",Flag);
                            if(Flag.equals(Constants.INVALID_REQUEST))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.thereisanerror))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }else if(Flag.equals(Constants.INVALID_USER))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.thereisanerror))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }
                            else if(Flag.equals(Constants.INVALID_VERVICATION_CODE))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.invalidVericationCode))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();

                            }else if(Flag.equals(Constants.SUCCSESS))
                            {
                                Toasty.success(SignUpActivity.this,getResources().getString(R.string.AccountCreated), Toast.LENGTH_LONG,true).show();
                                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toasty.error(SignUpActivity.this,"TimeoutError", Toast.LENGTH_LONG,true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(SignUpActivity.this,"AuthFailureError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(SignUpActivity.this,"ServerError: "+ error.getMessage(),Toast.LENGTH_LONG,true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(SignUpActivity.this,"NetworkError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(SignUpActivity.this,"ParseError",Toast.LENGTH_LONG,true).show();
                }
            }
        }) {



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
        AppController.getInstance().addToRequestQueue(jsonObjReq,"TAG");
    }


    public void GetNewCode(View view) {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(getApplicationContext().getResources().getString(R.string.pleaseWait));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String TAG="ASI";
        Map<String, String> postParam= new HashMap<String, String>();

        postParam.put("Phone",input_phone.getText().toString().trim());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL+"ResendVerificationCode", new JSONObject(postParam),
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {


                            String Flag=response.getString("Flag");
                            Log.e("FLAG-->",Flag);
                            if(Flag.equals(Constants.INVALID_REQUEST))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.thereisanerror))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }else if(Flag.equals(Constants.INVALID_USER))
                            {
                                new SweetAlertDialog(SignUpActivity.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(getResources().getString(R.string.Message))
                                        .setContentText(getResources().getString(R.string.thereisanerror))
                                        .setConfirmText(getResources().getString(R.string.done))
                                        .show();
                            }
                            else if(Flag.equals(Constants.SUCCSESS))
                            {
                                Toasty.success(SignUpActivity.this,getResources().getString(R.string.newCodeSent), Toast.LENGTH_LONG,true).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toasty.error(SignUpActivity.this,"TimeoutError", Toast.LENGTH_LONG,true).show();
                } else if (error instanceof AuthFailureError) {
                    Toasty.error(SignUpActivity.this,"AuthFailureError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ServerError) {
                    Toasty.error(SignUpActivity.this,"ServerError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof NetworkError) {
                    Toasty.error(SignUpActivity.this,"NetworkError",Toast.LENGTH_LONG,true).show();
                } else if (error instanceof ParseError) {
                    Toasty.error(SignUpActivity.this,"ParseError",Toast.LENGTH_LONG,true).show();
                }
            }
        }) {



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
        AppController.getInstance().addToRequestQueue(jsonObjReq,"TAG");
    }
}

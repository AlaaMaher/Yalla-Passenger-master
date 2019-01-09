package com.asi.yalla_passanger_eg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.Models.ProfileDataModel;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class EditeProfile extends AppCompatActivity
{

    ImageView  ivProfileImage;
    private FrameLayout frameLayout;
    EditText fName, lName, userMail, mobile;
    TextView tvtitle;
    NestedScrollView nestedParentLayout;
    CountryCodePicker ccp;
    private int REQUEST_CODE_PICKER=1;
    ProfileDataModel user;
    private String base64_string;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_profile);
        ini();
        // setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        frameLayout.setFocusable(true);
    }

    public void ini()
    {
        //toolbar= (Toolbar) findViewById(R.id.toolbar);

        // ivEdite.setOnClickListener(this);
        user = new Gson().fromJson(Constants.getUserProfileData(EditeProfile.this), ProfileDataModel.class);
        frameLayout = (FrameLayout) findViewById(R.id.frame);
        fName = (EditText) findViewById(R.id.etFname);
        fName.setText(user.getName());
        lName = (EditText) findViewById(R.id.edLname);
        lName.setText(user.getLastName());
        userMail = (EditText) findViewById(R.id.etEmail);
        userMail.setText(user.getEmail());
        mobile = (EditText) findViewById(R.id.etmobile);
        mobile.setText(user.getPhone());
        tvtitle= (TextView) findViewById(R.id.tvtitle);
        tvtitle.setText(user.getSalutation());
        nestedParentLayout = (NestedScrollView) findViewById(R.id.parentlayout);
        ivProfileImage = (ImageView) findViewById(R.id.profile_image);

        ccp= (CountryCodePicker) findViewById(R.id.ccp);



    }



    public void editProfile()
    {
        final SweetAlertDialog progressDialog = new SweetAlertDialog(EditeProfile.this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        progressDialog.setTitleText(getResources().getString(R.string.LOADING));
        progressDialog.setCancelable(false);
        progressDialog.show();

        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", new SQLiteHandler(EditeProfile.this).getUserDetails().get("uid"));
        postParam.put("Salutation",tvtitle.getText().toString());
        postParam.put("FirstName",fName.getText().toString());
        postParam.put("LastName",lName.getText().toString());
        postParam.put("Email",userMail.getText().toString());
        postParam.put("CountryCode",ccp.getSelectedCountryCodeWithPlus());
        postParam.put("Phone",mobile.getText().toString());
        postParam.put("ProfilePicture", base64_string);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "EditPassengerProfile", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {


                        progressDialog.cancel();
                        Log.e(TAG, response.toString());
                        try
                        {
                            String Flag = response.getString("Flag");

                            if (Flag.equals(Constants.INVALID_USER))
                            {

                                //invalid_user
                            }
                            else if (Flag.equals(Constants.INVALID_REQUEST))
                            {

                                //invalid_request
                            }
                            else if (Flag.equals(Constants.SUCCSESS))
                            {

                                Toasty.success(EditeProfile.this,getResources().getString(R.string.Profilesuccessfullymodified), Toast.LENGTH_LONG,true).show();
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
                Log.d(TAG, "Error: " + error.getMessage());

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
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "TAG");
    }

    public void SetTitle(View view)
    {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(EditeProfile.this, view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.titlemeun, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.mr_)
                {
                    tvtitle.setText(item.getTitle());
                }else if (item.getItemId()==R.id.mrs_)
                {
                    tvtitle.setText(item.getTitle());
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    public void ChoosePic(View view)
    {
        ImagePicker.create(this) // Activity or Fragment
                .single()
                .start(REQUEST_CODE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == RESULT_OK && data != null) {
             ArrayList <Image> images = (ArrayList<Image>) ImagePicker.getImages(data);

            Uri myUri = Uri.parse("file://"+images.get(0).getPath());


            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(
                        myUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
            base64_string=ConvertTobitmap(bmp);
            Log.e("BASE IS -->",base64_string);
            ivProfileImage.setImageBitmap(bmp);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String ConvertTobitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return  Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public void EditeProfile(View view)
    {
        editProfile();
    }
}

package com.asi.yalla_passanger_eg;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.asi.yalla_passanger_eg.Fragments.AboutFragment;
import com.asi.yalla_passanger_eg.Fragments.FareFragment;
import com.asi.yalla_passanger_eg.Fragments.FavFragment;
import com.asi.yalla_passanger_eg.Fragments.Home;
import com.asi.yalla_passanger_eg.Fragments.Profile;
import com.asi.yalla_passanger_eg.Fragments.PromotionHisFragment;
import com.asi.yalla_passanger_eg.Fragments.SettingsFragment;
import com.asi.yalla_passanger_eg.Fragments.WalletFragment;
import com.asi.yalla_passanger_eg.Fragments.YourTripsFragment;
import com.asi.yalla_passanger_eg.LoingSession.SQLiteHandler;
import com.asi.yalla_passanger_eg.LoingSession.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    FragmentManager fragmentManager;
    TextView txtTitle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);


        fragmentManager.beginTransaction()
                .replace(R.id.content_main, new Home()).setCustomAnimations(R.anim.anim_slide_in_from_left,R.anim.anim_slide_out_from_left)
                .commit();
        //intit icon for open drawer
        findViewById(R.id.imMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        txtTitle= (TextView) findViewById(R.id.txtTitle);

        inflateNavHeader();

    }


    // get data from sqlite to Nav header view
    public void inflateNavHeader()
    {

        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
           /*View view=navigationView.inflateHeaderView(R.layout.nav_header_main);*/
        TextView name = (TextView)header.findViewById(R.id.userNameNav);
        name.setText(new SQLiteHandler(MainActivity.this).getUserDetails().get("name"));
        ImageView ivProfilePic= (ImageView) header.findViewById(R.id.ivProfilePic);
        Picasso.with(MainActivity.this).load(new SQLiteHandler(MainActivity.this).getUserDetails().get("pro_pic"))
                .placeholder(R.drawable.user).into(ivProfilePic);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = new Home();
        if (id == R.id.nav_home)
        {
            fragment = new Home();
            txtTitle.setText(getString(R.string.Home));
        }
        else if (id == R.id.nav_profile)
        {
            fragment = new Profile();
            txtTitle.setText(getString(R.string.profile));
        }
//        else if (id == R.id.nav_payment)
//        {
//            fragment=new Paymant();
//            txtTitle.setText(getString(R.string.Payment));
//        }
        else if (id == R.id.nav_wallet)
        {
            fragment=new WalletFragment();
            txtTitle.setText(getString(R.string.wallet));
        }
        else if (id == R.id.nav_your_trips)
        {
            fragment=new YourTripsFragment();
            txtTitle.setText(getString(R.string.yourTrips));
        }
        else if (id == R.id.nav_fav)
        {
            fragment=new FavFragment();
            txtTitle.setText(getString(R.string.favourite));
        } else if (id == R.id.nav_promo_his)
        {
            fragment=new PromotionHisFragment();
            txtTitle.setText(getString(R.string.promotion));
        }else if (id == R.id.nav_fare)
        {
            fragment=new FareFragment();
            txtTitle.setText(getString(R.string.fare));
        }else if (id == R.id.nav_info)
        {
            fragment=new AboutFragment();
            txtTitle.setText(getString(R.string.about));
        }else if (id == R.id.nav_settings)
        {
            fragment=new SettingsFragment();
            txtTitle.setText(getString(R.string.action_settings));
        }else if (id == R.id.nav_log_out)
        {

            logOut(new SQLiteHandler(MainActivity.this).getUserDetails().get("uid"));

        }
        fragmentManager.beginTransaction()
                .replace(R.id.content_main, fragment).setCustomAnimations(R.anim.anim_slide_in_from_left,R.anim.anim_slide_out_from_left)
                .commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * log the passenger out
     *
     * @param uid
     */
    public void logOut(String uid)
    {
        final String TAG = "ASI";
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("PassengerId", uid);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.BASE_URL + "PassengerLogout", new JSONObject(postParam),
                new Response.Listener<JSONObject>()
                {



                    @Override
                    public void onResponse(JSONObject response)
                    {
                        Log.d(TAG + "USER PROFILE  DATA", response.toString());



                        //1 - 2 - 14

                        try
                        {
                            String flag = response.getString("Flag");
                            if (flag.equals(Constants.LOGOUT_SUCCESS))
                            {
                                new SessionManager(MainActivity.this).setLogin(false);
                                new SQLiteHandler(MainActivity.this).deleteUsers();
                                startActivity(new Intent(MainActivity.this, LoginSignup.class));
                                finish();
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
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setContentText(getResources().getString(R.string.AreYou))
                .setTitleText(getResources().getString(R.string.LEAVEAPP))
                .setCancelText(getResources().getString(R.string.no))
                .setConfirmText(getResources().getString(R.string.yes))
                .showCancelButton(true)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        finish();
                    }
                })
                .show();
//

    }
}

package com.asi.yalla_passanger_eg.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.asi.yalla_passanger_eg.ChangePassword;
import com.asi.yalla_passanger_eg.Constants;
import com.asi.yalla_passanger_eg.R;
import com.asi.yalla_passanger_eg.SplachScreen;


public class SettingsFragment extends Fragment {

    LinearLayout changeLang,changePass;
    private View view;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_settings, container, false);
        ini(view);
        return view;
    }


    public void ini(View view)
    {
        changeLang= (LinearLayout) view.findViewById(R.id.changeLangLayout);
        changePass= (LinearLayout)  view.findViewById(R.id.changePassLayout);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), view);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.langmenu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.one)
                        {
                            Constants.ChangeLang( "ar",getActivity());
                            Intent intent = new Intent(getActivity(), SplachScreen.class);
                            //In API level 11 or greater, use FLAG_ACTIVITY_CLEAR_TASK and FLAG_ACTIVITY_NEW_TASK flag on Intent to clear all the activity stack.
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }else if (item.getItemId()==R.id.two)
                        {
                            Constants.ChangeLang( "en", getActivity());
                            Intent intent = new Intent(getActivity(), SplachScreen.class);
                            //In API level 11 or greater, use FLAG_ACTIVITY_CLEAR_TASK and FLAG_ACTIVITY_NEW_TASK flag on Intent to clear all the activity stack.
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ChangePassword.class));
            }
        });
    }

}

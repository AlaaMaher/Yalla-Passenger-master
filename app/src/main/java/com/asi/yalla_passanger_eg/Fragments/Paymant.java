package com.asi.yalla_passanger_eg.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.asi.yalla_passanger_eg.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Paymant extends Fragment
{


    private View view;

    public Paymant()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_paymant, container, false);

        return view;
    }


}

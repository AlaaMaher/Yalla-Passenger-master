package com.asi.yalla_passanger_eg.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asi.yalla_passanger_eg.Constants;
import com.asi.yalla_passanger_eg.Models.CarModelsModel;
import com.asi.yalla_passanger_eg.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FareFragment extends Fragment {
    private TextView basefareTxt;
    private TextView minfareTxt;
    private TextView belowfareTxt;
    private TextView abovefareTxt;
    private TextView nightfareTxt;
    private TextView cancelfareTxt;
    private TextView evefareTxt;
    private TextView basekm;
    private TextView minkm;
    private TextView belowkm;
    private TextView abovekm;
    private TextView nighttime, evefare, leftIcon, back_text;
    private LinearLayout CancelBtn;
    private TextView HeadTitle;
    private LinearLayout lay_cir_car1;
    private ImageView txt_dra_car1;
    private TextView txt_car1;
    private LinearLayout lay_cir_car2;
    private ImageView txt_dra_car2;
    private TextView txt_car2;
    private LinearLayout lay_cir_car3;
    private ImageView txt_dra_car3;
    private TextView txt_car3;
    private LinearLayout lay_cir_car4;
    private ImageView txt_dra_car4;
    private TextView txt_car4;
    private String carModel = "1";
    private LinearLayout lay_model_one;
    private LinearLayout lay_model_two;
    private LinearLayout lay_model_three;
    private LinearLayout lay_model_four;
    View view_car1, view_car2, view_car3,getView_car4;
    private View view;
    private CarModelsModel ModelsData;
    private List<CarModelsModel> carModelsModels=new ArrayList<>();

    public FareFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_fare, container, false);
        ini(view);
        return view;
    }


    public  void  ini(View view)
    {
        basefareTxt = (TextView) view.findViewById(R.id.basefareTxt);
        minfareTxt = (TextView)  view.findViewById(R.id.minfareTxt);
        belowfareTxt = (TextView)  view.findViewById(R.id.belowfareTxt);
        abovefareTxt = (TextView)  view.findViewById(R.id.abovefareTxt);
        nightfareTxt = (TextView)  view.findViewById(R.id.nightfareTxt);
        cancelfareTxt = (TextView)  view.findViewById(R.id.cancelfareTxt);
        evefareTxt = (TextView)  view.findViewById(R.id.evefareTxt);
        evefare = (TextView)  view.findViewById(R.id.evefare);
        basekm = (TextView)  view.findViewById(R.id.basekm);
        minkm = (TextView)  view.findViewById(R.id.minkm);
        belowkm = (TextView)  view.findViewById(R.id.belowkm);
        abovekm = (TextView)  view.findViewById(R.id.abovekm);
        nighttime = (TextView)  view.findViewById(R.id.nightchrgtime);

        lay_cir_car1 = (LinearLayout)  view.findViewById(R.id.lay_cir_car1);
        lay_cir_car2 = (LinearLayout)  view.findViewById(R.id.lay_cir_car2);
        lay_cir_car3 = (LinearLayout)  view.findViewById(R.id.lay_cir_car3);
        lay_cir_car4 = (LinearLayout)  view.findViewById(R.id.lay_cir_car4);
        txt_dra_car1 = (ImageView)  view.findViewById(R.id.txt_dra_car1);
        txt_dra_car2 = (ImageView)  view.findViewById(R.id.txt_dra_car2);
        txt_dra_car3 = (ImageView)  view.findViewById(R.id.txt_dra_car3);
        txt_dra_car4 = (ImageView)  view.findViewById(R.id.txt_dra_car4);
        txt_car1 = (TextView)  view.findViewById(R.id.txt_car1);
        txt_car2 = (TextView)  view.findViewById(R.id.txt_car2);
        txt_car3 = (TextView)  view.findViewById(R.id.txt_car3);
        txt_car4 = (TextView)  view.findViewById(R.id.txt_car4);
        lay_model_one = (LinearLayout)  view.findViewById(R.id.lay_model_one);
        lay_model_two = (LinearLayout)  view.findViewById(R.id.lay_model_two);
        lay_model_three = (LinearLayout)  view.findViewById(R.id.lay_model_three);
        lay_model_four = (LinearLayout)  view.findViewById(R.id.lay_model_four);
        view_car1 = (View)  view.findViewById(R.id.view_car1);
        view_car2 = (View)  view.findViewById(R.id.view_car2);
        view_car3 = (View) view. findViewById(R.id.view_car3);

        JSONArray array = null;
        try
        {
            array = new JSONArray(Constants.getCarModels(getActivity()));
            // Loop through the array elements
            for (int i = 0; i < array.length(); i++)
            {
                // Get current json object
                JSONObject modelObject = array.getJSONObject(i);
                ModelsData = new Gson().fromJson(modelObject.toString(), CarModelsModel.class);
                carModelsModels.add(ModelsData);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        setModelDat(0);

        // Update the while pick the 1st car model.
        lay_model_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {

                    lay_cir_car1.setBackgroundResource(R.drawable.fare_round_focus);
                    lay_cir_car2.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car3.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car4.setBackgroundResource(R.drawable.fare_round_unfocus);
                    txt_dra_car1.setImageResource(R.drawable.car1_unfocus);
                    txt_dra_car2.setImageResource(R.drawable.car22_focus);
                    txt_dra_car3.setImageResource(R.drawable.car3_unfocus);
                    txt_dra_car4.setImageResource(R.drawable.ic_delivery_truck);

                    setModelDat(0);
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        // Update the while pick the 2nd car model.
        lay_model_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
//                    final JSONArray array = new JSONArray(SessionSave.getSession("model_details_update", FareAct.this));
//                    carModel = array.getJSONObject(1).getString("model_id");
                    //Log.e("DATA OF FARE TWO ===>",array.getJSONObject(1).toString());
                    lay_cir_car1.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car2.setBackgroundResource(R.drawable.fare_round_focus);
                    lay_cir_car3.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car4.setBackgroundResource(R.drawable.fare_round_unfocus);
                    txt_dra_car1.setImageResource(R.drawable.car1_unfocus);
                    txt_dra_car2.setImageResource(R.drawable.car22_focus);
                    txt_dra_car3.setImageResource(R.drawable.car3_unfocus);
                    txt_dra_car4.setImageResource(R.drawable.ic_delivery_truck);

                    setModelDat(1);
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        // Update the while pick the 3rd car model.
        lay_model_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
//                    final JSONArray array = new JSONArray(SessionSave.getSession("model_details_update", FareAct.this));
//                    carModel = array.getJSONObject(2).getString("model_id");
                   // Log.e("DATA OF FARE TWO ===>",array.getJSONObject(2).toString());
                    lay_cir_car1.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car2.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car3.setBackgroundResource(R.drawable.fare_round_focus);
                    lay_cir_car4.setBackgroundResource(R.drawable.fare_round_unfocus);
                    txt_dra_car1.setImageResource(R.drawable.car1_unfocus);
                    txt_dra_car2.setImageResource(R.drawable.car22_focus);
                    txt_dra_car3.setImageResource(R.drawable.car3_unfocus);
                    txt_dra_car4.setImageResource(R.drawable.ic_delivery_truck);

                    setModelDat(2);
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });
        // Update the while pick the 4th car model.
        lay_model_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                try {
//                    final JSONArray array = new JSONArray(SessionSave.getSession("model_details_update", FareAct.this));
//                    carModel = array.getJSONObject(3).getString("model_id");
                   // Log.e("DATA OF FARE TWO ===>",array.getJSONObject(3).toString());
                    lay_cir_car1.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car2.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car3.setBackgroundResource(R.drawable.fare_round_unfocus);
                    lay_cir_car4.setBackgroundResource(R.drawable.fare_round_focus);
                    txt_dra_car1.setImageResource(R.drawable.car1_unfocus);
                    txt_dra_car2.setImageResource(R.drawable.car22_focus);
                    txt_dra_car3.setImageResource(R.drawable.car3_unfocus);
                    txt_dra_car4.setImageResource(R.drawable.ic_delivery_truck);
                    setModelDat(3);
                }
                catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        });



    }


    public void setModelDat(int modelId)
    {
        minkm.setText("" + getResources().getString(R.string.min_fare) + " " + carModelsModels.get(modelId).getMinKM());
        belowkm.setText("" + getResources().getString(R.string.below) + " " + carModelsModels.get(modelId).getBelowKmRange() );
        abovekm.setText("" + getResources().getString(R.string.above) + " " + carModelsModels.get(modelId).getAboveKmRange() );
        basefareTxt.setText( String.format(Locale.UK, "%.2f", Float.parseFloat(carModelsModels.get(modelId).getBaseFare())));
        minfareTxt.setText( String.format(Locale.UK, "%.2f", Float.parseFloat(carModelsModels.get(modelId).getMinFare())));
        belowfareTxt.setText( String.format(Locale.UK, "%.2f", Float.parseFloat(carModelsModels.get(modelId).getBaseFare())));
        abovefareTxt.setText( String.format(Locale.UK, "%.2f", Float.parseFloat(carModelsModels.get(modelId).getBaseFare())));
        cancelfareTxt.setText( String.format(Locale.UK, "%.2f", Float.parseFloat(carModelsModels.get(modelId).getCancellationFare())));
        nightfareTxt.setText(""+carModelsModels.get(modelId).getNightFare()+"%");
        evefareTxt.setText(""+carModelsModels.get(modelId).getEveningCharge()+"%");
    }


}

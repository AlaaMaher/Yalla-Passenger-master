package com.asi.yalla_passanger_eg.MAP;

import android.content.Context;
import android.os.AsyncTask;

import com.asi.yalla_passanger_eg.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by m.khalid on 7/5/2017.
 */
public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>>
{

    GoogleMap map;
    Context context;
    public ParserTask(GoogleMap map,Context context)
    {
        this.map=map;
        this.context=context;
    }

    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData)
    {

        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try
        {
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return routes;
    }

    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result)
    {
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;
        MarkerOptions markerOptions = new MarkerOptions();
        String distance = "";
        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++)
        {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++)
            {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }


            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(context.getResources().getColor(R.color.colorPrimary));
        }

        // Drawing polyline in the Google Map for the i-th route
        map.addPolyline(lineOptions);
    }

    public String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }
}
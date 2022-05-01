package edu.neu.madcourse.joinus.util;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Utils {

    public static void setInputReset(View view){
        if (view instanceof EditText){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((EditText) view).getText().clear();
                }
            });

        }
    }

    public static double getDistance(double lat1,
                                  double lon1, double lat2,
                                  double lon2)
    {

        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }

    public static String getCityName(double latitude, double longitude) throws IOException {
        String cityName;
        @SuppressLint("RestrictedApi") Geocoder geocoder = new Geocoder(getApplicationContext(),
                Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        cityName = addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
        return cityName;
    }

}

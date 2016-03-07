package com.marianilga.mypizza.data.network;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.marianilga.mypizza.R;


/**
 * This class gets location of user using Network or GPS.
 */
public class UserLocation implements LocationListener {

    private LocationManager locationManager;
    public Double longitude;
    public Double latitude;
    public boolean isLocationKnown = false;
    private Context context;
    public boolean isProviderEnabled;


    public UserLocation(Context context) {
        this.context = context;
        isProviderEnabled = initLocation();
    }


    private boolean initLocation() {

        // Check permission
        if ((ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(context,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

            return false;
        }



        try {
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            // Get GPS and network status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isNetworkEnabled && !isGPSEnabled) {
                // cannot get location
                return false;
            }
            else  {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 100, this);
                }

                if (isGPSEnabled)  {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, this);
                }
            }
        } catch (Exception e)  {
            Log.e("pizzaLog", "Error creating location service: " + e );
            return false;
        }


       return true;
    }


    /**
     * Set the coordinates of location of the user.
     * @param latitude
     * @param longitude
     */
    public void setCoordinates( Double latitude, Double longitude){
        isLocationKnown = true;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        Log.v("pizzaLog", "latitude " + latitude + " location.getProvider() = " +  location.getProvider());
        longitude = location.getLongitude();
        Log.v("pizzaLog", "longitude " + longitude);

        if (!isLocationKnown) {
            // Notify user about getting location.
            Toast.makeText(context, R.string.get_location, Toast.LENGTH_LONG).show();
        }

        isLocationKnown = true;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {    }


    // Method returns true if there is connection to the Internet.
    public  boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

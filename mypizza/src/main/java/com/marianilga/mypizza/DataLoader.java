package com.marianilga.mypizza;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Toast;

import com.marianilga.mypizza.data.local.DBRepository;
import com.marianilga.mypizza.data.model.Restaurant;
import com.marianilga.mypizza.data.network.NetworkRepository;
import com.marianilga.mypizza.data.network.UserLocation;

import java.util.List;

/**
 * Load date from server
 */
public class DataLoader extends AsyncTask<Integer, Boolean, List<Restaurant>> {


    private final ListAdapter adapter;
    private final NetworkRepository repository;
    private final DBRepository repositoryDB = new DBRepository();
    private UserLocation userLocation;
    private Context context;
    // Waiting time to get location is 1 minute.
    private int waitingTime = 60000;
    private boolean isNeedCheckedLocation;


    public DataLoader(ListAdapter adapter, NetworkRepository repository, UserLocation userLocation,
                      Context context, boolean isNeedCheckedLocation) {
        this.adapter = adapter;
        this.repository = repository;
        this.userLocation = userLocation;
        this.context = context;
        this.isNeedCheckedLocation = isNeedCheckedLocation;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onPostExecute(List<Restaurant> restaurants) {
        if (!isCancelled())
        adapter.upDateEntries(restaurants);
    }


    @Override
    protected List<Restaurant> doInBackground(Integer... params) {

        int offset = params[0];

        // If there is not connection to the internet return data from the database.
        if (!userLocation.isNetworkAvailable(context)) {
            return repositoryDB.getList(offset);
        }

        if (isNeedCheckedLocation) {

            if (!userLocation.isProviderEnabled) {
                // Provider is not enabled, notify user about it.
                publishProgress(false);

            } else {
                int time = 0;
                // Wait to get location.
                while ((!userLocation.isLocationKnown) && (time < waitingTime)) {
                    SystemClock.sleep(2000);
                    time += 2000;
                }
            }
        }

        // Get a location.
        if (userLocation.isLocationKnown) {

            // Getting location is success.
            publishProgress(true);

            double latitude = userLocation.latitude;
            double longitude = userLocation.longitude;

            // If there is connection to the internet go to the server.
            if (userLocation.isNetworkAvailable(context)) {
                List<Restaurant> restaurants = repository.getList(latitude, longitude, offset);

                if (restaurants!=null && restaurants.size() != 0) {
                    // Save received data in the database.
                    repositoryDB.saveToDB(restaurants, offset);
                }
            }
        }
        else {
            // Getting location is fail.
            publishProgress(false);
        }

        return repositoryDB.getList(offset);
    }


    @Override
    protected void onProgressUpdate(Boolean... values) {

        if (!values[0]) {
            Toast.makeText(context, R.string.no_location, Toast.LENGTH_LONG).show();
        }
        super.onProgressUpdate(values);
    }


    public void stop(){
        cancel(true);
    }
}

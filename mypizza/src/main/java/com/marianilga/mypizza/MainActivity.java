package com.marianilga.mypizza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.marianilga.mypizza.data.model.Restaurant;
import com.marianilga.mypizza.data.network.NetworkRepository;
import com.marianilga.mypizza.data.network.UserLocation;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AbsListView.OnScrollListener, CompoundButton.OnCheckedChangeListener{

    // The coordinates of New York.
    private final double LONTITUDE_NEW_YORK = -74.0059700;
    private final double LATITUDE_NEW_YORK = 40.7142700;

    private long lastBackPressTime = 0;

    private int offset = 0;
    private ListView listView;
    private ListAdapter adapter;
    private NetworkRepository repository;
    private UserLocation userLocation;
    private DataLoader dataLoader = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();

        repository = new NetworkRepository();

        listView = (ListView)findViewById(R.id.listView);
        adapter = new ListAdapter(this);

        View footer = new ProgressBar(this);
        footer.setTag("footer");
        listView.addFooterView(footer, null, false);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);

        userLocation = new UserLocation(this);

        // Get location of user and get data.
        startDataLoader(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // Go to DetailActivity.
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", ((Restaurant) parent.getItemAtPosition(position)).getId());
        startActivity(intent);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        // Check if there is a need to download new data.
        boolean loadMore = false;
        if ((totalItemCount != 1) && (totalItemCount - 1 > offset) &&
                (firstVisibleItem + visibleItemCount >= totalItemCount)) {
            loadMore = true;
        }

        if(loadMore) {
            offset = totalItemCount - 1;

            // Get data without getting location.
            startDataLoader(false);
        }
    }

    /**
     * Initialize the toolbar.
     */
    private void setupToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Switch switchNewYork = (Switch)toolbar.findViewById(R.id.switchNewYork);
        switchNewYork.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        adapter.removeAllEntries();
        offset = 0;

        if (isChecked) {
            // Set the coordinates of New York.
            userLocation.setCoordinates(LATITUDE_NEW_YORK, LONTITUDE_NEW_YORK);
        }
        else {
            // Clear previous coordinates.
            userLocation = null;
            userLocation = new UserLocation(this);
        }

        // Get location of user and get data .
        startDataLoader(true);
    }




    // Close application after double click on the button BACK.
    public void onBackPressed() {
        Toast toast = null;
        if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
            toast = Toast.makeText(this, getResources().getString(R.string.click_again), Toast.LENGTH_LONG);
            toast.show();
            this.lastBackPressTime = System.currentTimeMillis();
        } else {
            if (toast != null) {
                toast.cancel();
            }
            finish();
            super.onBackPressed();
        }
    }


    /**
     * Start to download data from server or database
     *
     * @param isNeedLocation
     *         true - is need to get location of user
     *         false - if the location of user is known.
     */
    private void startDataLoader(boolean isNeedLocation){
        if (dataLoader != null) {
            dataLoader.stop();
            dataLoader = null;
        }
        dataLoader = new DataLoader(adapter, repository, userLocation, this, isNeedLocation);
        dataLoader.execute(offset);
    }

}

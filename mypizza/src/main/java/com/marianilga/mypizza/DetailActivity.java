package com.marianilga.mypizza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.marianilga.mypizza.data.model.Restaurant;


/**
 * Show detail information of the restaurant.
 */
public class DetailActivity extends AppCompatActivity {

    private TextView name;
    private TextView distance;
    private TextView phone;
    private TextView address;
    private TextView rating;
    private TextView url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setupViews();

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", 0);

        Restaurant restaurant = Restaurant.findById(Restaurant.class, id);
        if (restaurant != null) {

            if (restaurant.getName() != null)
                name.setText(restaurant.getName());
            distance.setText(String.valueOf(restaurant.getDistance()));

            if (restaurant.getRating() != null)
                rating.setText(String.valueOf(restaurant.getRating()));

            if (restaurant.getPhone() != null)
                phone.setText(restaurant.getPhone());
            if (restaurant.getAddress() != null)
                address.setText(restaurant.getAddress());
            if (restaurant.getUrl() != null)
                url.setText(restaurant.getUrl());
        }

    }


    private void setupViews(){
        name = (TextView) findViewById(R.id.name);
        distance = (TextView) findViewById(R.id.distance);
        phone = (TextView) findViewById(R.id.phone);
        address = (TextView) findViewById(R.id.address);
        rating = (TextView) findViewById(R.id.rating);
        url = (TextView) findViewById(R.id.url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Go to MainActivity.
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

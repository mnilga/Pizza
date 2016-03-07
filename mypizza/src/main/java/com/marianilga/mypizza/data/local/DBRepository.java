package com.marianilga.mypizza.data.local;


import com.marianilga.mypizza.data.model.Restaurant;
import com.orm.query.Select;
import java.util.List;

/**
 * Work with the database.
 */
public class DBRepository {


    /**
     * Get 10 numbers of the restaurants with offset from the database.
     *
     * @param offset
     * @return list of the restaurants
     */
    public List<Restaurant> getList(int offset) {
        // Get list of restaurants from the database.
        Select query = Select.from(Restaurant.class).limit(offset+",10");
        List<Restaurant> restaurantFromDB = query.list();
        return restaurantFromDB;
    }


    /**
     * Save a list of the restaurants in the database.
     *
     * @param restaurants
     * @param offset
     */
    public void saveToDB(List<Restaurant> restaurants, int offset){

        // Delete all previous entry.
        if(offset == 0) {
            Restaurant.deleteAll(Restaurant.class);
        }


        for (Restaurant r: restaurants){
            // Find a row in the database.
            List<Restaurant> findRestaurant = Restaurant.find(Restaurant.class,
                    "venue_id = ?", new String[]{r.getVenueId()});
            if (findRestaurant.size() != 0) {
                r.setId(findRestaurant.get(0).getId());
            }
            r.save();
        }
    }

}

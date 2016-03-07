package com.marianilga.mypizza.data.network;

import com.marianilga.mypizza.data.model.Restaurant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class gets data from server Foursquare
 */
public class NetworkRepository  {

    private final String CLIENT_ID = "HA1CIKMWJ1FRZJ4INNN4BWPQXD2PDTCBORYQFL3XTIUOLLPH";
    private final String CLIENT_SECRET = "IU500OONS1LFZO1YZBKTBNQB2MZI4AGYYBRMNXGY2T5GJC4C";

     private String url = "https://api.foursquare.com/v2/venues/explore?query=pizza&" +
             "&limit=10&sortByDistance=1&client_id="
            + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=" + 20160304;


    public List<Restaurant> getList(double latitude, double longitude, int offset) {

        List<Restaurant> result = new ArrayList<Restaurant>();
        String urlPizza = url + "&ll=" + latitude + "," + longitude + "&offset=" + offset;

        try {
            URL url = new URL(urlPizza);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Get a response from server.
            String resultString = "";
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line = br.readLine()) != null) {
                    resultString += line;
                }

                // Get json.
                JSONObject jsonRootObject = new JSONObject(resultString);
                JSONObject isonResponse = jsonRootObject.getJSONObject("response");
                JSONArray arrGroups = isonResponse.getJSONArray("groups");
                JSONObject jsonGroup = arrGroups.getJSONObject(0);
                JSONArray arrItems = jsonGroup.getJSONArray("items");

                for (int i = 0; i < arrItems.length(); i++) {
                    JSONObject jsonItems = arrItems.getJSONObject(i);
                    JSONObject isonVenue = jsonItems.getJSONObject("venue");
                    result.add(convertRestaurant(isonVenue));
                }

            }

            conn.disconnect();



        } catch (Throwable t) {  }
        return result;
    }


    /**
     * Convert json to the object Restaurant.
     *
     * @param obj
     *        json object
     * @return object Restaurant
     * @throws JSONException
     */
    private Restaurant convertRestaurant(JSONObject obj) throws JSONException {

        String venueId = obj.getString("id");
        String name = obj.getString("name");

        String url  = "";
        try {url = obj.getString("url");
        } catch (Exception e) {}

        Double rating = null;
        try {  rating = obj.getDouble("rating");
        } catch (Exception e) {}

        JSONObject jsonLocation = obj.getJSONObject("location");
        Double distance = jsonLocation.getDouble("distance");
        String address = "";
        try { address = jsonLocation.getString("address");
        } catch (Exception e) {}

        JSONObject jsonContact = obj.getJSONObject("contact");
        String phone = "";
        try { phone = jsonContact.getString("phone");
        } catch (Exception e) {}

        Restaurant restaurant = new Restaurant();
        restaurant.setVenueId(venueId);
        restaurant.setName(name);
        restaurant.setDistance(distance);
        restaurant.setPhone(phone);
        restaurant.setAddress(address);
        restaurant.setUrl(url);
        restaurant.setRating(rating);

        return restaurant;
    }
}

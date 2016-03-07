package com.marianilga.mypizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.marianilga.mypizza.data.model.Restaurant;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display short information of restaurants in a listView.
 */
public class ListAdapter extends ArrayAdapter {

    private Context mContext;
    private ListView listView;

    private LayoutInflater mLayoutInflater;

    private List<Restaurant> mEntries = new ArrayList<Restaurant>();

    public ListAdapter(Context context) {
        super(context, R.layout.item_list_restaurant);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        listView = (ListView) parent;

        RelativeLayout itemView;
        if (convertView == null) {
            itemView = (RelativeLayout) mLayoutInflater.inflate(
                    R.layout.item_list_restaurant, parent, false);

        } else {
            itemView = (RelativeLayout) convertView;
        }


        ImageView imageView = (ImageView)itemView.findViewById(R.id.listImage);
        TextView titleText = (TextView)itemView.findViewById(R.id.listTitle);
        TextView descriptionText = (TextView)itemView.findViewById(R.id.listDescription);

        Restaurant restaurant = mEntries.get(position);

        if (restaurant != null) {
            String title = restaurant.getName();
            titleText.setText(title);
            double distance = restaurant.getDistance();
            descriptionText.setText(mContext.getResources().getString(R.string.distance) + "  " + distance);
        }

        return itemView;
    }


    public void upDateEntries(List<Restaurant> entries) {

        // Check if there are any new items to add to the list.
        // No any item.
        if (entries.size() == 0) {
            // Remove a footer from the listView.
            if(listView.getFooterViewsCount() >0) {
                View v = listView.findViewWithTag("footer");
                if(v != null)  {
                    listView.removeFooterView(v);
                }
            }
            return;
        }

        // There are new items, add them to the listView.
        mEntries.addAll(entries);
        notifyDataSetChanged();
    }


    public void removeAllEntries(){
        mEntries.clear();
        notifyDataSetChanged();
    }


}
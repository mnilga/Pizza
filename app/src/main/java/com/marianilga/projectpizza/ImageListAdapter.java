package com.marianilga.projectpizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Masha on 04.03.2016.
 */
public class ImageListAdapter extends ArrayAdapter {

    private Context mContext;

    private LayoutInflater mLayoutInflater;

    private ArrayList<Restaurant> mEntries = new ArrayList<Restaurant>();

   // private final ImageDownloader mImageDownloader;

    public ImageListAdapter(Context context) {
        super(context, R.layout.item_list_restaurant);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // mImageDownloader = new ImageDownloader(context);
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

       // String imageUrl = mEntries.get(position).getContent().getSrc();
       // mImageDownloader.download(imageUrl, imageView);

        //String title = mEntries.get(position).getTitle().get$t();
        String title = "text title";
        titleText.setText(title);
        //String description = mEntries.get(position).getSummary().get$t();
        String description = "summary";
        if (description.trim().length() == 0) {
            description = "Sorry, no description for this image.";
        }
        descriptionText.setText(description);

        return itemView;
    }

    public void upDateEntries(ArrayList<Restaurant> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}
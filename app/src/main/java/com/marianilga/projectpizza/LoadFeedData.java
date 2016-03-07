package com.marianilga.projectpizza;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Masha on 04.03.2016.
 */
public class LoadFeedData extends AsyncTask<Void, Void,ArrayList<Map.Entry>> {

private final String mUrl =
        "http://picasaweb.google.com/data/feed/api/all?kind=photo&q=" +
        "sunset%20landscape&alt=json&max-results=20&thumbsize=144c";

private final ImageListAdapter mAdapter;

public LoadFeedData(ImageListAdapter adapter) {
        mAdapter = adapter;
        }

private InputStream retrieveStream(String url) {
       /* DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url);

        HttpResponse httpResponse = null;
        try {
        httpResponse = client.execute(httpGet);
        HttpEntity getResponseEntity = httpResponse.getEntity();
        return getResponseEntity.getContent();
        } catch (IOException e) {
        httpGet.abort();
        }*/
        return null;
        }

@Override
protected ArrayList<Map.Entry> doInBackground(Void... params) {
       /* InputStream source = retrieveStream(mUrl);
        Reader reader = null;
        try {
        reader = new InputStreamReader(source);
        } catch (Exception e) {
        return null;
        }
        Gson gson = new Gson();
        SearchResult result = gson.fromJson(reader,SearchResult.class);
        return result.getFeed().getEntry();*/

    return null;
        }

protected void onPostExecute(ArrayList<Map.Entry> entries) {
       // mAdapter.upDateEntries(entries);
        }
        }

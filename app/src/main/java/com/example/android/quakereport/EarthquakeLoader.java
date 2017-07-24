package com.example.android.quakereport;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mRequestUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mRequestUrl = url;
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(TextUtils.isEmpty(mRequestUrl) || mRequestUrl == null) {
            return null;
        }

        List<Earthquake> earthquakes = QueryUtils.fetchEarthQuakeData(mRequestUrl);

        return earthquakes;
    }

   // Override the onStartLoading() method to call forceLoad() which is a required step to actually trigger the loadInBackground() method to execute.
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

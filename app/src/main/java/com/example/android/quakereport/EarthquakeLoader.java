package com.example.android.quakereport;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
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

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}

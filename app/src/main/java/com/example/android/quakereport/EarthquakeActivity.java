/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    /** Adapter for the list of earthquakes */
    private EarthquakeAdapter mAdapter;

    private TextView mEmptyStateTextView;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        Log.v(LOG_TAG, "*******************onCreate() after setContentView()");

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.emptyView);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes the list of earthquakes as input
        mAdapter = new EarthquakeAdapter(EarthquakeActivity.this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri webpage = Uri.parse(currentEarthquake.getWebsite());

                // Create a new intent to view the earthquake URI
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

                // Send the intent to launch a new activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        Log.v(LOG_TAG, "*******************onCreate() before getLoadManager()");

        // First we need to specify an ID for our loader. This is only really relevant if
        // we were using multiple loaders in the same activity. We can choose any integer
        // number, so we choose the number 1.
        getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);

        Log.v(LOG_TAG, "*******************onCreate() after getLoadManager()");

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.v(LOG_TAG, "*******************Called onCreateLoader()");
        return new EarthquakeLoader(this, USGS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.v(LOG_TAG, "*******************Called onLoadFinished()");

        // To avoid the “No earthquakes found.” message blinking on the screen when the app
        // first launches, we can leave the empty state TextView blank, until the first load completes.
        // In the onLoadFinished callback method, we can set the text to be the string “No earthquakes
        // found.” It’s okay if this text is set every time the loader finishes because it’s not too
        // expensive of an operation. There’s always tradeoffs, and this user experience is better.
        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            // To test the empty state, you can temporarily comment out the line of code that
            // adds earthquake data to the adapter, which is the mAdapter.addAll(earthquakes) method call.
            // This will pretend like 0 results came back from the web server, and you should see
            // the empty state in the app.
            mAdapter.addAll(data);
        }
    }

    // We need onLoaderReset(), we're we're being informed that the data from our loader is no longer
    // valid. This isn't actually a case that's going to come up with our simple loader, but the correct
    // thing to do is to remove all the earthquake data from our UI by clearing out the adapter’s data set.
    // For example, when you press Back button on your device or you leave the app or the file, onLoaderReset
    // gets triggered because if the user leaves the app and comes back we want to referesh the data so they've
    // the latest earthquake information.
    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.v(LOG_TAG, "*******************Called onLoaderReset()");
        mAdapter.clear();
    }
}

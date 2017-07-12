package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An {@link EarthquakeAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Earthquake} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    /**
     * Constructs a new {@link EarthquakeAdapter}
     *
     * @param context of the app
     * @param earthquakes is the list of earthquakes, which is the data source of the adapter
     */
    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    /**
     * Returns a list item view that displays earthquake information about the earthquake at the given position
     * in the list of earthquakes.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, inflate a new list item layout.
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        // Find the earthquake object at the given position in the list of earthquakes
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView with view ID earthquake_list_item_magnitude
        TextView magnitudeTextView = (TextView) convertView.findViewById(R.id.earthquake_list_item_magnitude);
        // Display the magnitude of the current earthquake in that TextView
        magnitudeTextView.setText(currentEarthquake.getMagnitude());

        // Find the TextView with the view ID earthquake_list_item_location
        TextView locationTextView = (TextView) convertView.findViewById(R.id.earthquake_list_item_location);
        // Display the location of the current earthquake in that TextView
        locationTextView.setText(currentEarthquake.getLocation());

        // Find the TextView with the view ID earthquake_list_item_date
        TextView dateTextView = (TextView) convertView.findViewById(R.id.earthquake_list_item_date);
        // Display the date of the current earthquake in that TextView
        dateTextView.setText(currentEarthquake.getDate());

        // Return list item view that is now showing the appropriate data
        return convertView;
    }
}

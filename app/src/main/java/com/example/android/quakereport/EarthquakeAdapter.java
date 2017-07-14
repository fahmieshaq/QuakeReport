package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * An {@link EarthquakeAdapter} knows how to create a list item layout for each earthquake
 * in the data source (a list of {@link Earthquake} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = " of ";
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
        // Format magnitude decimal value of the current earthquake
        String magnitude = formatMagnitude(currentEarthquake.getMagnitude());
        // Display the formatted magnitude of the current earthquake in that TextView
        magnitudeTextView.setText(magnitude);

        // Get drawable background of the magnitude text view
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeTextView.getBackground();

        // Choose a color for the magnitude text view
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Assign a color to the the drawable background of magnitude text view
        magnitudeCircle.setColor(magnitudeColor);

        // Split original location string into two strings: primary location and location offset.
        // Since this logic is related to how the information is displayed in the UI, make the code changes
        // in the EarthquakeAdapter class. You could technically make the code change in the Earthquake class,
        // and ultimately it can come down to personal preference of the developer on where to put the code.
        // In this case, we prefer to store the original location String in the Earthquake class, and keep all
        // UI-related modifications to the earthquake data in the EarthquakeAdapter class. In the future, if our
        // designer wants to display the whole location string together in the UI, we can leave our Earthquake class
        // code as-is, and just modify the logic in the EarthquakeAdapter on how the location is displayed.
        String originalLocation = currentEarthquake.getLocation();
        String primaryLocation;
        String offsetLocation;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            offsetLocation = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            offsetLocation = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }


        // Find the TextView with the view ID earthquake_list_item_location
        TextView offsetLocationTextView = (TextView) convertView.findViewById(R.id.earthquake_list_item_offset_location);
        // Display the offset location of the current earthquake in that TextView
        offsetLocationTextView.setText(offsetLocation);

        // Find the TextView with the view ID earthquake_list_item_location
        TextView primaryLocationTextView = (TextView) convertView.findViewById(R.id.earthquake_list_item_primary_location);
        // Display the primary location of the current earthquake in that TextView
        primaryLocationTextView.setText(primaryLocation);

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find the TextView with the view ID earthquake_list_item_date
        TextView dateTextView = (TextView) convertView.findViewById(R.id.earthquake_list_item_date);
        String date = formatDate(dateObject);
        dateTextView.setText(date);

        // Find the TextView with the view ID earthquake_list_item_time
        TextView timeTextView = (TextView) convertView.findViewById(R.id.earthquake_list_item_time);
        String time = formatTime(dateObject);
        timeTextView.setText(time);

        // Return list item view that is now showing the appropriate data
        return convertView;
    }

    /**
     * Returns the formatted date string (i.e. "Jan 05, 2016") from a Date object
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormatter.format(dateObject);
    }

    /**
     * Returns the formatted time string (i.e. "4:30 PM") from a Date object
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
        return timeFormatter.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.4")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormatter = new DecimalFormat("0.0");
        return magnitudeFormatter.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeFloor = (int) Math.floor(magnitude);
        int magnitudeColorResourceId;

        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}

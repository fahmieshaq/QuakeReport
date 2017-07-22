package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray features = root.optJSONArray("features");
            for(int i = 0; i < features.length(); i++) {
                JSONObject feature = features.optJSONObject(i);
                JSONObject properties = feature.optJSONObject("properties");

                Double magnitude = properties.optDouble("mag");
                String location = properties.optString("place");
                long timeInMilliseconds = properties.optLong("time");
                String website = properties.optString("url");

                // Add earthquake data into earthquakes list
                earthquakes.add(new Earthquake(magnitude, location, timeInMilliseconds, website));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static URL createUrl(String string) {
        URL url = null;
        try {
            url = new URL(string);
        } catch (MalformedURLException e) {
            Log.e(EarthquakeActivity.LOG_TAG, "Incorrect request url", e);
        }
        return url;
    }

    public static String makeHttpsRequest(URL url) {
        StringBuilder jsonResponse = new StringBuilder();
        try {
            HttpsURLConnection connection = null;
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.connect();

            if(connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = null;
                inputStream = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                String line;

                while((line = in.readLine()) != null) {
                    jsonResponse.append(line);
                }
            }
        } catch (NullPointerException e) {
            Log.e(EarthquakeActivity.LOG_TAG, "NullPointerException", e);
        } catch (IOException e) {
            Log.e(EarthquakeActivity.LOG_TAG, "openConnection failed", e);
        }

        return jsonResponse.toString();
    }
}
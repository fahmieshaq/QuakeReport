package com.example.android.quakereport;

/**
 * An {@link Earthquake} object contains information related to a single earthquake.
 */
public class Earthquake {

    /** Magnitude of the earthquake */
    private double mMagnitude;

    /** Location of the earthquake */
    private String mLocation;

    /** Date of the earthquake */
    private long mTimeInMilliseconds;

    /** Date of the earthquake */
    private String mWebsite;

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location is the city location of the earthquake
     * @param timeInMilliseconds is the time in milliseconds (from the Epoch) when the earthquake happened
     */
    public Earthquake(double magnitude, String location, long timeInMilliseconds, String website) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mWebsite = website;
    }

    /**
     * Returns the magnitude of the earthquake.
     */
    public double getMagnitude() {
        return mMagnitude;
    }

    /**
     * Returns the location of the earthquake.
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Returns the date of the earthquake
     */
    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    /**
     * Returns the details web page of the earthquake
     */
    public String getWebsite() {
        return mWebsite;
    }
}

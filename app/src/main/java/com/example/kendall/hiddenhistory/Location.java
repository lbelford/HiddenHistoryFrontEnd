package com.example.kendall.hiddenhistory;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Owner on 4/19/2016.
 */
public class Location {

    private String name;
    private String description;
    private double latitude;
    private double longitude;

    private Location()
    {

    }

    public static Location loadLocation(JSONObject jLocation)
    {
        if(jLocation == null || jLocation.length() == 0)
        {
            return null;
        }

            Location l = new Location();
            try {
                l.name = jLocation.getString("name");
                l.description = jLocation.getString("description");
                l.latitude = jLocation.getDouble("latitude");
                l.longitude = jLocation.getDouble("longitude");
            } catch(JSONException e)
            {
                Log.e("LocationExceptions", e.getMessage());
            }
        return l;
    }

    @Override
    public String toString()
    {
        String str = "";

        str += "\n\t\tName: " + name + "\n";
        str += "\t\tDescription: " + description + "\n";
        str += "\t\tCoordinates: (" + latitude + ", " + longitude + ")\n";

        return str;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.description;
    }
}

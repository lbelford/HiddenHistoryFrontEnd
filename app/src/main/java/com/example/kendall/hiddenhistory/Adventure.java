package com.example.kendall.hiddenhistory;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Owner on 4/19/2016.
 */
public class Adventure {

    private Location currentLocation;
    private int minDistance;
    private int maxDistance;
    private int score;
    private boolean active;
    private boolean isOver;
    private String story;

    private Adventure()
    {

    }

    public static Adventure loadAdventure(JSONObject jAdventure)
    {
        if(jAdventure == null || jAdventure.length() == 0)
        {
            return null;
        }

        Adventure a = new Adventure();
        try {
            a.currentLocation = Location.loadLocation(jAdventure.getJSONObject("currentLocation"));
            a.minDistance = jAdventure.getInt("min_distance");
            a.maxDistance = jAdventure.getInt("max_distance");
            a.score = jAdventure.getInt("score");
            a.active = jAdventure.getBoolean("active");
            a.isOver = jAdventure.getBoolean("isOver");
            JSONObject storyObject = new JSONObject(jAdventure.getString("currentStory"));
            a.story = storyObject.getString("body");
        } catch(JSONException e)
        {
            Log.e("AdventureExceptions", e.getMessage());
        }
        return a;
    }

    @Override
    public String toString()
    {
        String str = "";

        str += "Your Adventure:\n";
        if(currentLocation != null) {
            str += "\tCurrentLocation: " + currentLocation.toString() + "\n";
        }
        str += "\tmin_distance: " + minDistance + ", max_distance: " + maxDistance + "\n";
        str += "\tscore: " + score;

        return str;
    }

    public String getDescription()
    {
       return this.currentLocation.getDescription();
    }

    public String getLocationName()
    {
        return this.currentLocation.getName();
    }

    public boolean getActive()
    {
        return this.active;
    }

    public boolean isOver()
    {
        return this.isOver;
    }

    public String getStory() {
        return this.story;
    }

    public Location getLocation () {
        return this.currentLocation;
    }
}

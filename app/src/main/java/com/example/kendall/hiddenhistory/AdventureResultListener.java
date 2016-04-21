package com.example.kendall.hiddenhistory;

/**
 * Created by Owner on 4/19/2016.
 */
public class AdventureResultListener implements AdventureResponse{

    public Adventure myAdventure;

    @Override
    public void processFinish(Adventure ad) {
        this.myAdventure = ad;
    }
}

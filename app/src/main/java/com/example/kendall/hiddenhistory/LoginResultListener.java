package com.example.kendall.hiddenhistory;

/**
 * Created by Owner on 4/19/2016.
 */
public class LoginResultListener implements AsyncResponse {

    public String token;

    @Override
    public void processFinish(String output) {
        this.token = output;
    }
}

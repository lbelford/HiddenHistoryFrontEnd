package com.example.kendall.hiddenhistory;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Owner on 4/3/2016.
 *
 * USE SECOND THREAD
 */
public class APICaller {
    private HTTPClient client;
    public final static String baseURL = "http://ec2-52-87-160-106.compute-1.amazonaws.com:9000/";
    public final static String apiPath = "api/hiddenhistory/";
    public final static String authPath = "auth/local/";

    public APICaller()
    {
        try {
            this.client = new HTTPClient(baseURL);
        } catch(Exception e) {
            System.out.println("Malformed URL");
        }
    }

    //Authorize an email and password, and return token if valid
    public String authorize(String email, String password)
    {
        String params = "email=" + email +"&password=" + password;
        String json;

        try {
            json = client.doPost(authPath, params);
            JSONArray jArray = processJSON(json);
            String token = jArray.getString(0);
            System.out.println(token);
            return token;
        } catch (Exception e) {
            System.out.println("IO Exception: " + e.getMessage());
            return "";
        }
    }

    private JSONArray processJSON(String JSONString)
    {
        try {
            JSONArray jsonArray = new JSONArray(JSONString);
            System.out.println("\n\njsonArray: " + jsonArray);
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.example.kendall.hiddenhistory;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

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
import java.util.concurrent.ExecutionException;

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
        LoginResultListener listener = new LoginResultListener();
        String token= "";

        try {
            token = new AuthorizeTask(listener).execute(params).get();
        } catch(InterruptedException e)
        {
            Log.d("Interrupted", e.getMessage());
        }
        catch (ExecutionException e)
        {
            Log.d("Concurrent Execution", e.getMessage());
        }

        //token = listener.token;
        Log.d("Authorize Method", "Token: " + token);


 /*       try {
            json = client.doPost(authPath, params);
            JSONArray jArray = processJSON(json);
            String token = jArray.getString(0);
            System.out.println(token);
            return token;
        } catch (Exception e) {
            System.out.println("IO Exception: " + e.getMessage());
            return "";
        } */
        return token;
    }

    //StartAdventure call to begin a HiddenHistory Adventure
    public Adventure startAdventure(String token, double latitude, double longitude,
                                    int min_distance, int max_distance, String email)
    {
        String params = "min_distance=" + min_distance + "&max_distance=" + max_distance + "&latitude=" +
                            latitude + "&longitude=" + longitude + "&email=" + email;
        Adventure myAdventure = null;

        AdventureResultListener listener = new AdventureResultListener();

        try {
            myAdventure = new StartAdventureTask(listener).execute(token, params).get();
        }
        catch(InterruptedException e)
        {
            Log.d("Interrupted", e.getMessage());
        }
        catch (ExecutionException e)
        {
            Log.d("Concurrent Execution", e.getMessage());
        }

        return myAdventure;
    }

    public Adventure getAdventure(String token)
    {
        AdventureResultListener listener = new AdventureResultListener();
        Adventure myAdventure = null;

        try{
            myAdventure = new GetAdventureTask(listener).execute(token).get();
        }
        catch(InterruptedException e)
        {
            Log.d("Interrupted", e.getMessage());
        }
        catch (ExecutionException e)
        {
            Log.d("Concurrent Execution", e.getMessage());
        }
        if(myAdventure != null) {
//            Log.d("AdventureGetGot", myAdventure.toString());
        }
        return myAdventure;
    }

    public String startNext(String token)
    {
        AdventureResultListener listener = new AdventureResultListener();
        String stuff = null;

        //String params = "email=" + email + "&choice=" + choice;

        try {
            stuff = new StartNextTask(listener).execute(token).get();
        }
        catch(InterruptedException e)
        {
            Log.d("Interrupted", e.getMessage());
        }
        catch (ExecutionException e)
        {
            Log.d("Concurrent Execution", e.getMessage());
        }
        return stuff;
    }

    public String findLocation(String token){
        LoginResultListener listener = new LoginResultListener();
        String choices= "";

        try {
            choices = new FoundLocationTask(listener).execute(token).get();
        } catch(InterruptedException e)
        {
            Log.d("Interrupted", e.getMessage());
        }
        catch (ExecutionException e)
        {
            Log.d("Concurrent Execution", e.getMessage());
        }
        return choices;
    }

    public int abort(String token)
    {
        AbortListener listener = new AbortListener();
        int score = 0;

        try {
            Integer s = new AbortTask(listener).execute(token).get();
            if (s != null)
                score = s.intValue();
        } catch(InterruptedException e)
        {
            Log.d("Interrupted", e.getMessage());
        }
        catch (ExecutionException e)
        {
            Log.d("Concurrent Execution", e.getMessage());
        }
        return score;
    }

    private JSONObject processJSON(String jsonString)
    {
        System.out.println(jsonString + "!!!");
        Log.d("HiddenHistory", "Here's our debug message");
        try {

            JSONObject jsonObject = new JSONObject(jsonString);
            //System.out.println("\n\njsonArray: " + jsonArray);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private class AuthorizeTask extends AsyncTask<String, String, String> {

        public AsyncResponse delegate = null;

        public AuthorizeTask(AsyncResponse delegate)
        {
            this.delegate = delegate;
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d("HiddenHistory", "Here's our debug message 2");
            try {
                String json = client.doPost(authPath, params[0]);
                Log.d("HiddenHistory", json + "***");
                JSONObject jObj = processJSON(json);
                System.out.println(jObj.toString());
                String token = jObj.getString("token");
                System.out.println(token);
                return token;
            } catch (Exception e) {
                System.out.println("IO Exception: " + e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(String token) {
            delegate.processFinish(token);
        }

    }

    private class GetAdventureTask extends AsyncTask<String, Adventure, Adventure> {

        public AdventureResponse delegate = null;

        public GetAdventureTask(AdventureResponse delegate)
        {
            this.delegate = delegate;
        }

        @Override
        protected Adventure doInBackground(String... params) {
            try{
                String json = client.doGet(apiPath + "getAdventure/", params[0]);
                Log.d("GetAdventure JSON Check", json);
                JSONObject jObj = processJSON(json);
                Adventure ad = Adventure.loadAdventure(jObj);
                return ad;
            }
            catch (Exception e) {
                System.out.println("IO Exception: " + e.getMessage());
                return null;

            }
        }

        protected void onPostExecute(Adventure ad) {
            delegate.processFinish(ad);
        }
    }

    private class StartAdventureTask extends AsyncTask<String, Adventure, Adventure> {

        public AdventureResponse delegate = null;

        public StartAdventureTask(AdventureResponse delegate) {
            this.delegate = delegate;
        }

        @Override
        protected Adventure doInBackground(String... params) {
            Log.d("HiddenHistory", "Here's our debug message 2");
            try {
                String json = client.doAuthorizedPost(apiPath + "startadventure/", params[0], params[1]);
                if (json == null)
                    Log.d("It's Null", "The JSON is null, ya doofus");
                System.out.println("JSON HERE: " + json);
                Log.d("StartAdventureJSON", "-------------" + json);
                JSONObject jObj = processJSON(json);
                if (!jObj.has("name")) {
                    Adventure ad = Adventure.loadAdventure(jObj);
                    return ad;
                }
                return null;
            } catch (Exception e) {
                System.out.println("IO Exception: " + e.getMessage());
                return null;
            }
        }

        protected void onPostExecute(Adventure ad) {
            delegate.processFinish(ad);
        }
    }

    private class StartNextTask extends AsyncTask<String, String, String>{

        public AdventureResponse delegate = null;

        public StartNextTask(AdventureResponse delegate)
        {
            this.delegate = delegate;
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                String json = client.doAuthorizedPost(apiPath + "startnext/", params[0], "");
                return json;
            }
            catch (Exception e) {
                System.out.println("IO Exception: " + e.getMessage());
                return null;

            }
        }

        protected void onPostExecute(Adventure ad) {
            delegate.processFinish(ad);
        }
    }

    private class FoundLocationTask extends AsyncTask<String, String, String> {

        public AsyncResponse delegate = null;

        public FoundLocationTask(AsyncResponse delegate) { this.delegate = delegate;}

        @Override
        protected String doInBackground(String... params) {
            try {
                String json = client.doAuthorizedPost(apiPath + "foundlocation/", params[0], "");
                return json;
            } catch (Exception e) {
                System.out.println("IO Exception: " + e.getMessage());
                return null;
            }
        }

        public void onPostExecute(String output) {delegate.processFinish(output);}
    }

    private class AbortTask extends AsyncTask<String, Integer, Integer> {


        public ScoreResponse delegate = null;

        public AbortTask(ScoreResponse delegate) { this.delegate = delegate; }

        @Override
        protected Integer doInBackground(String ... params) {

            Integer score = null;

            try {
                String score_string = client.doAuthorizedPost(apiPath + "abort/", params[0], "");
                score = Integer.parseInt(score_string);
            } catch (Exception e) {
                System.out.println("IO Exception: " + e.getMessage());
                return null;
            }

            return score;
        }

        protected void onPostExecute(Integer score) {delegate.processFinish(score);}
    }
}



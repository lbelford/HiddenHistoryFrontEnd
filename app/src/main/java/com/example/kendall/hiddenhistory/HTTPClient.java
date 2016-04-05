package com.example.kendall.hiddenhistory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple utility class that facilitates doing HTTP calls (GET, PUT, POST, and
 * DELETE) for use in querying servers.
 *
 * @author Daniel Chiquito
 *
 */
public class HTTPClient {

    private final String baseUrl;

    public HTTPClient(String url) throws MalformedURLException {
        if (url.charAt(url.length() - 1) != '/') {
            url = url + '/';
        }
        this.baseUrl = url;
    }

    public String doPost(String path, String urlParameters) throws IOException {
        HttpURLConnection connection;
        URL url = new URL(baseUrl + path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

        connection.setRequestProperty("Content-Length",
                Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        // Send request
        System.out.println("getting output stream");
        OutputStream wr = connection.getOutputStream();
        System.out.println("writing params");
        wr.write(urlParameters.getBytes());
        System.out.println("closing write");
        wr.close();

        // Get Response
        boolean failed = false;
        InputStream is;
        try {
            is = connection.getInputStream();
        } catch (IOException e) {
            failed = true;
            is = connection.getErrorStream();
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        if (failed) {
            //throw new MessagingManagerException(connection.getResponseCode()+": "+response.toString());
        }
        return response.toString();
    }

    public String doGet(String path) throws IOException {
        HttpURLConnection connection;
        URL url = new URL(baseUrl + path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Send request
        connection.connect();

        // Get Response
        boolean failed = false;
        InputStream is;
        try {
            is = connection.getInputStream();
        } catch (IOException e) {
            failed = true;
            is = connection.getErrorStream();
        }
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        if (failed) {
            //throw new MessagingManagerException(connection.getResponseCode()+": "+response.toString());
        }
        return response.toString();
    }

    public void doPut(String path, String urlParameters) throws IOException {
        HttpURLConnection connection;
        URL url = new URL(baseUrl + path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/raw");

        connection.setRequestProperty("Content-Length",
                Integer.toString(urlParameters.getBytes().length));
        connection.setRequestProperty("Content-Language", "en-US");

        connection.setUseCaches(false);
        connection.setDoOutput(true);

        // Send request
        OutputStream wr = connection.getOutputStream();
        wr.write(urlParameters.getBytes());
        wr.close();
        connection.connect();
        connection.getResponseCode();
    }

    public void doDelete(String path) throws IOException {
        HttpURLConnection connection;
        URL url = new URL(baseUrl + path);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        // Send request
        connection.connect();
        connection.getResponseCode();
    }
}

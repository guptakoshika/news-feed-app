package com.example.android.newfeedapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class OueryUtilis {
    private OueryUtilis() {
    }

    public static final String LOG_TAG = OueryUtilis.class.getSimpleName();

    public static List<Adapter> fetchEarthquakeData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Adapter> news = extractJson(jsonResponse);

        return news;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Adapter> extractJson(String JSON) {
        List<Adapter> listItem = new ArrayList<>();

        try {
            JSONObject jsonResponse = new JSONObject(JSON);
            JSONObject jsonResults = jsonResponse.getJSONObject("response");
            JSONArray jsonArray = jsonResults.getJSONArray("results");


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject currentnews = jsonArray.getJSONObject(i);

                JSONObject properties = currentnews.getJSONObject("fields");

                String StroyTitle = currentnews.getString("webTitle");

                String storyinfo = currentnews.getString("sectionName");

                String details = currentnews.getString("webPublicationDate");

                String url = currentnews.getString("webUrl");

                String author = properties.getString("byline");

                Adapter newsitem = new Adapter(StroyTitle, storyinfo, details,url,author);

                listItem.add(newsitem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listItem;
    }

    public static List<Adapter> fetchData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Adapter> news = extractJson(jsonResponse);

        return news;
    }
}

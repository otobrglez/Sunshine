package com.opalab.sunshine.app.utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetcher {
    private final String LOG_TAG = WeatherFetcher.class.getSimpleName();

    private String query;
    private String units;
    private String apiKey;

    public WeatherFetcher(String apiKey, String query) {
        this.apiKey = apiKey;
        this.query = query;
    }

    public WeatherFetcher(String apiKey, String query, String units) {
        this(apiKey, query);
        this.units = units;
    }

    public String[] fetch() {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String[] forecast = null;

        try {
            final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String QUERY_PARAM = "q";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String APPID_PARAM = "APPID";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, query)
                    .appendQueryParameter(FORMAT_PARAM, "json")
                    .appendQueryParameter(UNITS_PARAM, "metric")
                    .appendQueryParameter(DAYS_PARAM, "7") // Integer.toString(numDays)
                    .appendQueryParameter(APPID_PARAM, apiKey)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) return null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) buffer.append(line + "\n");
            if (buffer.length() == 0) return null;

            forecast = WeatherDataParser.getWeatherDataFromJson(buffer.toString(), 7, units);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error ", e);
            e.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return forecast;
    }
}

package com.opalab.sunshine.app;

import android.text.format.Time;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherDataParser {
    public static double getMaxTemperatureForDay(String weatherJsonStr, int dayIndex) throws JSONException {
        try {
            JSONObject reader = new JSONObject(weatherJsonStr);
            JSONArray list = reader.optJSONArray("list");
            JSONObject temp = list.getJSONObject(dayIndex).getJSONObject("temp");
            return temp.getDouble("max");
        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static String[] getWeatherDataFromJson(String weatherJsonStr, int numDays) throws JSONException {
        String[] results = new String[numDays];

        try {
            JSONObject reader = new JSONObject(weatherJsonStr);
            JSONArray list = reader.optJSONArray("list");

            /*
            Time dayTime = new Time();
            dayTime.setToNow();

            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
            dayTime = new Time();
            */

            System.out.println(list.length());

            for (int i = 0; i < numDays; i++) {
                String day, description, highAndLow;
                JSONObject dayForecast = list.getJSONObject(i);
                JSONObject weatherObject = dayForecast.getJSONArray("weather").getJSONObject(0);

                /* Day */


                /* Temp */
                JSONObject temperatureObject = dayForecast.getJSONObject("temp");
                long high = Math.round(temperatureObject.getDouble("max"));
                long low = Math.round(temperatureObject.getDouble("min"));

                /* Description */
                description = weatherObject.getString("description");

                /* High / Low */
                highAndLow = high + "/" + low;

                results[i] = description + " - " + highAndLow;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        }

        return results;
    }
}

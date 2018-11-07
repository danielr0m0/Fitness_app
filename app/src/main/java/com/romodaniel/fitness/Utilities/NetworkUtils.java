package com.romodaniel.fitness.Utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


/**
 * Created by Daniel on 7/27/2017.
 */

public final class NetworkUtils {
    public static final String TAG = "networkutils";
    public static final String STATIC_NEWS_URL =
            "https://api.nutritionix.com/v1_1/search/";
    public static final String NEWS_BASE_URL = STATIC_NEWS_URL;

    // TODO insert apikey below
    public static final String appKey = "d25dd44a9d60b2a60da02aa028229157";
    public static final String appId= "497f09c8";
    public static final String results = "0:15";
    public static final String cal_max = "5000";
    public static final String cal_min = "50";
    public static final String fields = "item_name,brand_name,nf_calories";

    public static String PARAM_RESULTS = "results";
    public static String PARAM_CAL_MIN= "cal_min";
    public static String PARAM_CAL_MAX = "cal_max";
    public static String PARAM_FIELDS = "fields";
    public static String PARAM_APPID = "appId";
    public static String PARAM_APIKEY = "appKey";




    public static URL buildUrl(String search) {
        String NEW_URL = NEWS_BASE_URL.concat("phrase=" + search);
        Uri buildUri = Uri.parse(NEW_URL).buildUpon()
                .appendQueryParameter(PARAM_RESULTS, results)
                .appendQueryParameter(PARAM_CAL_MIN, cal_min)
                .appendQueryParameter(PARAM_CAL_MAX, cal_max)
                .appendQueryParameter(PARAM_FIELDS, fields)
                .appendQueryParameter(PARAM_APPID, appId)
                .appendQueryParameter(PARAM_APIKEY, appKey)
                .build();

        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static ArrayList<FoodItems> parseJSON(String json) throws JSONException {
        ArrayList<FoodItems> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray hits = main.getJSONArray("hits");

        String itemname = null;
        String brand_name = null;
        String servingsize = null;
        String servingqty = null;
        String calories = null;

        for (int i = 0; i < hits.length(); i++) {
            JSONObject hit = hits.getJSONObject(i);
            JSONObject hitField = hit.getJSONObject("fields");

            itemname = hitField.getString("item_name");
            brand_name = hitField.getString("brand_name");
            servingsize = hitField.getString("nf_serving_size_unit");
            servingqty = hitField.getString("nf_serving_size_qty");
            calories = hitField.getString("nf_calories");

            Log.d(TAG, itemname + " " + brand_name + " " + servingqty + " " + servingsize + " " + calories);

            result.add(new FoodItems(itemname, calories, brand_name,servingqty, servingsize));
        }
        return result;

    }
}
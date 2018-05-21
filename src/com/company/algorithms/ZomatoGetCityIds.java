package com.company.algorithms;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.*;
import org.json.JSONException;
import org.json.JSONObject;

public class ZomatoGetCityIds {

    private static String[] cities = {"Bangalore", "Mumbai", "Pune", "Delhi", "Chennai"};

    public static JSONObject getCityIds() {
        System.out.println ("OUTPUT from ZOMATO GET CITY API :");
        ArrayList<String> citiesList = new ArrayList<String> ();
        citiesList.addAll (Arrays.asList (cities));
        JSONObject cityNode = new JSONObject ();
        citiesList.forEach (
                city -> {
                    try {
                        URL url =
                                new URL (("https://developers.zomato.com/api/v2.1/cities").concat ("?q=" + city));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection ();
                        conn.setDoOutput (true);
                        conn.setRequestMethod ("GET");
                        conn.setRequestProperty ("Accept", "application/json");
                        conn.setRequestProperty ("user-key", "");
                        failureHttpMessage (conn);
                        BufferedReader br = new BufferedReader (new InputStreamReader ((conn.getInputStream ())));
                        String output = br.readLine ();
                        ObjectMapper mapper = new ObjectMapper ();
                        JsonNode actualObj = mapper.readTree (output);
                        cityNode.put (city, actualObj.get ("location_suggestions").get (0).get ("id"));
                        conn.disconnect ();
                    } catch (MalformedURLException e) {
                        e.printStackTrace ();
                    } catch (IOException e) {
                        e.printStackTrace ();
                    } catch (JSONException e) {
                        e.printStackTrace ();
                    }
                });
        System.out.println (cityNode);
        return cityNode;
    }

    private static void failureHttpMessage(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode () != 200) {
            throw new RuntimeException ("Failed : HTTP error code : " + conn.getResponseCode ());
        }
    }
}

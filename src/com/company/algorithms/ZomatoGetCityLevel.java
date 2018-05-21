package com.company.algorithms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ZomatoGetCityLevel {
    private static String[] cities = {"Bangalore", "Mumbai", "Pune", "Delhi", "Chennai"};

    public JSONObject getCityLevelRestaurantCounts(JSONObject citiesNode) throws IOException {
        int index = 0;
        JSONObject cityNode = new JSONObject ();
        while (index != citiesNode.length ()) {
            try {
                JSONObject collectionNode = new JSONObject ();
                URL url =
                        new URL (
                                ("https://developers.zomato.com/api/v2.1/search")
                                        .concat ("?entity_type=city&entity_id=" + citiesNode.get (cities[index])));
                System.out.println (url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection ();
                conn.setDoOutput (true);
                conn.setRequestMethod ("GET");
                conn.setRequestProperty ("Accept", "application/json");
                conn.setRequestProperty ("user-key", "");
                failureHttpMessage (conn);
                BufferedReader br = new BufferedReader (new InputStreamReader ((conn.getInputStream ())));
                String output = br.readLine ();
                System.out.println ("Output from Collections API .... \n");
                ObjectMapper mapper = new ObjectMapper ();
                JsonNode actualObj = mapper.readTree (output);
                System.out.println (actualObj);
                cityNode.put (cities[index], actualObj.get ("results_found"));
                System.out.println (cityNode);
                conn.disconnect ();
            } catch (MalformedURLException e) {
                e.printStackTrace ();
            } catch (IOException e) {
                e.printStackTrace ();
            } catch (JSONException e) {
                e.printStackTrace ();
            }
            index++;
        }
        return cityNode;
    }

    private static void failureHttpMessage(HttpURLConnection conn) throws IOException {
        if (conn.getResponseCode () != 200) {
            throw new RuntimeException ("Failed : HTTP error code : " + conn.getResponseCode ());
        }
    }
}

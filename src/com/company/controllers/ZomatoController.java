package com.company.controllers;

import com.company.algorithms.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Scanner;

public class ZomatoController {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner (System.in);
        System.out.println ("1. Get city with city ids :");
        System.out.println ("2. Get collection ids count associated with city ids :");
        System.out.println ("3. Get restaurant counts as per city :");
        int value = sc.nextInt ();
        switch (value) {
            case 1:
                getCityIDNodes ();
                return;
            case 2:
                getCityCollectionIDs ();
                return;
            case 3:
                getCityLevelRestaurantCounts ();
                return;
        }
    }

    private static void getCityLevelRestaurantCounts() throws IOException {
        ZomatoGetCityLevel zomatoCollection = new ZomatoGetCityLevel ();
        zomatoCollection.getCityLevelRestaurantCounts (getCityIDNodes ());
    }

    private static void getCityCollectionIDs() throws IOException {
        ZomatoGetCollectionIds zomatoCollection = new ZomatoGetCollectionIds ();
        zomatoCollection.getCollectionIds (getCityIDNodes ());
    }

    private static JSONObject getCityIDNodes() {
        ZomatoGetCityIds zomato = new ZomatoGetCityIds ();
        return zomato.getCityIds ();
    }
}

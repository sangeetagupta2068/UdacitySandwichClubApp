package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json){

        Sandwich sandwich;

        try {

            JSONObject sandwichJSON = new JSONObject(json);

            JSONObject sandwichName = sandwichJSON.getJSONObject("name");
            String sandwichMainName = sandwichName.getString("mainName");

            List<String> sandwichAlsoKnownAs = new ArrayList<>();
            JSONArray sandwichJSONAlsoKnownAs = sandwichName.getJSONArray("alsoKnownAs");
            for(int count = 0; count<sandwichJSONAlsoKnownAs.length();count++){
                sandwichAlsoKnownAs.add(sandwichJSONAlsoKnownAs.getString(count));
            }

            String sandwichPlaceOfOrigin = sandwichJSON.getString("placeOfOrigin");

            String sandwichDescription = sandwichJSON.getString("description");

            String image = sandwichJSON.getString("image");

            List<String> sandwichIngredients = new ArrayList<>();
            JSONArray sandwichJSONIngredients = sandwichJSON.getJSONArray("ingredients");
            for(int count = 0; count<sandwichJSONAlsoKnownAs.length();count++){
                sandwichIngredients.add(sandwichJSONIngredients.getString(count));
            }

            sandwich = new Sandwich(sandwichMainName,sandwichAlsoKnownAs,sandwichPlaceOfOrigin,sandwichDescription,image,sandwichIngredients);

        } catch (JSONException e) {
            e.printStackTrace();
            sandwich = new Sandwich();
        }

        return sandwich;
    }
}

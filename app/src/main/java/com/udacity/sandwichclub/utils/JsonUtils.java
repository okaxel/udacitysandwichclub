package com.udacity.sandwichclub.utils;

// To identify my work more easily I made remarks everywhere before or around my modifications.

import com.udacity.sandwichclub.model.Sandwich;

/* Import section for JSON parsing. */
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
/* Finished import section for JSON parsing. */

public class JsonUtils {

    /*

    The parseSandwichJson() method must be a bit appended, because null isn't the best output for
    further workflow.

    First of all we have to throw some exception to the masses if something goes wrong.

     */
    public static Sandwich parseSandwichJson(String json) {

        // Try to JSONify our great sandwich and check if Mr. JSON is satisfied or not.
        try {
            JSONObject sandwichJson = new JSONObject(json);

            // First of all I handle the names.
            JSONObject sandwichName = sandwichJson.getJSONObject("name");
            String mMainName = sandwichName.getString("mainName");
            JSONArray akaNames = sandwichName.getJSONArray("alsoKnownAs");
            List<String> mAlsoKnownAs = new ArrayList<>();
            for (int i=0;i<akaNames.length();i++) {
                mAlsoKnownAs.add(akaNames.getString(i));
            }

            // Now I handle everything else.
            String mPlaceOfOrigin = sandwichJson.getString("placeOfOrigin");
            String mDescription = sandwichJson.getString("description");
            String mImage = sandwichJson.getString("image");
            JSONArray ingredientsArray = sandwichJson.getJSONArray("ingredients");
            List<String> mIngredients = new ArrayList<>();
            for (int i=0;i<ingredientsArray.length();i++) {
                mIngredients.add(ingredientsArray.getString(i));
            }
            return new Sandwich(mMainName,mAlsoKnownAs,mPlaceOfOrigin,mDescription,mImage,mIngredients);

        } catch (JSONException e) {
            Log.e("Sandwich Club Exercise",e.getLocalizedMessage());
            return null;
        }
    }
}

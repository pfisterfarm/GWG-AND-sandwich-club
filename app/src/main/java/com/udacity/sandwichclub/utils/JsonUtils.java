package com.udacity.sandwichclub.utils;

import android.widget.Toast;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        Sandwich result = null;
        try {
            JSONObject parseDetails = new JSONObject(json);
            JSONObject parseName = parseDetails.getJSONObject("name");

            result = new Sandwich();
            result.setMainName(parseName.optString("mainName"));
            result.setImage(parseDetails.optString("image"));

            List<String> alsoKnownList = new ArrayList<>();
            JSONArray alsoKnownArray = parseName.getJSONArray("alsoKnownAs");
            for (int i = 0; i < alsoKnownArray.length(); i++) {
                alsoKnownList.add(alsoKnownArray.getString(i));
            }
            result.setAlsoKnownAs(alsoKnownList);

            List<String> ingredientsList = new ArrayList<>();
            JSONArray ingredientsArray = parseDetails.getJSONArray("ingredients");
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredientsList.add(ingredientsArray.getString(i));
            }
            result.setIngredients(ingredientsList);

            String descString = parseDetails.optString("description");
            if (descString.length() > 0)
                result.setDescription(descString);
            else
                result.setDescription("N/A");

            String originString = parseDetails.optString("placeOfOrigin");
            if (originString.length() > 0)
                result.setPlaceOfOrigin(originString);
            else
                result.setPlaceOfOrigin("N/A");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}

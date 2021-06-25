package com.stephenklop.breakingbadassesment.data;

import android.util.Log;

import com.stephenklop.breakingbadassesment.domain.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterParse {

    public static Character jsonObjectToObject(JSONObject jsonCharacterObject) throws JSONException {

        // Load all attributes and return a Java object
        int id = jsonCharacterObject.getInt("char_id");
        String name = jsonCharacterObject.getString("name");
        String birthday = jsonCharacterObject.getString("birthday");
        String img = jsonCharacterObject.getString("img");
        String nickname = jsonCharacterObject.getString("nickname");
        String potrayed = jsonCharacterObject.getString("portrayed");
        JSONArray jsonOccupation = jsonCharacterObject.getJSONArray("occupation");
        JSONArray jsonAppearance = jsonCharacterObject.getJSONArray("appearance");

        // Fix strings
        String status = jsonCharacterObject.getString("status");

        // Load Occupation, appearance and category and store in ArrayList
        String occupation = "";
        String appearance = "";

        for (int i = 0; i < jsonOccupation.length(); i++) {
            occupation = occupation + jsonOccupation.get(i).toString() + ", ";
        }

        for (int i = 0; i < jsonAppearance.length(); i++) {
            appearance = String.valueOf(jsonAppearance.length());
        }

        return new Character(id, name, birthday, img, status, nickname, potrayed, occupation, appearance);
    }
}

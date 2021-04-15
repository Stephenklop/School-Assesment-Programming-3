package com.stephenklop.breakingbadassesment.data;

import com.stephenklop.breakingbadassesment.domain.Character;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterParse {
    public static Character jsonObjectToObject(JSONObject jsonCharacterObject) throws JSONException {

        // Load all attributes and return a Java object
        int id = jsonCharacterObject.getInt("id");
        String name = jsonCharacterObject.getString("name");
        String birthday = jsonCharacterObject.getString("birthday");
        String img = jsonCharacterObject.getString("img");
        String status = jsonCharacterObject.getString("status");
        String nickname = jsonCharacterObject.getString("nickname");
        String potrayed = jsonCharacterObject.getString("portrayed");

        // Load Occupation, appearance and category and store in ArrayList
        ArrayList<String> occupation = new ArrayList<>();
        ArrayList<Integer> appearance = new ArrayList<>();

        JSONArray jsonOccupation = jsonCharacterObject.getJSONArray("occupation");
        JSONArray jsonAppearance = jsonCharacterObject.getJSONArray("appearance");

        for (int i = 0; i < jsonOccupation.length(); i++) {
            String occupationName = jsonOccupation.getJSONObject(i).getString("name");
            occupation.add(occupationName);
        }

        for (int i = 0; i < jsonAppearance.length(); i++) {
            int appearanceInt = jsonAppearance.getJSONObject(i).getInt("name");
            appearance.add(appearanceInt);
        }

        return new Character(id, name, birthday, img, status, nickname, potrayed, occupation, appearance);
    }
}

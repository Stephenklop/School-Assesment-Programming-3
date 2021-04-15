package com.stephenklop.breakingbadassesment.data;

import com.stephenklop.breakingbadassesment.domain.Character;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIService {
    HttpURLConnection conn;

    public APIService() {}

    private void connect(String url) throws IOException {
        URL connUrl = new URL(url);
        conn = (HttpURLConnection) connUrl.openConnection();
    }

    private void disconnect() {
        conn.disconnect();
    }

    public List<Character> getAllCharacters() {
        List<Character> result = new ArrayList<>();

        try {
            connect("https://breakingbadapi.com/api/characters");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(inputLine);
                result.add(CharacterParse.jsonObjectToObject(jsonObject));
            }

        } catch (IOException | JSONException e) {
            // Handle error
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return result;
    }
}

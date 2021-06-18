package com.stephenklop.breakingbadassesment.data;

import com.stephenklop.breakingbadassesment.domain.Character;
import com.stephenklop.breakingbadassesment.domain.Quote;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APIService {
    private final String BASEURL;
    HttpURLConnection conn;

    public APIService() {
        BASEURL = "https://breakingbadapi.com/api";
    }

    private void connect(String url) throws IOException {
        URL connUrl = new URL(BASEURL + url);
        conn = (HttpURLConnection) connUrl.openConnection();
    }

    private void disconnect() {
        conn.disconnect();
    }

    public List<Character> getAllCharacters() {
        List<Character> result = new ArrayList<>();

        try {
            connect("/characters");
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine()) != null) {
                JSONArray jsonArray = new JSONArray(inputLine);
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.add(CharacterParse.jsonObjectToObject((JSONObject) jsonArray.get(i)));
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Quote> getQuoteByAuthor(String authorName) {
        List<Quote> result = new ArrayList<>();

        try {
            connect("/quotes?author=" + authorName);
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while((inputLine = in.readLine()) != null) {
                JSONArray jsonArray = new JSONArray(inputLine);
                for (int i = 0; i < jsonArray.length(); i++) {
                    result.add(QuoteParse.jsonObjectToObject((JSONObject) jsonArray.get(i)));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}

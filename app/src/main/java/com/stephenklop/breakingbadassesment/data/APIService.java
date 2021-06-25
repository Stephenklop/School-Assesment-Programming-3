package com.stephenklop.breakingbadassesment.data;

import android.util.Log;

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
    private HttpURLConnection conn;
    private final String LOG_TAG = this.getClass().getSimpleName();

    public APIService() {
        BASEURL = "https://breakingbadapi.com/api";
    }

    private void connect(String url) throws IOException {
        Log.d(LOG_TAG, "Create API connection");
        URL connUrl = new URL(BASEURL + url);
        conn = (HttpURLConnection) connUrl.openConnection();
    }

    private void disconnect() {
        Log.d(LOG_TAG, "Disconnect API connection");
        conn.disconnect();
    }

    public List<Character> getAllCharacters() {
        Log.d(LOG_TAG, "Get all characters API call");
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

        System.out.println("Character results: " + result);

        return result;
    }

    public List<Quote> getQuoteByAuthor(String authorName) {
        Log.d(LOG_TAG, "Get all quotes by author API call");
        List<Quote> result = new ArrayList<>();

        try {
            connect("/quote?author=" + authorName);
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

        System.out.println("Quotes results: " + result);

        return result;
    }
}

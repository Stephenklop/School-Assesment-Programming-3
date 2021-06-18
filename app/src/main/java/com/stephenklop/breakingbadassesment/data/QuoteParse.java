package com.stephenklop.breakingbadassesment.data;

import com.stephenklop.breakingbadassesment.domain.Quote;

import org.json.JSONException;
import org.json.JSONObject;

public class QuoteParse {
    public static Quote jsonObjectToObject(JSONObject jsonQuoteObject) throws JSONException {

        // Load all attributes and return a Java object
        int id = jsonQuoteObject.getInt("quote_id");
        String quote = jsonQuoteObject.getString("quote");
        String author = jsonQuoteObject.getString("author");
        String series = jsonQuoteObject.getString("series");

        return new Quote(id, quote, author, series);
    }
}

package com.example.ispitnizadatak_animeapi.dtos;

import android.util.Log;

import com.example.ispitnizadatak_animeapi.formatters.DateFormatter;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Review {
    private String url = null;
    private String date = null;
    private User user;
    private int score;
    private String review;


    public Review(String jsonString) {
        try {
            this.url = (JsonPath.read(jsonString, "$.url"));
            this.score = (JsonPath.read(jsonString, "$.score"));
            this.review = (JsonPath.read(jsonString, "$.review"));
            this.date = (JsonPath.read(jsonString, "$.date"));
            this.user = new User(JsonPath.read(jsonString, "$.user"));
        } catch (Exception e) {
            Log.d("Parse error", Objects.requireNonNull(e.getMessage()));
        }
    }

    public String getDate() {
        return DateFormatter.formatISO8601Date(this.date);
    }

    public String getUrl() {
        return url;
    }

    public String getScore() {
        return Integer.toString(score);
    }

    public User getUser() {
        return user;
    }

    public String getReview() {
        return review;
    }

    public static ArrayList<Review> parseJSONArray(JSONObject json) {
        ArrayList<Review> reviewList = new ArrayList<>();
        try {
            JSONArray jsonArray = json.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                Review review = new Review(jsonArray.getString(i));
                reviewList.add(review);
            }
        } catch (Exception e) {
            Log.d("Parsing short", Objects.requireNonNull(e.getMessage()));
        }
        return reviewList;
    }
}

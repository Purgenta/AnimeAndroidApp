package com.example.ispitnizadatak_animeapi.dtos;

import android.util.Log;

import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class AnimePost {
    private String image_url;
    private String title;
    private Double score;
    private int anime_id;


    public int getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(int anime_id) {
        this.anime_id = anime_id;
    }


    public AnimePost(String jsonString) {
        try {
            this.setAnime_id(JsonPath.read(jsonString, "$.mal_id"));
            this.setTitle(JsonPath.read(jsonString, "$.title"));
            this.setImage_url(JsonPath.read(jsonString, "$.images.webp.image_url"));
            this.setScore(JsonPath.read(jsonString, "$.score"));
        } catch (Exception e) {
            Log.d("Parse error", Objects.requireNonNull(e.getMessage()));
        }
    }

    public static ArrayList<AnimePost> parseJSONArray(JSONObject json) {
        ArrayList<AnimePost> animeList = new ArrayList<>();
        try {
            JSONArray jsonArray = json.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                AnimePost anime = new AnimePost(jsonArray.getString(i));
                animeList.add(anime);
            }
        } catch (Exception e) {
            Log.d("Parsing short", Objects.requireNonNull(e.getMessage()));
        }
        return animeList;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String small_image_url) {
        this.image_url = small_image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScore() {
        String scoreString;
        if (score != null) scoreString = score + "";
        else scoreString = "No score yet";

        return String.format("%s", scoreString);
    }

    public void setScore(Double score) {
        this.score = score;
    }

}

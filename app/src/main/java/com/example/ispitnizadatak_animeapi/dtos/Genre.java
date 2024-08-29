package com.example.ispitnizadatak_animeapi.dtos;

import android.util.Log;


import com.jayway.jsonpath.JsonPath;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class Genre {
    private final int id;
    private final String name;

    public Genre(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String jsonString) {
        this.id = JsonPath.read(jsonString, "$.id");
        this.name = JsonPath.read(jsonString, "$.name");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<Genre> parseJSONArray(JSONObject json) {
        ArrayList<Genre> genres = new ArrayList<>();
        try {
            JSONArray jsonArray = json.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                Genre genre = new Genre(jsonArray.getString(i));
                genres.add(genre);
            }
        } catch (Exception e) {
            Log.d("Parsing error", Objects.requireNonNull(e.getMessage()));
        }
        return genres;
    }

}

package com.example.ispitnizadatak_animeapi.dtos;

import com.jayway.jsonpath.JsonPath;

import java.util.LinkedHashMap;

public class User {
    private final String username;
    private final String url;


    public User(String username, String url) {
        this.username = username;
        this.url = url;
    }

    public String getUsername() {
        if (username == null) return "Anonymous";
        return username;
    }

    public String getUrl() {
        return url;
    }

    public User(LinkedHashMap json) {
        this.username = JsonPath.read(json, "$.username");
        this.url = JsonPath.read(json, "$.images.jpg.image_url");
    }
}

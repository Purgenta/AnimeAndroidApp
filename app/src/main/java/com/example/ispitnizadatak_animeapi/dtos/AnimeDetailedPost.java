package com.example.ispitnizadatak_animeapi.dtos;

import android.util.Log;

import androidx.annotation.NonNull;

import com.jayway.jsonpath.JsonPath;


import java.util.Objects;

public class AnimeDetailedPost extends AnimePost {
    private int episodes;
    private String synopsis;
    private String embedUrl;
    private int viewers;


    public String getEpisodes() {
        if (episodes == 0) {
            return "No episodes yet";
        } else {
            return String.format("%s", this.episodes);
        }
    }

    private void setEpisodes(int episodes) {
        this.episodes = episodes;
    }


    public String getSynopsis() {
        if (this.synopsis == null || this.synopsis.isEmpty()) {
            return "No synopsis give for the anime yet.";
        } else return synopsis;
    }

    public String getViewers() {
        if (episodes == 0) return "No viewers";
        return String.format("%s", this.viewers);
    }

    private void setViewers(int viewers) {
        this.viewers = viewers;
    }

    private void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    private void setEmbedUrl(String embedUrl) {
        this.embedUrl = embedUrl;
    }

    public String getEmbedUrl() {
        return embedUrl;
    }

    public AnimeDetailedPost(@NonNull String jsonString) {
        super(jsonString);
        try {
            this.setEpisodes(JsonPath.read(jsonString, "$.episodes"));
            this.setSynopsis(JsonPath.read(jsonString, "$.synopsis"));
            this.setViewers(JsonPath.read(jsonString, "$.members"));
            this.setEmbedUrl(JsonPath.read(jsonString, "$.trailer.embed_url"));
        } catch (Exception e) {
            Log.d("Parsing details error", Objects.requireNonNull(e.getMessage()));
        }
    }
}

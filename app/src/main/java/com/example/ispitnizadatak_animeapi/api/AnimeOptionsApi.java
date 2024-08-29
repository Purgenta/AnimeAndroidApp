package com.example.ispitnizadatak_animeapi.api;

import android.os.Message;

import androidx.annotation.NonNull;

import com.example.ispitnizadatak_animeapi.dtos.Genre;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeGenresCallback;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeOptionsApi;
import com.example.ispitnizadatak_animeapi.requests.GetRequest;
import com.example.ispitnizadatak_animeapi.requests.ReadJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AnimeOptionsApi implements IAnimeOptionsApi {
    private final String baseUrl = "https://api.jikan.moe/v4/genres/anime";

    @Override
    public void getGenres(IAnimeGenresCallback callback) {
        GetRequest.getJSON(baseUrl, new ReadJson() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                String jsonResponse = getJson();
                try {
                    JSONObject response = new JSONObject(jsonResponse);
                    ArrayList<Genre> genres = Genre.parseJSONArray(response);
                    callback.onSuccess(genres);
                } catch (JSONException e) {
                    callback.onError(e);
                }
            }
        });
    }
}

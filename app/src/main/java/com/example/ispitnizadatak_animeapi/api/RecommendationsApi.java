package com.example.ispitnizadatak_animeapi.api;

import android.os.Message;

import androidx.annotation.NonNull;

import com.example.ispitnizadatak_animeapi.dtos.AnimePost;
import com.example.ispitnizadatak_animeapi.interfaces.api.IRecommendationsApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.IRecommendationsApiCallback;
import com.example.ispitnizadatak_animeapi.requests.GetRequest;
import com.example.ispitnizadatak_animeapi.requests.ReadJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecommendationsApi implements IRecommendationsApi {
    private final String baseUrl = "https://api.jikan.moe/v4/anime/%s/recommendations";

    @Override()
    public void getData(IRecommendationsApiCallback callback, int animeId) {
        String url = String.format(baseUrl, animeId);
        GetRequest.getJSON(url, new ReadJson() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    String json = getJson();
                    JSONObject response = new JSONObject(json);
                    JSONArray data = response.getJSONArray("data");
                    ArrayList<AnimePost> animeList = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject structure = data.getJSONObject(i);
                        JSONObject anime = structure.getJSONObject("entry");
                        animeList.add(new AnimePost(anime.toString()));
                    }
                    callback.onRecommendationsData(animeList);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        });
    }
}

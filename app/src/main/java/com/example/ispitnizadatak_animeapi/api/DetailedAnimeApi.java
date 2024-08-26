package com.example.ispitnizadatak_animeapi.api;

import android.annotation.SuppressLint;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.ispitnizadatak_animeapi.dtos.AnimeDetailedPost;
import com.example.ispitnizadatak_animeapi.dtos.Review;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeDetailsApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeDetailsApiCallback;
import com.example.ispitnizadatak_animeapi.interfaces.api.IReviewsApiCallback;
import com.example.ispitnizadatak_animeapi.requests.GetRequest;
import com.example.ispitnizadatak_animeapi.requests.ReadJson;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONObject;

import java.util.ArrayList;

public class DetailedAnimeApi implements IAnimeDetailsApi {

    @Override
    public void getAnimeById(int animeId, IAnimeDetailsApiCallback callback) {

        GetRequest.getJSON(String.format("https://api.jikan.moe/v4/anime/%s/full", animeId), new ReadJson() {
            @SuppressLint("SetTextI18n")
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    String jsonResponse = getJson();
                    JSONObject json = new JSONObject(jsonResponse);
                    JSONObject data = null;
                    if (json.has("data")) {
                        data = json.getJSONObject("data");
                    }
                    assert data != null;
                    callback.onSuccess(new AnimeDetailedPost(data.toString()));
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        });
    }

    @Override
    public void getAnimeReviewsById(int animeId, int page, IReviewsApiCallback callback) {
        GetRequest.getJSON(String.format("https://api.jikan.moe/v4/anime/%s/reviews?page=%s", animeId, page), new ReadJson() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    String response = getJson();

                    JSONObject json = new JSONObject(response);
                    ArrayList<Review> reviews = Review.parseJSONArray(json);
                    callback.onSuccess(reviews, true);
                } catch (Exception e) {
                    callback.onError(e);
                }

            }
        });
    }
}

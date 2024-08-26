package com.example.ispitnizadatak_animeapi.interfaces.api;

import com.example.ispitnizadatak_animeapi.dtos.AnimePost;

import java.util.ArrayList;

public interface IRecommendationsApiCallback {
    void onRecommendationsData(ArrayList<AnimePost> data);

    void onError(Exception e);
}

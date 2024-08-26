package com.example.ispitnizadatak_animeapi.interfaces.api;

public interface IRecommendationsApi {
    void getData(IRecommendationsApiCallback callback, int animeId);
}

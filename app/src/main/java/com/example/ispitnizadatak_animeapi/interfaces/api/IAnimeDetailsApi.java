package com.example.ispitnizadatak_animeapi.interfaces.api;

public interface IAnimeDetailsApi {
    void getAnimeById(int animeId, IAnimeDetailsApiCallback callback);

    void getAnimeReviewsById(int animeId, int page, IReviewsApiCallback callback);
}

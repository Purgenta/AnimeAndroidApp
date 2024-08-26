package com.example.ispitnizadatak_animeapi.interfaces.api;

import com.example.ispitnizadatak_animeapi.dtos.AnimeDetailedPost;

public interface IAnimeDetailsApiCallback {
    void onSuccess(AnimeDetailedPost data);

    void onError(Exception e);

}

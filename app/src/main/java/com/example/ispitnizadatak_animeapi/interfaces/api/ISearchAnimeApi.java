package com.example.ispitnizadatak_animeapi.interfaces.api;

import com.example.ispitnizadatak_animeapi.models.SearchAnimeModel;

public interface ISearchAnimeApi {
    void getData(ISearchAnimeApiCallback callback, SearchAnimeModel model);

}


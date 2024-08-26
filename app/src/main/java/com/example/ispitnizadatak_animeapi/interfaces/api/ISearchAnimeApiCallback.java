package com.example.ispitnizadatak_animeapi.interfaces.api;

import com.example.ispitnizadatak_animeapi.dtos.AnimePost;

import java.util.ArrayList;

public interface ISearchAnimeApiCallback {
    void onSearchResult(ArrayList<AnimePost> animeList, boolean hasNextPage);

    void onError(Exception e);
}

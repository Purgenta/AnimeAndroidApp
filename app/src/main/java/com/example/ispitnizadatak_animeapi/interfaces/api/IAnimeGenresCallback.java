package com.example.ispitnizadatak_animeapi.interfaces.api;

import com.example.ispitnizadatak_animeapi.dtos.Genre;

import java.util.ArrayList;

public interface IAnimeGenresCallback {
    void onSuccess(ArrayList<Genre> genres);

    void onError(Exception e);
}

package com.example.ispitnizadatak_animeapi.interfaces.api;

import com.example.ispitnizadatak_animeapi.dtos.Review;

import java.util.ArrayList;

public interface IReviewsApiCallback {
    void onSuccess(ArrayList<Review> reviews, boolean hasNextPage);

    void onError(Exception e);
}

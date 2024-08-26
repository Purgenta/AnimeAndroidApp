package com.example.ispitnizadatak_animeapi.models;

public class SearchFormModel {
    private final String order;
    private final String ageRating;
    private final String title;

    public SearchFormModel(String order, String ageRating, String title) {
        this.order = order;
        this.ageRating = ageRating;
        this.title = title;
    }

    public String getOrder() {
        return order;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getTitle() {
        return title;
    }
}

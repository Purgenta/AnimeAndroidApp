package com.example.ispitnizadatak_animeapi.models;

public class SearchFormModel {
    private final String order;
    private final String ageRating;
    private final String title;
    private final String genre;

    public SearchFormModel(String order, String ageRating, String title, String genre) {
        this.order = order;
        this.ageRating = ageRating;
        this.title = title;
        this.genre = genre;
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

    public String getGenre() {
        return genre;
    }
}

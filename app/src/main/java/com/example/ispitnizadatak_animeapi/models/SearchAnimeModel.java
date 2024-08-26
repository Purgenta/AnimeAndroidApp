package com.example.ispitnizadatak_animeapi.models;

public class SearchAnimeModel {
    private String genre;
    private String order;
    private String rating;
    private String title;
    private String sort;
    private String status;
    private int page;
    private int limit;

    private SearchAnimeModel() {
    }

    public static class Builder {
        private String genre;
        private String order;
        private String rating;
        private String title;
        private String sort;
        private String status;
        private int page;
        private int limit;

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder order(String order) {
            this.order = order;
            return this;
        }

        public Builder rating(String rating) {
            this.rating = rating;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder sort(String sort) {
            this.sort = sort;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder page(int page) {
            this.page = page;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public SearchAnimeModel build() {
            SearchAnimeModel model = new SearchAnimeModel();
            model.genre = this.genre;
            model.order = this.order;
            model.rating = this.rating;
            model.title = this.title;
            model.sort = this.sort;
            model.status = this.status;
            model.page = this.page;
            model.limit = this.limit;
            return model;
        }
    }

    // Getters for all fields
    public String getGenre() {
        return genre;
    }

    public String getOrder() {
        return order;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getRating() {
        return rating;
    }

    public String getTitle() {
        return title;
    }

    public String getSort() {
        return sort;
    }

    public String getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }
}
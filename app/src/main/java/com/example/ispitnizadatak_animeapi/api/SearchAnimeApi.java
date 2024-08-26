package com.example.ispitnizadatak_animeapi.api;

import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.ispitnizadatak_animeapi.dtos.AnimePost;
import com.example.ispitnizadatak_animeapi.interfaces.api.ISearchAnimeApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.ISearchAnimeApiCallback;
import com.example.ispitnizadatak_animeapi.models.SearchAnimeModel;
import com.example.ispitnizadatak_animeapi.requests.GetRequest;
import com.example.ispitnizadatak_animeapi.requests.ReadJson;
import com.jayway.jsonpath.JsonPath;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchAnimeApi implements ISearchAnimeApi {
    public final String baseUrl = "https://api.jikan.moe/v4/anime?page=%s";

    private String toUrl(SearchAnimeModel model) {
        String genre = model.getGenre();
        String sort = model.getSort();
        String order = model.getOrder();
        String status = model.getStatus();
        String title = model.getTitle();
        String rating = model.getRating();
        int limit = model.getLimit();
        StringBuilder urlBuilder = new StringBuilder(String.format(baseUrl, model.getPage()));
        if (genre != null && !genre.isEmpty()) {
            urlBuilder.append("&genres=").append(genre);
        }
        if (sort != null && !sort.isEmpty()) {
            urlBuilder.append("&sort=").append(sort);
        }

        if (order != null && !order.isEmpty()) {
            urlBuilder.append("&order_by=").append(order);
        }

        if (rating != null && !rating.isEmpty()) {
            urlBuilder.append("&rating=").append(rating);
        }
        if (status != null && !status.isEmpty()) {
            urlBuilder.append("&status=").append(status);
        }
        if (title != null && !title.isEmpty()) {
            urlBuilder.append("&q=").append(title);
        }
        urlBuilder.append("&limit=").append(limit);

        return urlBuilder.toString();
    }

    public void getData(final ISearchAnimeApiCallback callback, SearchAnimeModel model) {
        String url = toUrl(model);
        GetRequest.getJSON(url, new ReadJson() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                try {
                    String response = getJson();
                    boolean hasNextPage = JsonPath.read(response, "$.pagination.has_next_page");
                    JSONObject data = new JSONObject(response);
                    ArrayList<AnimePost> animeList = AnimePost.parseJSONArray(data);
                    callback.onSearchResult(animeList, hasNextPage);
                } catch (Exception e) {
                    callback.onError(e);
                }
            }
        });
    }
}



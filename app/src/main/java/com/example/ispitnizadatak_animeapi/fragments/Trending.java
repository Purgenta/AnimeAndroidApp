package com.example.ispitnizadatak_animeapi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.interfaces.api.ISearchAnimeApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.ISearchAnimeApiCallback;
import com.example.ispitnizadatak_animeapi.api.SearchAnimeApi;
import com.example.ispitnizadatak_animeapi.dtos.AnimePost;
import com.example.ispitnizadatak_animeapi.interfaces.IDetails;
import com.example.ispitnizadatak_animeapi.models.SearchAnimeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Trending extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private LinearLayout list;
    private IDetails activity;
    private ISearchAnimeApi api;

    private SearchAnimeModel model = null;


    public Trending(ISearchAnimeApi api) {
        this.api = api;
        this.model = new SearchAnimeModel.Builder().page(1).limit(10).order("score").sort("desc").build();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_trending, container, false);
        this.list = inf.findViewById(R.id.animeTrending);
        return inf;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getTrendingAnime();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getTrendingAnime() {
        ISearchAnimeApiCallback callback = new ISearchAnimeApiCallback() {
            @Override
            public void onSearchResult(ArrayList<AnimePost> animeList, boolean hasNextPage) {
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                for (AnimePost anime : animeList) {
                    ConstraintLayout animeItem = (ConstraintLayout) inflater.inflate(R.layout.trending_anime_item, null);
                    TextView animeTitle = animeItem.findViewById(R.id.labelAnimeTitle);
                    Button animeDetails = animeItem.findViewById(R.id.buttonDetails);
                    animeDetails.setOnClickListener(view -> Trending.this.activity.onDetailsClicked(anime.getAnime_id()));
                    ImageView animeImage = animeItem.findViewById(R.id.anime_image);
                    Picasso.get().load(anime.getImage_url()).into(animeImage);
                    TextView animeScore = animeItem.findViewById(R.id.labelAnimeScore);
                    animeTitle.setText(anime.getTitle());
                    animeScore.setText(anime.getScore());
                    list.addView(animeItem);
                }
            }

            @Override
            public void onError(Exception e) {
                Log.d("Fetch error", "Error loading trending anime route" + e.getMessage());
            }
        };
        api.getData(callback, model);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IDetails) {
            activity = (IDetails) context;
        } else {
            throw new RuntimeException("You've to implement IDetails Interface!");
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }
}
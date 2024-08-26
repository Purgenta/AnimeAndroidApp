package com.example.ispitnizadatak_animeapi.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.ispitnizadatak_animeapi.dtos.AnimePost;
import com.example.ispitnizadatak_animeapi.interfaces.IDetails;
import com.example.ispitnizadatak_animeapi.interfaces.api.IRecommendationsApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.IRecommendationsApiCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Recommendations extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView labelRecommendations;
    private LinearLayout list;
    private View view;
    private IDetails activity;
    private final IRecommendationsApi api;


    public Recommendations(IRecommendationsApi api) {
        this.api = api;
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
        return view = inflater.inflate(R.layout.fragment_recommendations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MainActivitySharedPreferences", 0);
        int animeId = sharedPreferences.getInt("favouriteAnime", -1);
        if (animeId == -1) {
            this.labelRecommendations.setText(R.string.no_anime_to_recommend);
        } else {
            api.getData(new IRecommendationsApiCallback() {
                @Override
                public void onRecommendationsData(ArrayList<AnimePost> data) {
                    LayoutInflater inflater = requireActivity().getLayoutInflater();
                    if (data.size() > 20) {
                        for (int i = 0; i < 20; i++) {
                            createAnime(data.get(i), inflater);
                        }
                    } else {
                        for (AnimePost anime : data) {
                            createAnime(anime, inflater);
                        }
                    }
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Error getting the data", e.getMessage());
                }
            }, animeId);
        }
    }

    private void initComponents() {
        labelRecommendations = this.view.findViewById(R.id.labelRecommendations);
        list = this.view.findViewById(R.id.animeRecommendations);
    }

    private void createAnime(AnimePost anime, LayoutInflater inflater) {
        ConstraintLayout animeItem = (ConstraintLayout) inflater.inflate(R.layout.trending_anime_item, null);
        TextView animeTitle = animeItem.findViewById(R.id.labelAnimeTitle);
        TextView score = animeItem.findViewById(R.id.textViewScore);
        Button animeDetails = animeItem.findViewById(R.id.buttonDetails);
        animeTitle.setText(anime.getTitle());
        score.setText("");
        animeDetails.setOnClickListener(view -> Recommendations.this.activity.onDetailsClicked(anime.getAnime_id()));
        ImageView animeImage = animeItem.findViewById(R.id.anime_image);
        Picasso.get().load(anime.getImage_url()).into(animeImage);
        list.addView(animeItem);
        Log.d("ANIME", String.format("ANIME: TITLE: %s IMAGE : %s ID:%s", anime.getTitle(), anime.getImage_url(), anime.getAnime_id()));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (!(context instanceof IDetails)) {
            throw new RuntimeException("You've gotta implement IDetails");
        } else {
            activity = (IDetails) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }
}
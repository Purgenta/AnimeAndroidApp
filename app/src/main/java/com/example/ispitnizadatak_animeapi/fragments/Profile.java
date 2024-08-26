package com.example.ispitnizadatak_animeapi.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.ispitnizadatak_animeapi.R;


public class Profile extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View view;
    private TextView email;
    private TextView username;
    private TextView favouriteAnime;
    private TextView noFavouriteAnime;
    private ConstraintLayout favouriteAnimeDisplay;
    private Button unsetFavouriteAnime;

    public Profile() {
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
        return view = inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MainActivitySharedPreferences", 0);
        username.setText(sharedPreferences.getString("username", ""));
        email.setText(sharedPreferences.getString("email", ""));
        String animeTitle = sharedPreferences.getString("favouriteAnimeTitle", "");
        if (animeTitle.isEmpty()) {
            noFavouriteAnime.setText(R.string.you_don_t_have_a_favourite_anime_set);
            favouriteAnimeDisplay.setVisibility(View.GONE);
        } else {
            favouriteAnime.setText(animeTitle);
            unsetFavouriteAnime.setOnClickListener(view1 -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("favouriteAnime");
                editor.remove("favouriteAnimeTitle");
                editor.apply();
                favouriteAnimeDisplay.setVisibility(View.GONE);
                noFavouriteAnime.setText(R.string.you_don_t_have_a_favourite_anime_set);
            });
        }
    }

    private void initComponents() {
        username = view.findViewById(R.id.profileOverviewUsername);
        email = view.findViewById(R.id.profileOverviewEmail);
        noFavouriteAnime = view.findViewById(R.id.labelNoFavouriteAnime);
        favouriteAnime = view.findViewById(R.id.profileOverviewFavouriteAnime);
        favouriteAnimeDisplay = view.findViewById(R.id.favouriteAnimeDisplay);
        unsetFavouriteAnime = view.findViewById(R.id.profileOverviewRemoveFavourite);
    }
}
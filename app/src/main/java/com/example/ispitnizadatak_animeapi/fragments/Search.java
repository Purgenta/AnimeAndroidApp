package com.example.ispitnizadatak_animeapi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.interfaces.ISearchFragment;
import com.example.ispitnizadatak_animeapi.models.SearchFormModel;

import java.util.ArrayList;
import java.util.Arrays;

public class Search extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam2;
    private Spinner orderBy;
    private Spinner rating;
    private Button search;
    private EditText animeTitle;
    private ISearchFragment activity;

    public Search() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ISearchFragment) {
            this.activity = (ISearchFragment) context;
        } else {
            throw new RuntimeException("Activity needs to implement ISearchFragment");
        }
    }

    @Override
    public void onDetach() {
        activity = null;
        super.onDetach();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_search, container, false);
        initComponents(inf);
        search.setOnClickListener(view -> {
            String order = orderBy.getSelectedItem().toString().toLowerCase();
            String ageRating = rating.getSelectedItem().toString();
            String title = animeTitle.getText().toString().trim();
            String transformedAgeRating = "";
            if (!ageRating.equals("No specific age group")) {
                switch (ageRating) {
                    case "PG - Children": {
                        transformedAgeRating = "pg";
                        break;
                    }
                    case "G - All Ages": {
                        transformedAgeRating = "g";
                        break;
                    }
                    case "PG-13 - Teens 13 or older": {
                        transformedAgeRating = "pg13";
                        break;
                    }
                    case "R - 17+ (violence & profanity)": {
                        transformedAgeRating = "r17";
                        break;
                    }
                    case "R+ - Mild Nudity": {
                        transformedAgeRating = "r";
                        break;
                    }
                    case "Rx - Hentai": {
                        transformedAgeRating = "rx";
                        break;
                    }
                    default: {
                    }
                }
            }
            this.activity.searchAnime(new SearchFormModel(order, transformedAgeRating, title));
        });
        return inf;
    }

    private void initComponents(View inf) {
        orderBy = inf.findViewById(R.id.spinnerOrderBy);
        rating = inf.findViewById(R.id.spinnerRating);
        search = inf.findViewById(R.id.buttonSeach);
        animeTitle = inf.findViewById(R.id.editTextAnimeTitle);
        ArrayList<String> order = new ArrayList<>(Arrays.asList("Score", "Popularity", "Rank"));
        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, order);
        orderBy.setAdapter(orderAdapter);
        ArrayList<String> ratings = new ArrayList<>(Arrays.asList("No specific age group", "G - All Ages",
                "PG - Children",
                "PG-13 - Teens 13 or older",
                "R - 17+ (violence & profanity)",
                "R+ - Mild Nudity",
                "Rx - Hentai"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, ratings);
        rating.setAdapter(adapter);
    }

}
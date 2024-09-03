package com.example.ispitnizadatak_animeapi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ispitnizadatak_animeapi.R;

public class Home extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button beginSearch;
    private IHomeFragment activity;

    public Home() {
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IHomeFragment) {
            this.activity = (IHomeFragment) context;
        } else {
            throw new RuntimeException("You've gotta implement IHomeFragment");
        }
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
        View inf = inflater.inflate(R.layout.fragment_home, container, false);
        this.beginSearch = inf.findViewById(R.id.startSearching);
        beginSearch.setOnClickListener(view -> Home.this.activity.beginSearch());
        return inf;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public interface IHomeFragment {
        void beginSearch();
    }

    @Override
    public void onDetach() {
        this.activity = null;
        super.onDetach();
    }
}
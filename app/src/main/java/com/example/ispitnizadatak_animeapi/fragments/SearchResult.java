package com.example.ispitnizadatak_animeapi.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.interfaces.api.ISearchAnimeApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.ISearchAnimeApiCallback;
import com.example.ispitnizadatak_animeapi.interfaces.IDetails;
import com.example.ispitnizadatak_animeapi.models.SearchAnimeModel;
import com.example.ispitnizadatak_animeapi.recyclerview.RecyclerViewAdapter;
import com.example.ispitnizadatak_animeapi.dtos.AnimePost;

import java.util.ArrayList;


public class SearchResult extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private boolean hasNextPage = true;
    private final int lastVisibleItemOffset = 5;
    private int page = 1;
    private boolean isLoading = false;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private View view;
    private boolean initialLoad = true;
    private TextView searchType;
    private String searchName;
    private final ISearchAnimeApi apiRequest;
    private final SearchAnimeModel model;

    public SearchResult(ISearchAnimeApi api, SearchAnimeModel model) {
        apiRequest = api;
        this.model = model;
    }

    private void initProperties() {
        hasNextPage = true;
        page = 1;
        isLoading = false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof IDetails)) {
            throw new RuntimeException("You've gotta implement IDetails");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initProperties();
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
        if (initialLoad) {
            initProperties();
            getSearchData();
        }
        if (searchName != null) {
            searchType.setText(searchName);
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = linearLayoutManager != null ? linearLayoutManager.findLastCompletelyVisibleItemPosition() : 0;
                if (linearLayoutManager != null && linearLayoutManager.getItemCount() - lastVisibleItem < lastVisibleItemOffset && !SearchResult.this.isLoading) {
                    if (SearchResult.this.hasNextPage) {
                        SearchResult.this.isLoading = true;
                        model.setPage(++page);
                        getSearchData();
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_search_result, container, false);
        this.view = inf;
        return inf;
    }


    private void initComponents() {
        recyclerView = view.findViewById(R.id.searchResultsData);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchType = view.findViewById(R.id.labelSearchResults);
    }

    private void getSearchData() {
        ISearchAnimeApiCallback callback = new ISearchAnimeApiCallback() {
            @Override
            public void onSearchResult(ArrayList<AnimePost> animeList, boolean hasNextPage) {
                adapter.setSearchResults(animeList);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

            @Override
            public void onError(Exception e) {
                Log.d("Search data", "Error processing search request" + e.getMessage());
            }
        };
        apiRequest.getData(callback, model);
    }


    public void setSearchTitle(String searchName) {
        this.searchName = searchName;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.initialLoad = true;
    }

    @Override
    public void onPause() {
        this.initialLoad = true;
        super.onPause();
    }
}
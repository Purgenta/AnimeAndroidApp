package com.example.ispitnizadatak_animeapi.navigation;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.api.RecommendationsApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.ISearchAnimeApi;
import com.example.ispitnizadatak_animeapi.api.SearchAnimeApi;
import com.example.ispitnizadatak_animeapi.fragments.Home;
import com.example.ispitnizadatak_animeapi.fragments.Profile;
import com.example.ispitnizadatak_animeapi.fragments.Recommendations;
import com.example.ispitnizadatak_animeapi.fragments.Search;
import com.example.ispitnizadatak_animeapi.fragments.SearchResult;
import com.example.ispitnizadatak_animeapi.fragments.Trending;
import com.example.ispitnizadatak_animeapi.models.SearchAnimeModel;
import com.google.android.material.navigation.NavigationView;

public class Navigation {
    private DrawerLayout drawer;
    private final AppCompatActivity activity;

    public Navigation(AppCompatActivity activity) {
        this.activity = activity;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    @SuppressLint("NonConstantResourceId")
    public void initDrawer() {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        drawer = activity.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navView = activity.findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(item -> {
            FragmentManager fm = Navigation.this.activity.getSupportFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            switch (item.getItemId()) {
                case R.id.trendingAnime: {
                    ISearchAnimeApi api = new SearchAnimeApi();
                    Navigation.this.activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Trending(api)).setReorderingAllowed(true).addToBackStack(null).commit();
                    break;
                }
                case R.id.searchAnime: {
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Search()).setReorderingAllowed(true).addToBackStack(null).commit();
                    break;
                }
                case R.id.currentlyAiring: {
                    ISearchAnimeApi api = new SearchAnimeApi();
                    SearchAnimeModel searchAnimeModel = new SearchAnimeModel.Builder().page(1).limit(10).sort("desc&sfw").order("score").status("airing").build();
                    SearchResult searchResult = new SearchResult(api, searchAnimeModel);
                    searchResult.setSearchTitle("Currently Airing");
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            searchResult).setReorderingAllowed(true).addToBackStack(null).commit();
                    break;
                }
                case R.id.profile: {
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Profile()).setReorderingAllowed(true).addToBackStack(null).commit();
                    break;
                }
                case R.id.recommendations: {
                    RecommendationsApi api = new RecommendationsApi();
                    activity.getSupportFragmentManager().beginTransaction().replace
                            (R.id.fragment_container, new Recommendations(api)).setReorderingAllowed(true).addToBackStack(null).commit();
                    break;
                }
                default: {
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new Home()).setReorderingAllowed(true).addToBackStack(null).commit();
                    break;
                }
            }
            drawer.closeDrawer(GravityCompat.START);
            return false;
        });
    }
}

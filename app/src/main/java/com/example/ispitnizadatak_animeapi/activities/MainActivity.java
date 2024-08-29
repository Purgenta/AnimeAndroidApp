package com.example.ispitnizadatak_animeapi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.api.AnimeOptionsApi;
import com.example.ispitnizadatak_animeapi.api.DetailedAnimeApi;
import com.example.ispitnizadatak_animeapi.api.SearchAnimeApi;
import com.example.ispitnizadatak_animeapi.fragments.DetailedAnime;
import com.example.ispitnizadatak_animeapi.fragments.Home;
import com.example.ispitnizadatak_animeapi.interfaces.IDetails;
import com.example.ispitnizadatak_animeapi.fragments.Search;
import com.example.ispitnizadatak_animeapi.fragments.SearchResult;
import com.example.ispitnizadatak_animeapi.interfaces.ISearchFragment;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeOptionsApi;
import com.example.ispitnizadatak_animeapi.models.SearchAnimeModel;
import com.example.ispitnizadatak_animeapi.models.SearchFormModel;
import com.example.ispitnizadatak_animeapi.navigation.Navigation;

public class MainActivity extends AppCompatActivity implements ISearchFragment, IDetails, Home.IHomeFragment {
    private final Navigation navigation = new Navigation(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("MainActivitySharedPreferences", 0);
        if (sharedPreferences.getString("username", "").isEmpty()) {
            Intent intent = new Intent(this, UserInformationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation.initDrawer();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).setReorderingAllowed(true).addToBackStack("name").commit();
    }

    public void onDetailsClicked(int animeId) {
        DetailedAnime fragment = new DetailedAnime(new DetailedAnimeApi(), animeId);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (this.navigation.getDrawer().isDrawerOpen(GravityCompat.START)) {
            this.navigation.getDrawer().closeDrawer(GravityCompat.START);
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            MainActivity.this.finish();
        }
        super.onBackPressed();
    }

    @Override
    public void searchAnime(SearchFormModel model) {
        SearchAnimeApi api = new SearchAnimeApi();
        SearchAnimeModel searchAnimeModel = new SearchAnimeModel.Builder().page(1).limit(10).order(model.getOrder()).title(model.getTitle()).rating(model.getAgeRating()).build();
        SearchResult searchResultFragment = new SearchResult(api, searchAnimeModel);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchResultFragment).addToBackStack(null).commit();
    }

    @Override
    public void beginSearch() {
        IAnimeOptionsApi api = new AnimeOptionsApi();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Search(api)).addToBackStack(null).commit();
    }
}
package com.example.ispitnizadatak_animeapi.fragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.dtos.AnimeDetailedPost;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeDetailsApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeDetailsApiCallback;
import com.example.ispitnizadatak_animeapi.recyclerview.RecyclerReviewAdapter;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailedAnime extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private View view;
    private ImageView animeImage;
    private TextView animeTitle;
    private TextView animeScore;
    private TextView episodes;
    private TextView synopsis;
    private TextView animeViewers;
    private Button setAsFavourite;
    private RecyclerReviewAdapter adapter;
    private WebView animeTrailer;
    private ConstraintLayout animeTrailerLayout;
    private AnimeDetailedPost anime;
    private final IAnimeDetailsApi api;
    private final int animeId;
    private CardView openComments;

    public DetailedAnime(IAnimeDetailsApi api, int animeId) {
        this.api = api;
        this.animeId = animeId;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detailed_anime, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        detailedAnime();
        initComponents();

    }


    @SuppressLint("SetJavaScriptEnabled")
    public void embedTrailer() {
        String embedUrl = anime.getEmbedUrl();
        if (embedUrl != null && embedUrl.isEmpty()) {
            animeTrailer.setVisibility(View.INVISIBLE);
            animeTrailerLayout.setVisibility(View.INVISIBLE);
            return;
        }

        WebSettings webSettings = animeTrailer.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if (anime.getEmbedUrl() == null) {
            handleTrailerLoadError();
            return;
        }
        String html = "<html><body style=\"display: flex; justify-content: center; align-items: center; margin: 0;\">" +
                "<iframe width=\"100%\" height=\"100%\" " +
                "src=\"" + anime.getEmbedUrl() + "\"" +
                " frameborder=\"0\" allowfullscreen></iframe>" +
                "</body></html>";
        animeTrailer.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                handleTrailerLoadError();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
        animeTrailer.loadData(html, "text/html", "utf-8");
    }

    public void addFavouriteToggle(int animeId) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MainActivitySharedPreferences", 0);
        if (sharedPreferences.getInt("favouriteAnime", -1) == animeId) {
            setAsFavourite.setText(R.string.remove_favourite);
        }
        setAsFavourite.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String toastMessage;
            if (sharedPreferences.getInt("favouriteAnime", -1) == animeId) {
                toastMessage = "Removed anime as favourite";
                editor.remove("favouriteAnime");
                editor.remove("favouriteAnimeTitle");
                setAsFavourite.setText(R.string.set_as_favourite);
            } else {
                editor.putInt("favouriteAnime", animeId);
                editor.putString("favouriteAnimeTitle", anime.getTitle());
                setAsFavourite.setText(R.string.remove_favourite);
                toastMessage = "Added anime as favourite";
            }
            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
            editor.apply();
        });
    }

    public void detailedAnime() {
        api.getAnimeById(animeId, new IAnimeDetailsApiCallback() {
            @Override
            public void onSuccess(AnimeDetailedPost data) {
                anime = data;
                populateUiElements(animeId);
            }

            @Override
            public void onError(Exception e) {
                Log.d("Detailed anime", Objects.requireNonNull(e.getMessage()));
            }
        });
    }

    public void populateUiElements(int animeId) {
        String imageUrl = anime.getImage_url();
        String episodeNumber = anime.getEpisodes();
        String title = anime.getTitle();
        String score = anime.getScore();
        String viewers = anime.getViewers();
        if (imageUrl == null) {
            animeImage.setBackgroundResource(R.drawable.icons8_naruto_sign);
        } else {
            Picasso.get().load(imageUrl).into(animeImage);
        }
        embedTrailer();
        animeScore.setText(score);
        animeTitle.setText(title);
        animeViewers.setText(viewers);
        episodes.setText(episodeNumber);
        synopsis.setText(anime.getSynopsis());
        addFavouriteToggle(animeId);
    }

    private void initComponents() {
        animeImage = this.view.findViewById(R.id.detailedAnimeImage);
        animeTitle = this.view.findViewById(R.id.labelDetailedAnimeTitle);
        animeScore = this.view.findViewById(R.id.ratingCount);
        animeViewers = this.view.findViewById(R.id.viewersCount);
        episodes = this.view.findViewById(R.id.detailedAnimeNumberOfEpisodes);
        synopsis = this.view.findViewById(R.id.labelDetailedAnimeSynopsis);
        setAsFavourite = this.view.findViewById(R.id.buttonAddFavourite);
        animeTrailer = this.view.findViewById(R.id.animeTrailer);
        animeTrailerLayout = this.view.findViewById(R.id.animeTrailerlayout);
        openComments = this.view.findViewById(R.id.openComments);
        addCommentSectionOnClick();
    }

    private void addCommentSectionOnClick() {
        openComments.setOnClickListener(view -> {
            ReviewsDialog dialog = new ReviewsDialog(api, animeId);
            dialog.show(getParentFragmentManager(), "Reviews Dialog");
        });
    }

    private void handleTrailerLoadError() {
        TextView trailerErrorMessage = view.findViewById(R.id.trailerErrorMessage);
        animeTrailer.setVisibility(View.INVISIBLE);
        trailerErrorMessage.setVisibility(View.VISIBLE);

    }
}
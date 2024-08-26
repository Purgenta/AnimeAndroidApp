package com.example.ispitnizadatak_animeapi.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.interfaces.IDetails;
import com.example.ispitnizadatak_animeapi.dtos.AnimePost;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<AnimePost> searchResults = new ArrayList<>();
    private final Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setSearchResults(ArrayList<AnimePost> searchResults) {
        this.searchResults.addAll(searchResults);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trending_anime_item, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AnimeViewHolder viewHolder = (AnimeViewHolder) holder;
        AnimePost anime = searchResults.get(position);
        int animeId = anime.getAnime_id();
        viewHolder.getDetails().setOnClickListener(view -> {
            if (context instanceof IDetails) {
                ((IDetails) context).onDetailsClicked(animeId);
            } else {
                throw new RuntimeException("You've gotta implement IDetails");
            }
        });
        viewHolder.getTitle().setText(anime.getTitle());
        viewHolder.getScore().setText(anime.getScore());
        String imageUrl = anime.getImage_url();
        if (imageUrl == null) {
            viewHolder.getAnimeImage().setBackgroundResource(R.drawable.icons8_naruto_sign);
        } else {
            Picasso.get().load(imageUrl).into(viewHolder.getAnimeImage(), new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                    viewHolder.getAnimeImage().setBackgroundResource(R.drawable.icons8_naruto_sign);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

}

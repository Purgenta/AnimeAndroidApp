package com.example.ispitnizadatak_animeapi.recyclerview;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ispitnizadatak_animeapi.R;

public class AnimeViewHolder extends RecyclerView.ViewHolder {
    private final ImageView animeImage;
    private final TextView title;
    private final TextView score;
    private final Button details;


    public ImageView getAnimeImage() {
        return animeImage;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getScore() {
        return score;
    }

    public Button getDetails() {
        return details;
    }

    public AnimeViewHolder(@NonNull View itemView) {
        super(itemView);
        details = itemView.findViewById(R.id.buttonDetails);
        animeImage = itemView.findViewById(R.id.anime_image);
        title = itemView.findViewById(R.id.labelAnimeTitle);
        score = itemView.findViewById(R.id.labelAnimeScore);
    }
}

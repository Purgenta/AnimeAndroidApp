package com.example.ispitnizadatak_animeapi.recyclerview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ispitnizadatak_animeapi.R;

public class ReviewsViewHolder extends RecyclerView.ViewHolder {

    private final TextView score;
    private final TextView user;
    private final TextView reviewDate;
    private final TextView review;
    private final ImageView profileImage;
    private boolean isReviewExpanded = false;

    public ReviewsViewHolder(@NonNull View itemView) {
        super(itemView);
        reviewDate = itemView.findViewById(R.id.reviewDate);
        user = itemView.findViewById(R.id.reviewUsername);
        review = itemView.findViewById(R.id.reviewContent);
        score = itemView.findViewById(R.id.reviewScore);
        profileImage = itemView.findViewById(R.id.reviewUserProfile);

    }

    public TextView getScore() {
        return score;
    }

    public TextView getUser() {
        return user;
    }

    public TextView getReviewDate() {
        return reviewDate;
    }

    public TextView getReview() {
        return review;
    }

    public ImageView getProfileImage() {
        return profileImage;
    }

    public boolean isReviewExpanded() {
        return isReviewExpanded;
    }

    public void setReviewExpanded(boolean reviewExpanded) {
        isReviewExpanded = reviewExpanded;
    }
}

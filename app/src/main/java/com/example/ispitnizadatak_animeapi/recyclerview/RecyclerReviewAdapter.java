package com.example.ispitnizadatak_animeapi.recyclerview;


import android.content.Context;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.dtos.Review;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final ArrayList<Review> reviews = new ArrayList<>();
    private final Context context;

    public RecyclerReviewAdapter(Context context) {
        this.context = context;
    }

    public void setSearchResults(ArrayList<Review> reviews) {
        this.reviews.addAll(reviews);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReviewsViewHolder viewHolder = (ReviewsViewHolder) holder;
        Review review = reviews.get(position);
        viewHolder.getReviewDate().setText(review.getDate());
        String readMore = "...Read more";
        String readLess = "...Read less";
        SpannableString spannableReview = new SpannableString(review.getReview() + readMore);
        viewHolder.getScore().setText(review.getScore());
        viewHolder.getReview().setText(spannableReview);
        viewHolder.getReviewDate().setText(review.getDate());
        viewHolder.getUser().setText(review.getUser().getUsername());
        String userImageUrl = review.getUser().getUrl();
        viewHolder.getReview().setOnClickListener(view -> {
            boolean nextValue = !viewHolder.isReviewExpanded();
            viewHolder.setReviewExpanded(nextValue);
            SpannableString nextSpannableReview = new SpannableString(review.getReview() + (nextValue ? readLess : readMore));
            viewHolder.getReview().setText(nextSpannableReview);
        });
        if (userImageUrl != null && !userImageUrl.isEmpty()) {
            Picasso.get().load(userImageUrl).into(viewHolder.getProfileImage(), new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Log.d("Image error", "Error loading user image");
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}

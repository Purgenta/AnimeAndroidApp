package com.example.ispitnizadatak_animeapi.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ispitnizadatak_animeapi.R;
import com.example.ispitnizadatak_animeapi.dtos.Review;
import com.example.ispitnizadatak_animeapi.interfaces.api.IAnimeDetailsApi;
import com.example.ispitnizadatak_animeapi.interfaces.api.IReviewsApiCallback;
import com.example.ispitnizadatak_animeapi.recyclerview.RecyclerReviewAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Objects;

public class ReviewsDialog extends BottomSheetDialogFragment {
    private final IAnimeDetailsApi api;
    private boolean isLoading = false;
    private boolean hasNextPage = true;
    private final int lastVisibleItemOffset = 5;
    private int page = 1;
    private final int animeId;
    private RecyclerReviewAdapter adapter;
    private RecyclerView viewHolder;

    public ReviewsDialog(IAnimeDetailsApi api, int animeId) {
        this.api = api;
        this.animeId = animeId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Remove title from the dialog
        Objects.requireNonNull(getDialog()).requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.fragmentreviewdialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHolder = view.findViewById(R.id.reviewResults);
        viewHolder.setHasFixedSize(true);
        adapter = new RecyclerReviewAdapter(getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        viewHolder.setLayoutManager(manager);
        viewHolder.setAdapter(adapter);
        getReviewsData();
        viewHolder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastVisibleItem = linearLayoutManager != null ? linearLayoutManager.findLastCompletelyVisibleItemPosition() : 0;

                if (linearLayoutManager != null) {
                    int itemCount = linearLayoutManager.getItemCount();
                    if (itemCount - lastVisibleItem < lastVisibleItemOffset && !isLoading && hasNextPage) {
                        page += 1;
                        getReviewsData();
                    }
                }
            }
        });
        View bottomSheet = this.getView().findViewById(R.id.standard_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenHeight = displayMetrics.heightPixels;
        int maxHeight = (int) (screenHeight * 0.7);
        // we set initial peek to be at max and maxHeight to be 70% of the screen from the bottom to the top
        behavior.setMaxHeight(maxHeight - 48);
        behavior.setPeekHeight(maxHeight);
        behavior.setHideable(false);

        viewHolder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy != 0) {
                    behavior.setDraggable(false);
                } else {
                    behavior.setDraggable(true);
                }
            }
        });


        viewHolder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    behavior.setDraggable(true);
                }
            }
        });

    }

    private void getReviewsData() {
        isLoading = true;
        IReviewsApiCallback callback = new IReviewsApiCallback() {
            @Override
            public void onSuccess(ArrayList<Review> reviews, boolean hasNextPage) {
                if (reviews.isEmpty()) ReviewsDialog.this.hasNextPage = false;
                adapter.setSearchResults(reviews);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

            @Override
            public void onError(Exception e) {
                Log.d("Error loading review", Objects.requireNonNull(e.getMessage()));
                hasNextPage = false;
                isLoading = false;
            }
        };
        api.getAnimeReviewsById(animeId, page, callback);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}

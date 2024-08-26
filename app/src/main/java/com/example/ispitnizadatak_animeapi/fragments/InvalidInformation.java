package com.example.ispitnizadatak_animeapi.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ispitnizadatak_animeapi.R;

public class InvalidInformation extends DialogFragment {
    private final String message;

    public InvalidInformation(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext()).setMessage(this.message).setPositiveButton(
                getString(R.string.ok), (dialog, which) -> {
                }).create();
    }
}

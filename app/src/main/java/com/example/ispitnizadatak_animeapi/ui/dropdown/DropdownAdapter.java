package com.example.ispitnizadatak_animeapi.ui.dropdown;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DropdownAdapter extends ArrayAdapter<DropdownItem> {
    public DropdownAdapter(@NonNull Context context, int resource, @NonNull List<DropdownItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public android.view.View getView(int position, @Nullable android.view.View convertView, @NonNull android.view.ViewGroup parent) {
        // Customize the view here if needed
        return super.getView(position, convertView, parent);
    }

    @Override
    public android.view.View getDropDownView(int position, @Nullable android.view.View convertView, @NonNull android.view.ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }
}

package com.example.ispitnizadatak_animeapi.ui.dropdown;

import androidx.annotation.NonNull;

public class DropdownItem {
    private final String label;
    private final String value;

    public DropdownItem(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    @NonNull
    @Override
    public String toString() {
        return label;
    }
}

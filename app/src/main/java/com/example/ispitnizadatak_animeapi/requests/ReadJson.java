package com.example.ispitnizadatak_animeapi.requests;

import android.os.Handler;

public class ReadJson extends Handler {
    private String json;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}

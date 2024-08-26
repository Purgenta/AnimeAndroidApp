package com.example.ispitnizadatak_animeapi.formatters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateFormatter {
    public static String formatISO8601Date(String iso8601Date) {
        try {
            String modifiedDate = iso8601Date.replaceAll("([+-]\\d{2}):(\\d{2})$", "$1$2");

            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
            iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = iso8601Format.parse(modifiedDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "Invalid date format";
        }
    }
}
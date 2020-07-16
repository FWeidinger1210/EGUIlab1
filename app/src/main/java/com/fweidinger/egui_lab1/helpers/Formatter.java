package com.fweidinger.egui_lab1.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Simple class that holds useful formatting operations.
 */
public class Formatter {

    /**
     * Takes an object of type Instant and returns String representation in correct date time formatting style for Germany.
     * @param instant an instantaneous point in time to be formatted
     * @return the formatted date
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatDate(Instant instant) {

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMANY)
                        .withZone(ZoneId.systemDefault());

        return dateTimeFormatter.format(instant);

    }
}

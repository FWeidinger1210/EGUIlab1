package com.fweidinger.egui_lab1.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Formatter {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatDate(Instant instant) {

        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                        .withLocale(Locale.GERMANY)
                        .withZone(ZoneId.systemDefault());

        return dateTimeFormatter.format(instant);

    }
}

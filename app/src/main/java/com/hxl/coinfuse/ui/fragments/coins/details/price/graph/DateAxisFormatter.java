package com.hxl.coinfuse.ui.fragments.coins.details.price.graph;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateAxisFormatter {

    private static ValueFormatter longTime;
    private static ValueFormatter shortTime;

    public static ValueFormatter getLongTimeFormatter() {
        if (longTime == null) {
            longTime = new LongTime();
        }

        return longTime;
    }

    public static ValueFormatter getShortTimeFormatter() {
        if (shortTime == null) {
            shortTime = new ShortTime();
        }

        return shortTime;
    }

    private static final class LongTime extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            Instant instant = Instant.ofEpochMilli((long) value);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM", Locale.getDefault());
            return localDateTime.format(formatter);
        }
    }


    private static final class ShortTime extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            Instant instant = Instant.ofEpochMilli((long) value);
            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault());
            return localDateTime.format(formatter);
        }
    }
}

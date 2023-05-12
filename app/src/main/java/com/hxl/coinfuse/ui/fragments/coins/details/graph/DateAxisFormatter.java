package com.hxl.coinfuse.ui.fragments.coins.details.graph;

import com.github.mikephil.charting.formatter.ValueFormatter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateAxisFormatter {

    public static ValueFormatter longTime = new LongTime();
    public static ValueFormatter shortTime = new ShortTime();

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

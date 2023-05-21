package com.hxl.coinfuse.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class NumberFormatUtil {

    public static String formatDouble(Double num, int bigFractions, int smallFractions) {
        if (num != null) {
            DecimalFormat df = new DecimalFormat("###,###");
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(bigFractions);
            if (num > 1.0d) {
                df.setMaximumFractionDigits(bigFractions);
                return df.format(num);
            }
            df.setMaximumFractionDigits(smallFractions);
            return df.format(num);
        }
        return "-";
    }

    public static String formatDouble(Double num) {
        return formatDouble(num, 2, 6);
    }

    public static String formatDoubleDetailed(Double num) {
        return formatDouble(num, 2, 10);
    }

    public static String formatFloat(Float num) {
        if (num != null) {
            DecimalFormat df = new DecimalFormat("###,###.##");
            df.setGroupingSize(3);
            df.setMinimumFractionDigits(2);
            df.setDecimalSeparatorAlwaysShown(true);
            return df.format(num);
        }
        return "-";
    }

    public static String formatBigDouble(Double num) {
        if (num != null) {
            String suffix = "";
            double formattedValue = num;
            if (num >= 1_000_000_000D) {
                suffix = "B";
                formattedValue = num / 1_000_000_000.0D;
            } else if (num >= 1_000_000D) {
                suffix = "M";
                formattedValue = num / 1_000_000.0D;
            } else if (num >= 1_000D) {
                suffix = "K";
                formattedValue = num / 1_000.0D;
            }

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            return df.format(formattedValue) + suffix;
        }
        return "-";
    }
}

package me.truec0der.mwhitelist.util;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class TimeUtil {
    public long parseUnit(String[] unitList, int startIndex) {
        long totalMillis = 0;

        for (int i = startIndex; i < unitList.length; i++) {
            String unit = unitList[i];

            Pattern pattern = Pattern.compile("(\\d+)([a-zA-Z]+)");
            Matcher matcher = pattern.matcher(unit);

            if (matcher.matches()) {
                int number = Integer.parseInt(matcher.group(1));
                String matchUnit = matcher.group(2);

                totalMillis += convertUnitToMillis(number, matchUnit);
            }
        }

        return totalMillis;
    }

    public long convertUnitToMillis(int number, String unit) {
        return switch (unit) {
            case "y" -> number * 31556952000L;
            case "mo" -> number * 2629746000L;
            case "w" -> number * 604800000L;
            case "d" -> number * 86400000L;
            case "h" -> number * 3600000L;
            case "m" -> number * 60000L;
            case "s" -> number * 1000L;
            default -> -1;
        };
    }

    public Duration timeToDuration(long time) {
        Instant instant = Instant.ofEpochMilli(time);
        return Duration.between(Instant.EPOCH, instant);
    }
}

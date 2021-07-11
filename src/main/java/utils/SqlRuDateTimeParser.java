package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SqlRuDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) throws IllegalArgumentException {
        String[] splitParse = parse.split(" ");
        if (splitParse.length == 2) {
            String day = splitParse[0].substring(0,splitParse[0].length() - 1);
            String[] time = splitParse[1].split(":");
            int hour = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            return LocalDateTime.of(shortDate(day), LocalTime.of(hour, minutes));
        } else if (splitParse.length == 4) {
            int day = Integer.parseInt(splitParse[0]);
            int month = monthConvert(splitParse[1]);
            int year = Integer.parseInt(splitParse[2].substring(0,splitParse[2].length() - 1));
            String[] time = splitParse[3].split(":");
            int hour = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            return LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minutes));
        }
        return null;
    }

    private LocalDate shortDate(String day) throws IllegalArgumentException {
        switch (day) {
            case ("сегодня"): return LocalDate.now();
            case ("вчера"): return LocalDate.now().minusDays(1);
        }
        throw new IllegalArgumentException();
    }

    private int monthConvert(String month) throws IllegalArgumentException {
        switch (month) {
            case ("янв"): return 1;
            case ("фев"): return 2;
            case ("апр"): return 3;
            case ("май"): return 4;
            case ("июн"): return 5;
            case ("июл"): return 6;
            case ("авг"): return 7;
            case ("сен"): return 8;
            case ("окт"): return 9;
            case ("ноя"): return 10;
            case ("дек"): return 11;
        }
        throw new IllegalArgumentException();
    }
}
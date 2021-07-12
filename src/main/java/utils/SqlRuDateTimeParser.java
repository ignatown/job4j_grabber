package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, Integer> months = new HashMap<>(){{
        put("янв",1);
        put("фев",2);
        put("мар",3);
        put("апр",4);
        put("май",5);
        put("июн",6);
        put("июл",7);
        put("авг",8);
        put("сен",9);
        put("окт",10);
        put("ноя",11);
        put("дек",12);
    }};

    @Override
    public LocalDateTime parse(String parse) throws IllegalArgumentException {
        String[] splitParse = parse.split(" ");
        if (splitParse.length == 2) {
            String day = splitParse[0].substring(0, splitParse[0].length() - 1);
            return LocalDateTime.of(shortDate(day), timePars(splitParse[1]));
        } else if (splitParse.length == 4) {
            int day = Integer.parseInt(splitParse[0]);
            int month = months.get(splitParse[1]);
            int year = Integer.parseInt(splitParse[2].substring(0, splitParse[2].length() - 1));
            return LocalDateTime.of(LocalDate.of(year, month, day), timePars(splitParse[3]));
        }
        return null;
    }

    private LocalTime timePars(String time){
        String[] splitTime = time.split(":");
        return LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
    }

    private LocalDate shortDate(String day) throws IllegalArgumentException {
        switch (day) {
            case ("сегодня"): return LocalDate.now();
            case ("вчера"): return LocalDate.now().minusDays(1);
            default: throw new IllegalArgumentException();
        }
    }
}
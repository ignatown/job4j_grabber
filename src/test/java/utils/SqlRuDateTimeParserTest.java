package utils;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalDate;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SqlRuDateTimeParserTest {
    @Test
    public void simpleDate() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = parser.parse("22 янв 21, 10:56");
        assertThat(localDateTime.getDayOfMonth(), is(22));
        assertThat(localDateTime.getMonth().toString(), is("JANUARY"));
        assertThat(localDateTime.getYear(), is(21));
        assertThat(localDateTime.getHour(), is(10));
        assertThat(localDateTime.getMinute(), is(56));
    }

    @Test
    public void shortDateToday() {
        LocalDate today = LocalDate.now();
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = parser.parse("сегодня, 10:56");
        assertThat(localDateTime.getDayOfMonth(), is(today.getDayOfMonth()));
        assertEquals(localDateTime.getMonth().toString(), today.getMonth().toString());
        assertEquals(localDateTime.getYear(), today.getYear());
        assertThat(localDateTime.getHour(), is(10));
        assertThat(localDateTime.getMinute(), is(56));
    }

    @Test
    public void shortDateYesterday() {
        LocalDate today = LocalDate.now();
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime localDateTime = parser.parse("вчера, 15:12");
        assertThat(localDateTime.getDayOfMonth(), is(today.minusDays(1).getDayOfMonth()));
        assertEquals(localDateTime.getMonth().toString(), today.getMonth().toString());
        assertEquals(localDateTime.getYear(), today.getYear());
        assertThat(localDateTime.getHour(), is(15));
        assertThat(localDateTime.getMinute(), is(12));
    }

    @Test
    public void incorrectData() {
        boolean thrown = false;
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
       try {
           parser.parse("завтра, 25:64");
       }
       catch (IllegalArgumentException e) {
           thrown = true;
       }
        assertTrue(thrown);
    }
}
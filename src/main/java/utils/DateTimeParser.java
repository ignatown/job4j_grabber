package utils;

import java.io.IOException;
import java.time.LocalDateTime;

public interface DateTimeParser {
    LocalDateTime parse(String parse) throws IOException;
}

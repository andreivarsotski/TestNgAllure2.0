package by.company.app.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class GenerateUtil {

    public static String getUniqueNum() {
        return
                LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS"))
                        .concat(String.valueOf(Thread.currentThread().getId()))
                        .replaceAll("[\\D]", EMPTY);
    }

}

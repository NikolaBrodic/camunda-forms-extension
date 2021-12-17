package rs.ac.uns.ftn.camundaformsextension.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeUtil {

    public static LocalDate getLocalDate(String dateStr, DateTimeFormatter formatter) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            date = null;
        }
        return date;
    }

    public static LocalTime getLocalTime(String timeStr, DateTimeFormatter formatter) {
        LocalTime time;
        try {
            time = LocalTime.parse(timeStr, formatter);
        } catch (DateTimeParseException e) {
            time = null;
        }
        return time;
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}

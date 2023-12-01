package app.share.app.utility;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebDateUtils {
    public static final String DATE_PATTERN = "yyyy.MM.dd";
    public static final String DATE_HM_PATTERN = "yyyy.MM.dd HH:mm";
    public static final String DATE_HMS_PATTERN = "yyyy.MM.dd HH:mm:ss"; // HH: 00:00, kk: 24:00

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum DatePatternType {
        DATE_PATTERN("yyyy.MM.dd"),
        DATE_HM_PATTERN("yyyy.MM.dd HH:mm"),
        DATE_HMS_PATTERN("yyyy.MM.dd HH:mm:ss");

        private final String value;
    }

    public static String toString(LocalDateTime dateTime, DatePatternType pattern) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern.getValue()));
    }

    public static String getCurrentYear(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return String.valueOf(LocalDate.now().getYear());
        } else return String.valueOf(dateTime.getYear());
    }

    public static String getCurrentMonth(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return String.valueOf(LocalDate.now().getMonthValue());
        } else return String.valueOf(dateTime.getMonthValue());
    }

    public static String getCurrentDay(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime)) {
            return String.valueOf(LocalDate.now().getDayOfMonth());
        } else return String.valueOf(dateTime.getDayOfMonth());
    }

    public static String getCurrentDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_HMS_PATTERN));
    }

    public static LocalDateTime getMaxDateTime() {
        return LocalDateTime.of(9999, 12, 31, 23, 59, 59);
    }

    public static DateTimeFormatter getDateFormatter() {
        return DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(DATE_HMS_PATTERN);
    }

    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime toDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime toDateTime(String value) {
        return LocalDateTime.parse(value, getDateTimeFormatter());
    }

    public static String toString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DATE_HMS_PATTERN));
    }
}
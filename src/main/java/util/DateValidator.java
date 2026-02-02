package main.java.util;

import java.time.Year;

public class DateValidator {

    public static boolean isValidYear(String yearText) {
        try {
            int year = Integer.parseInt(yearText.trim());
            return year >= 1888 && year <= Year.now().getValue();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int parseYear(String yearText) {
        if (!isValidYear(yearText)) {
            throw new IllegalArgumentException("Некорректный год. Допустимый диапазон: 1888-" + Year.now().getValue());
        }
        return Integer.parseInt(yearText.trim());
    }
}
package main.java.util;

public class RatingValidator {

    public static boolean isValidRating(String ratingText) {
        try {
            String cleanText = ratingText.trim().replace(',', '.');
            double rating = Double.parseDouble(cleanText);
            return rating >= 0 && rating <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double parseRating(String ratingText) {
        if (!isValidRating(ratingText)) {
            throw new IllegalArgumentException("Рейтинг должен быть от 0 до 10");
        }
        String cleanText = ratingText.trim().replace(',', '.');
        return Double.parseDouble(cleanText);
    }
}
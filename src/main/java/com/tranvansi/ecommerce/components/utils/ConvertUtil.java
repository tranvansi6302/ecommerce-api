package com.tranvansi.ecommerce.components.utils;

import java.text.Normalizer;
import java.util.Locale;

public class ConvertUtil {
    public static String toSlug(String input) {
        // Convert to lower case
        String normalized =
                Normalizer.normalize(input, Normalizer.Form.NFD)
                        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                        .toLowerCase(Locale.ENGLISH);

        // Replace all non-alphanumeric characters with hyphens
        String slug = normalized.replaceAll("[^\\p{Alnum}]+", "-");

        // Remove leading and trailing hyphens
        slug = slug.replaceAll("^-+|-+$", "");

        return slug;
    }

    public static String toRemoveAccent(String input) {
        // Normalize and remove diacritical marks
        String normalized =
                Normalizer.normalize(input, Normalizer.Form.NFD)
                        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        // Replace specific Vietnamese characters
        normalized = normalized.replace('đ', 'd').replace('Đ', 'D');

        // Convert to lower case
        normalized = normalized.toLowerCase(Locale.ENGLISH);

        // Replace all non-alphanumeric characters with hyphens
        String result = normalized.replaceAll("[^\\p{Alnum}]+", "-");

        // Convert to upper case
        return result.toUpperCase();
    }
}

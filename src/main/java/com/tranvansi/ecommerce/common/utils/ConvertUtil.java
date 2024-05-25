package com.tranvansi.ecommerce.common.utils;

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
}

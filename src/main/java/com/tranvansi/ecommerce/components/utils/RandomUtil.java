package com.tranvansi.ecommerce.components.utils;

import java.util.Random;

public class RandomUtil {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SKU_LENGTH = 5;
    private static final Random RANDOM = new Random();

    public static String generateUniqueSKU() {
        String sku;
        do {
            sku = generateRandomSKU();
        } while (isSKUExists(sku));
        return sku;
    }

    private static String generateRandomSKU() {
        StringBuilder builder = new StringBuilder(SKU_LENGTH);
        for (int i = 0; i < SKU_LENGTH; i++) {
            builder.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return builder.toString();
    }

    private static boolean isSKUExists(String sku) {
        return false;
    }
}

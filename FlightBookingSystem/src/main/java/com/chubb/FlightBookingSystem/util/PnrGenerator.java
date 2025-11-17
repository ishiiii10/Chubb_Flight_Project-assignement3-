package com.chubb.FlightBookingSystem.util;

import java.security.SecureRandom;

/**
 * Simple PNR generator for my flight project.
 * Example output: "A7K9QZ", "P3LM8X"
 */
public class PnrGenerator {

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int PNR_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePnr() {
        StringBuilder sb = new StringBuilder(PNR_LENGTH);
        for (int i = 0; i < PNR_LENGTH; i++) {
            int index = RANDOM.nextInt(CHAR_POOL.length());
            sb.append(CHAR_POOL.charAt(index));
        }
        return sb.toString();
    }
}
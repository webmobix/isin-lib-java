package com.webmobix.util;

import java.math.BigInteger;

/**
 * Utility class for converting ISIN strings to or from BigInteger.
 */
public class ISINConverter {

    private static final long MAX_ISIN_VALUE = (long) Math.pow(36, 12) - 1; // 36^12 - 1

    /**
     * Converts an ISIN string into a BigInteger in base 36.
     *
     * @param isin A valid 12-character ISIN string
     * @return A BigInteger representing the ISIN
     * @throws IllegalArgumentException If the ISIN format is invalid
     */
    public static BigInteger isinToUint256(String isin) {
        // Normalize to uppercase and validate format
        isin = isin.toUpperCase();
        if (!isin.matches("^[A-Z0-9]{12}$")) {
            throw new IllegalArgumentException("Invalid ISIN format");
        }

        BigInteger numericValue = BigInteger.ZERO;
        for (char ch : isin.toCharArray()) {
            int value;
            if (Character.isDigit(ch)) {
                value = ch - '0'; // 0-9
            } else {
                value = ch - 'A' + 10; // A-Z => 10-35
            }
            numericValue = numericValue.multiply(BigInteger.valueOf(36)).add(BigInteger.valueOf(value));
        }
        return numericValue;
    }

    /**
     * Converts a base 36 BigInteger back into a 12-character ISIN string.
     *
     * @param value BigInteger value representing an ISIN
     * @return The corresponding ISIN string
     * @throws IllegalArgumentException If value is out of range
     */
    public static String uint256ToIsin(BigInteger value) {
        if (value.compareTo(BigInteger.ZERO) < 0 || value.compareTo(BigInteger.valueOf(MAX_ISIN_VALUE)) > 0) {
            throw new IllegalArgumentException("Invalid uint256 to decode as ISIN");
        }

        StringBuilder isin = new StringBuilder();
        BigInteger base = BigInteger.valueOf(36);

        while (value.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divAndRem = value.divideAndRemainder(base);
            int remainder = divAndRem[1].intValue();
            char ch = (remainder < 10) ? (char) ('0' + remainder) : (char) ('A' + (remainder - 10));
            isin.insert(0, ch);
            value = divAndRem[0];
        }

        // Pad with leading zeros
        while (isin.length() < 12) {
            isin.insert(0, '0');
        }

        return isin.toString();
    }
}
package com.webmobix.util;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class ISINConverterTest {
    
    @Test
    void testEncodeValidISINWithLetters() {
        String isin = "ABCDEFGHIJKL";
        BigInteger encoded = ISINConverter.isinToUint256(isin);
        String decoded = ISINConverter.uint256ToIsin(encoded);
        assertEquals(isin, decoded);
    }

    @Test
    void testEncodeValidISINWithNumbers() {
        String isin = "012345678901";
        BigInteger encoded = ISINConverter.isinToUint256(isin);
        String decoded = ISINConverter.uint256ToIsin(encoded);
        assertEquals(isin, decoded);
    }

    @Test
    void testEncodeValidISINWithMixedCharacters() {
        String isin = "US0378331005";
        BigInteger encoded = ISINConverter.isinToUint256(isin);
        String decoded = ISINConverter.uint256ToIsin(encoded);
        assertEquals(isin, decoded);
    }

    @Test
    void testHandleLowercaseISIN() {
        String lowercaseIsin = "us0378331005";
        BigInteger encoded = ISINConverter.isinToUint256(lowercaseIsin);
        String decoded = ISINConverter.uint256ToIsin(encoded);
        assertEquals("US0378331005", decoded);
    }

    @Test
    void testRejectInvalidISINCharacters() {
        assertThrows(IllegalArgumentException.class, () -> {
            ISINConverter.isinToUint256("US03783310$5");
        });
    }

    @Test
    void testRejectShortISIN() {
        assertThrows(IllegalArgumentException.class, () -> {
            ISINConverter.isinToUint256("US03783310");
        });
    }

    @Test
    void testRejectLongISIN() {
        assertThrows(IllegalArgumentException.class, () -> {
            ISINConverter.isinToUint256("US03783310051");
        });
    }

    @Test
    void testRejectTooLargeValue() {
        BigInteger tooLarge = BigInteger.valueOf(36).pow(13); // Exceeds 36^12
        assertThrows(IllegalArgumentException.class, () -> {
            ISINConverter.uint256ToIsin(tooLarge);
        });
    }

    @Test
    void testDecodeZeroValue() {
        BigInteger zero = BigInteger.ZERO;
        String decoded = ISINConverter.uint256ToIsin(zero);
        assertEquals("000000000000", decoded);
    }

    @Test
    void testRoundTripEncodingDecoding() {
        String[] testCases = {
            "US0378331005",
            "DE000BAY0017",
            "GB0002374006",
            "FR0000131104",
            "CH0012221716",
            "AAAAAAAAAAAA",
            "000000000000"
        };
        for (String isin : testCases) {
            BigInteger encoded = ISINConverter.isinToUint256(isin);
            String decoded = ISINConverter.uint256ToIsin(encoded);
            assertEquals(isin, decoded);
        }
    }

}

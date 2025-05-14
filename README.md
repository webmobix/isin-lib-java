# isin-lib-java

A Java library for reversible conversion between International Securities Identification Numbers (ISINs) and java.math.BigInteger (representing uint256) values using base36 encoding.

This library is designed for Java-based backend systems and enterprise applications that need to interface with blockchain systems or manage financial data that requires mapping ISINs to uint256 identifiers.

## Why This Library Matters

Financial instruments are globally identified by ISINs, which are 12-character alphanumeric codes. Smart contracts on EVM blockchains, however, often use uint256 integers as unique identifiers. This fundamental difference in data types poses a challenge for integration. isin-lib-java addresses this by providing a robust, reversible method to convert ISINs to BigInteger (representing uint256) and back, using base36 encoding. This ensures a reliable one-to-one mapping, crucial for bringing traditional financial assets into blockchain environments.

For a deeper dive into the challenge and our comprehensive solution, please read our article:
[https://webmobix.com](https://webmobix.com)

## Features

- Converts ISIN strings to java.math.BigInteger.
- Converts java.math.BigInteger back to ISIN strings.
- Utilizes base36 encoding for the conversion logic. [5, 6]
- Includes validation for ISIN format (length, alphanumeric characters).
- Ensures correct padding for ISINs generated from BigInteger.

## Installation

### Maven

Add the following dependency to your pom.xml:xml

```xml
<dependency>
    <groupId>com.webmobix</groupId>
    <artifactId>isin-lib-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle` file:

```gradle
implementation 'com.webmobix:isin-lib-java:1.0.0'
```

## Usage

```java
import com.webmobix.util.ISINConverter; // Based on provided file path
import java.math.BigInteger;

public class Main {
    public static void main(String args) {
        String isin = "US0378331005"; // Example Apple Inc. ISIN

        // Convert ISIN to BigInteger (uint256)
        try {
            BigInteger uint256Value = ISINConverter.isinToUint256(isin);
            System.out.println("ISIN: " + isin);
            System.out.println("uint256 (BigInteger): " + uint256Value.toString());

            // Convert BigInteger (uint256) back to ISIN
            String originalIsin = ISINConverter.uint256ToIsin(uint256Value);
            System.out.println("Converted back to ISIN: " + originalIsin);
            // Expected: US0378331005

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Example with a different BigInteger
        // This value corresponds to 'US0378331005'
        BigInteger exampleUint256 = new BigInteger("33366803344263005");
        try {
            String derivedIsin = ISINConverter.uint256ToIsin(exampleUint256);
            System.out.println("BigInteger " + exampleUint256.toString() + " as ISIN: " + derivedIsin);
            // Expected: US0378331005
        } catch (IllegalArgumentException e) {
            System.err.println("Error converting BigInteger to ISIN: " + e.getMessage());
        }
    }
}
```

## API

`public static BigInteger isinToUint256(String isin)`

- Takes a 12-character alphanumeric ISIN string.
- Returns its BigInteger representation.
- Throws IllegalArgumentException for invalid ISIN formats.

`public static String uint256ToIsin(BigInteger value)`

- Takes a BigInteger value.
- Returns the corresponding 12-character ISIN string (padded with leading '0's if necessary).
- Throws IllegalArgumentException if the value is too large or negative.

## Sister Libraries

This library is part of a suite designed to provide consistent ISIN <=> uint256 conversion across different environments:

- isin-lib-js: For JavaScript/TypeScript front-end or Node.js applications.
  [https://github.com/webmobix/isin-lib-js](https://github.com/webmobix/isin-lib-js)
- isin-lib-solidity: For direct use in EVM smart contracts.
  [https://github.com/webmobix/isin-lib-solidity](https://github.com/webmobix/isin-lib-solidity)

## Contributing

Contributions are welcome! Please feel free to submit issues or pull requests.

## License

This project is licensed under the Apache License 2.0. See the LICENSE file for details.

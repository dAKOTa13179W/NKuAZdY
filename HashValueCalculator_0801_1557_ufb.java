// 代码生成时间: 2025-08-01 15:57:13
 * given strings using various hashing algorithms like SHA-256, MD5, etc.
 */

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HashValueCalculator {
    private static final Logger LOGGER = Logger.getLogger(HashValueCalculator.class.getName());

    /**
     * Main method to run the hash value calculator.
     * 
     * @param args Program arguments
     */
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the string to hash: ");
            String inputString = scanner.nextLine();
            System.out.print("Choose the hashing algorithm (SHA-256, MD5): ");
            String algorithm = scanner.nextLine();
            
            String hashValue = calculateHash(inputString, algorithm);
            System.out.println("The hash value is: " + hashValue);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error calculating hash value", e);
        }
    }

    /**
     * Calculates the hash value for the given string using the specified algorithm.
     * 
     * @param inputString The input string to hash
     * @param algorithm The hashing algorithm to use (e.g., SHA-256, MD5)
     * @return The calculated hash value
     * @throws Exception If an error occurs during hashing
     */
    public static String calculateHash(String inputString, String algorithm) throws Exception {
        if (inputString == null || inputString.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        switch (algorithm.toUpperCase()) {
            case "SHA-256":
                return DigestUtils.sha256Hex(inputString);
            case "MD5":
                return DigestUtils.md5Hex(inputString);
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }
    }
}

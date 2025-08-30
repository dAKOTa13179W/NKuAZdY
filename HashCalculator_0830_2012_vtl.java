// 代码生成时间: 2025-08-30 20:12:46
import org.apache.commons.codec.digest.DigestUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * HashCalculator is a utility class that provides functionality to calculate
 * various types of hash values for given input.
 */
public class HashCalculator {

    /**
     * Calculates and returns the SHA-256 hash value of the given input.
     *
     * @param input The input string to calculate the hash value for.
     * @return The SHA-256 hash value as a hexadecimal string.
     */
    public static String calculateSHA256Hash(String input) {
        try {
            return DigestUtils.sha256Hex(input);
        } catch (Exception e) {
            System.err.println("Error calculating SHA-256 hash: " + e.getMessage());
            return null;
        }
    }

    /**
     * Calculates and returns the SHA-256 hash value of the given file.
     *
     * @param filePath The path to the file to calculate the hash value for.
     * @return The SHA-256 hash value as a hexadecimal string.
     */
    public static String calculateFileSHA256Hash(String filePath) {
        try {
            byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
            return DigestUtils.sha256Hex(fileContent);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error calculating SHA-256 hash for file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Main method to test the HashCalculator class.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the string to calculate SHA-256 hash: ");
        String input = scanner.nextLine();
        String hash = calculateSHA256Hash(input);
        if (hash != null) {
            System.out.println("SHA-256 hash of the input: " + hash);
        }

        System.out.println("Enter the path to the file to calculate SHA-256 hash: ");
        String filePath = scanner.nextLine();
        hash = calculateFileSHA256Hash(filePath);
        if (hash != null) {
            System.out.println("SHA-256 hash of the file: " + hash);
        }
    }
}

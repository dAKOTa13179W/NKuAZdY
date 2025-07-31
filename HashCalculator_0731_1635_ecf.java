// 代码生成时间: 2025-07-31 16:35:37
 * A utility class to calculate hash values for given input strings.
 * Supports SHA-256 and MD5 hash algorithms.
 *
 * @author Your Name
 * @version 1.0
 */
import org.apache.commons.codec.digest.DigestUtils;
import java.security.NoSuchAlgorithmException;

public class HashCalculator {

    /**
     * Calculates SHA-256 hash for a given input string.
     *
     * @param input The input string to be hashed.
     * @return The SHA-256 hash of the input string.
     * @throws NoSuchAlgorithmException If the SHA-256 algorithm is not available.
     */
    public static String calculateSHA256(String input) throws NoSuchAlgorithmException {
        return DigestUtils.sha256Hex(input);
    }

    /**
     * Calculates MD5 hash for a given input string.
     *
     * @param input The input string to be hashed.
     * @return The MD5 hash of the input string.
     * @throws NoSuchAlgorithmException If the MD5 algorithm is not available.
     */
    public static String calculateMD5(String input) throws NoSuchAlgorithmException {
        return DigestUtils.md5Hex(input);
    }

    /**
     * Main method to test the hash calculation functionality.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            String input = "Hello, World!";
            System.out.println("SHA-256 Hash: " + calculateSHA256(input));
            System.out.println("MD5 Hash: " + calculateMD5(input));
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error calculating hash: " + e.getMessage());
        }
    }
}
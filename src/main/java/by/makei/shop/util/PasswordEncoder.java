package by.makei.shop.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD2 - deprecated
 * MD5 - deprecated
 * SHA-1 - deprecated
 * SHA-224
 * SHA-256
 * SHA-384
 * SHA-512/224
 * SHA-512/256
 * SHA3-224
 * SHA3-256
 * SHA3-384
 * SHA3-512
 * https://docs.oracle.com/en/java/javase/17/docs/specs/security/standard-names.html
 */


public class PasswordEncoder {
    private static final Logger logger = LogManager.getLogger();
    private static final String ENCODING_ALGORITHM = "SHA3-256";
    private static final String CHARSET_NAME = "UTF-8";
    private static final int PASSWORD_HASH_MAX_SIZE_VALUE = 45;

    /**
     * generate hash with SHA3-256 algorithm and cut it up to 45 chars
     * @param password - String as password
     * @return encoded String with length 45
     */
    public static String getHashedPassword(String password){
    String result = null;
    try{
    MessageDigest messageDigest = MessageDigest.getInstance(ENCODING_ALGORITHM);
    byte[] data1 = password.getBytes(CHARSET_NAME);
    messageDigest.update(data1);
    byte[] salt = {23, 34};// improve safety
    messageDigest.update(salt);// add additional security
    byte[] digest = messageDigest.digest();
    result =  bytesToHex(digest).substring(0,PASSWORD_HASH_MAX_SIZE_VALUE);

    } catch (UnsupportedEncodingException |  NoSuchAlgorithmException e) {
        logger.log(Level.WARN,"encryption exception can't be reached");
    }
    return result;
}

    /**
     * convert input bytes array to it's HEX String representation
     * @param hash
     * @return converted String
     */
    static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte h : hash) {
            String hex = Integer.toHexString(0xff & h);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

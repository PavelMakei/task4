package by.makei.shop.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import static by.makei.shop.util.PasswordEncoder.bytesToHex;

public final class CodeGenerator {
    private static final Logger logger = LogManager.getLogger();
    private CodeGenerator(){}

    public  static String generateCode(){
        String digest = null;
        try {
            MessageDigest salt = MessageDigest.getInstance("SHA-1");
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
            digest = bytesToHex(salt.digest());
        } catch (UnsupportedEncodingException |  NoSuchAlgorithmException e) {
           logger.log(Level.WARN, "can't be reached");
        }
        return digest.substring(0,10);
    }
}

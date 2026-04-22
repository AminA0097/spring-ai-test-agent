package com.webapp.arvand.coreback.Utils;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CryptoUtil {

    private static final String ALGORITHM = "AES";

    private static final String SECRET = System.getenv("github.security.secret");

    private static SecretKey getKey() {
        byte[] decodedKey = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    public static String encryptSecret(String secret) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getKey());

            byte[] encrypted = cipher.doFinal(secret.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getKey());

            byte[] decoded = Base64.getDecoder().decode(encrypted);
            return new String(cipher.doFinal(decoded));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

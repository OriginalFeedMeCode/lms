package com.leadnile.organization.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AesUtil {

    private final int ITERATIONS = 1000;
    private final int KEYSIZE = 128;
    private final Cipher cipher;
    public static final String RANDOM_STRING = "shoaibString";

    @Value("${PASSPHRASE}")
    protected String PASSPHRASE;

    @Value("${IV}")
    protected String IV;

    @Value("${SALT}")
    protected String SALT;

    public AesUtil() {
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("AesUtil :: Exception caught :: ", e);
            throw fail(e);
        }
    }

    public String encryptExternalParam(String planText) {
        if (planText == null) {
            return StringUtils.EMPTY;
        }
        String encryptedText = encrypt(SALT, IV, PASSPHRASE, planText);
        encryptedText = encryptedText.replaceAll("\\+", "%20");
        return encryptedText;
    }

    public String decryptExternalParam(String encryptedText) {
        if (encryptedText == null) {
            return StringUtils.EMPTY;
        }
        encryptedText = encryptedText.replaceAll("%25", "%");
        encryptedText = encryptedText.replaceAll("%26", "&");
        encryptedText = encryptedText.replaceAll(StringUtils.SPACE, "+");
        encryptedText = encryptedText.replaceAll("%20", "+");
        String decryptedText = decrypt(SALT, IV, PASSPHRASE, encryptedText);
        return decryptedText.trim();
    }

    public String encrypt(String salt, String iv, String passphrase, String planText) {
        try {
            SecretKey key = generateKey(salt, passphrase);
            byte[] encrypted = doFinal(Cipher.ENCRYPT_MODE, key, iv, planText.getBytes("UTF-8"));
            return base64(encrypted).trim();
        } catch (UnsupportedEncodingException e) {
            log.error("encrypt :: Exception caught :: ", e);
        }
        return StringUtils.EMPTY;
    }

    public String decrypt(String salt, String iv, String passphrase, String ciphertext) {
        try {
            SecretKey key = generateKey(salt, passphrase);
            byte[] decrypted = doFinal(Cipher.DECRYPT_MODE, key, iv, base64(ciphertext));
            if (decrypted == null) {
                return StringUtils.EMPTY;
            }
            return new String(decrypted, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("decrypt :: Exception caught ::  ", e);
        }
        return StringUtils.EMPTY;
    }

    private byte[] doFinal(int encryptMode, SecretKey key, String iv, byte[] bytes) {
        try {
            cipher.init(encryptMode, key, new IvParameterSpec(hex(iv)));
            return cipher.doFinal(bytes);
        } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            log.error("doFinal :: Exception caught :: ", e);
        }
        return null;
    }

    private SecretKey generateKey(String salt, String passphrase) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), ITERATIONS, KEYSIZE);
            SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return key;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("generateKey :: Exception caught :: ", e);
        }
        return null;
    }

    public static String random(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return hex(salt);
    }

    public static String base64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64(String str) {
        return Base64.decodeBase64(str);
    }

    public static String hex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public static byte[] hex(String str) {
        try {
            return Hex.decodeHex(str.toCharArray());
        } catch (DecoderException e) {
            log.error("hex :: Exception caught :: ");
            throw new IllegalStateException(e);
        }
    }

    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }

}

package com.encdec.utils;

import android.content.Context;

import java.security.Key;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Cryptor {

    public static String encryptString(String secretKeyString, String msgContentString, Context context) {
        byte[] returnArray = null;
        try {
            Key key = generateKey(secretKeyString);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            returnArray = cipher.doFinal(msgContentString.getBytes());
        } catch (Exception e) {
            ApplicationUtils.showToast(context,"Something went wrong!" );
        } finally {
            return returnArray != null ? getBase64String(returnArray) : null;
        }
    }

    public static String decryptString(String secretKeyString, String content, Context context) {
        String plainText = null;
        byte[] input = getStringFromBase64(content);

        try {
            Key key = generateKey(secretKeyString);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plaintextArr = cipher.doFinal(input);
            plainText = new String(plaintextArr, "UTF-8");
            return plainText;
        } catch (BadPaddingException bpe) {
            ApplicationUtils.showToast(context,"Invalid Decryption key" );
        } catch(Exception e){
            ApplicationUtils.showToast(context,"Something went wrong" );
        }finally {
            return plainText;
        }
    }

    private static Key generateKey(String secretKeyString) throws Exception {
        Key key = new SecretKeySpec(secretKeyString.getBytes(), "AES");
        return key;
    }

    public static String getBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] getStringFromBase64(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }
}

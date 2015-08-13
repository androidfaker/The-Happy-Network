package com.baozi.utils.Encrypt;

/**
 * Created by User on 2015/7/8.
 */

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DES {

    private static String encrypt(String str) {

        byte[] enc = null;
        try {
            enc = desEncrypt(str, "iotcrcB4");
        } catch (Exception ex) {
        }
        String s = new BASE64Encoder().encode(enc);
        return s;
    }

    private static byte[] desEncrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    /**
     * ����
     */
    public static String getDes(String s) {
        //DES����
        return encrypt(s)
//                .replace("%", "%25")
//                .replace("+", "%2B")
//                .replace("/", "%2F")
//                .replace("?", "%3F")
//                .replace("#", "%23")
//                .replace("&", "%26")
                .replace("\n", "");
//        return DES.encrypt("NPCUserRegister?username=");

    }
}

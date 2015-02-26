package com.dream.net.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by sunlongxiao on 2/19/15.
 */
public class MD5Utils {

    public  static String encrypt(String password){

        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();

            for (byte b:result){
                int num = b & 0xff;
                String str = Integer.toHexString(num);
                if (str.length()==1){
                    buffer.append("0");
                }
                buffer.append(str);
            }

            return buffer.toString();



        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

}

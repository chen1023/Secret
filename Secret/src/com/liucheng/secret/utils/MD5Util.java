package com.liucheng.secret.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String md5(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(input);
        return conversion(b);
    }

    public static String md5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return md5(s.getBytes("UTF8"));
    }

	public static String md5File(String filename) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        DigestInputStream in
                = new DigestInputStream(new FileInputStream(filename), md);
        byte[] buffer = new byte[8192];
        while (in.read(buffer) != -1) {
            md.update(buffer);
        }
        byte[] raw = md.digest();
        in.close();
        return conversion(raw);
    }

    private static String conversion(byte[] b) {
        String s = "";
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
            "b", "c", "d", "e", "f"};
        for (int i = 0; i < b.length; i++) {
            int n = b[i];
            if (n < 0) {
                n = n + 256;
            }
            int d1 = n / 16;
            int d2 = n % 16;
            s = s + hex[d1] + hex[d2];
        }
        return s;
    }
}

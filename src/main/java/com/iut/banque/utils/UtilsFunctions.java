package com.iut.banque.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilsFunctions {

    private UtilsFunctions(){}

    // Hashe l'input en SHA-512, handle de l'exception necessaire
    public static String sha512Hash(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] hashed = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b : hashed) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}

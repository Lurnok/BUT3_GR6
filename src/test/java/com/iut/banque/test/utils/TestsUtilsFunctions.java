package com.iut.banque.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.NoSuchAlgorithmException;

import com.iut.banque.utils.UtilsFunctions;
import org.junit.Test;

public class TestsUtilsFunctions {

    @Test
    public void testSha512Hash() {
        try {
            String hashed = UtilsFunctions.sha512Hash("aaa");
            assertEquals("d6f644b19812e97b5d871658d6d3400ecd4787faeb9b8990c1e7608288664be77257104a58d033bcf1a0e0945ff06468ebe53e2dff36e248424c7273117dac09",hashed);
        } catch (NoSuchAlgorithmException e) {
            fail("NoSuchAlgorithmException");
        }
    }
}

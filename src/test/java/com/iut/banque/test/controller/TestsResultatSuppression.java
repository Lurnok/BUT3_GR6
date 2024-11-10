package com.iut.banque.test.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import com.iut.banque.controller.ResultatSuppression;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteSansDecouvert;


public class TestsResultatSuppression {

    private ResultatSuppression resultatSuppression;
    private Client client;
    private Compte compte;

    @Before
    public void setUp() throws IllegalFormatException {
        
        resultatSuppression = new ResultatSuppression();
        client = new Client();  
        try {
            compte = new CompteSansDecouvert("sabjdölsjnflsöjnf", 40, client);
        } catch (IllegalFormatException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSetAndGetClient() {
        
        resultatSuppression.setClient(client);
        assertEquals("Client should be set and returned correctly.", client, resultatSuppression.getClient());
    }

    @Test
    public void testSetAndGetCompte() {
        
        resultatSuppression.setCompte(compte);
        assertEquals("Compte should be set and returned correctly.", compte, resultatSuppression.getCompte());
    }

    @Test
    public void testSetAndGetCompteInfo() {
        
        String compteInfo = "Account info";
        resultatSuppression.setCompteInfo(compteInfo);
        assertEquals("Compte info should be set and returned correctly.", compteInfo, resultatSuppression.getCompteInfo());
    }

    @Test
    public void testSetAndGetUserInfo() {
        
        String userInfo = "User info";
        resultatSuppression.setUserInfo(userInfo);
        assertEquals("User info should be set and returned correctly.", userInfo, resultatSuppression.getUserInfo());
    }

    @Test
    public void testSetAndGetErrorMessage() {
        
        String errorMessage = "Error occurred";
        resultatSuppression.setErrorMessage(errorMessage);
        assertEquals("Error message should be set and returned correctly.", errorMessage, resultatSuppression.getErrorMessage());
    }

    @Test
    public void testSetAndGetError() {
        
        resultatSuppression.setError(true);
        assertTrue("Error flag should be set correctly.", resultatSuppression.isError());
        
        resultatSuppression.setError(false);
        assertFalse("Error flag should be set correctly.", resultatSuppression.isError());
    }

    @Test
    public void testSetAndGetAccount() {
        
        resultatSuppression.setAccount(true);
        assertTrue("isAccount flag should be set correctly.", resultatSuppression.isAccount());
        
        resultatSuppression.setAccount(false);
        assertFalse("isAccount flag should be set correctly.", resultatSuppression.isAccount());
    }
}

package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.modele.Utilisateur;

public class TestsUtilisateurs {

    private Utilisateur utilisateur;

    @Before
    public void setUp() {
        utilisateur = new UtilisateurConcrete("Doe", "John", "123 Street", true, "jdoe", "password");
    }

    @Test
    public void testUtilisateurConstructorWithParameters() {
        assertNotNull(utilisateur);
        assertEquals("Doe", utilisateur.getNom());
        assertEquals("John", utilisateur.getPrenom());
        assertEquals("123 Street", utilisateur.getAdresse());
        assertEquals(true, utilisateur.isMale());
        assertEquals("jdoe", utilisateur.getUserId());
        assertEquals("password", utilisateur.getUserPwd());
    }

    @Test
    public void testUtilisateurNoArgConstructor() {
        Utilisateur utilisateur = new UtilisateurConcrete();
        assertNotNull(utilisateur);
    }

    @Test
    public void testGettersAndSetters() {
        utilisateur.setNom("Smith");
        assertEquals("Smith", utilisateur.getNom());

        utilisateur.setPrenom("Jane");
        assertEquals("Jane", utilisateur.getPrenom());

        utilisateur.setAdresse("456 Avenue");
        assertEquals("456 Avenue", utilisateur.getAdresse());

        utilisateur.setMale(false);
        assertEquals(false, utilisateur.isMale());

        utilisateur.setUserId("jsmith");
        assertEquals("jsmith", utilisateur.getUserId());

        utilisateur.setUserPwd("newpassword");
        assertEquals("newpassword", utilisateur.getUserPwd());
    }

    @Test
    public void testToString() {
        String expected = "Utilisateur [userId=jdoe, nom=Doe, prenom=John, adresse=123 Street, male=true, userPwd=password]";
        assertEquals(expected, utilisateur.toString());
    }

    private static class UtilisateurConcrete extends Utilisateur {
        public UtilisateurConcrete(String nom, String prenom, String adresse, boolean male, String userId, String userPwd) {
            super(nom, prenom, adresse, male, userId, userPwd);
        }

        public UtilisateurConcrete() {
            super();
        }
    }
}

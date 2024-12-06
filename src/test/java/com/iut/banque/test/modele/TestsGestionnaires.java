package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;


@RunWith(MockitoJUnitRunner.class)
public class TestsGestionnaires {

    @InjectMocks
    private Gestionnaire gestionnaire;

    @Mock
    private Utilisateur utilisateur;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGestionnaireConstructorWithParameters() {
        try {
            Gestionnaire gestionnaire = new Gestionnaire("Doe", "John", "123 Street", true, "jdoe", "password");
            assertNotNull(gestionnaire);
            assertEquals("Doe", gestionnaire.getNom());
            assertEquals("John", gestionnaire.getPrenom());
            assertEquals("123 Street", gestionnaire.getAdresse());
            assertEquals(true, gestionnaire.isMale());
            assertEquals("jdoe", gestionnaire.getUserId());
            assertEquals("password", gestionnaire.getUserPwd());
        } catch (IllegalFormatException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGestionnaireConstructorWithEmptyUserId() throws IllegalFormatException {
        new Gestionnaire("Doe", "John", "123 Street", true, "", "password");
    }

    @Test
    public void testGestionnaireNoArgConstructor() {
        Gestionnaire gestionnaire = new Gestionnaire();
        assertNotNull(gestionnaire);
    }

    @Test
    public void testToString() {
        try {
            Gestionnaire gestionnaire = new Gestionnaire("Doe", "John", "123 Street", true, "jdoe", "password");
            String expected = "Gestionnaire [nom=Doe, prenom=John, adresse=123 Street, male=true, userId=jdoe, userPwd=password]";
            assertEquals(expected, gestionnaire.toString());
        } catch (IllegalFormatException e) {
            fail("Exception should not be thrown");
        }
    }
}

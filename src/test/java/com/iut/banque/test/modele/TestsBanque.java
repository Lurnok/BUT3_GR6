package com.iut.banque.test.modele;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;

import com.iut.banque.modele.Banque;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.CompteSansDecouvert;
import com.iut.banque.modele.Gestionnaire;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TestsBanque {

    @InjectMocks
    private Banque banque;

    @Mock
    private Client client;

    @Mock
    private Gestionnaire gestionnaire;

    @Mock
    private Compte compte;

    @Mock
    private CompteAvecDecouvert compteAvecDecouvert;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        banque = new Banque();
    }

    @Test
    public void testGetClients() {
        Map<String, Client> clients = new HashMap<>();
        clients.put("1", client);
        banque.setClients(clients);
        assertEquals(clients, banque.getClients());
    }

    @Test
    public void testSetClients() {
        Map<String, Client> clients = new HashMap<>();
        clients.put("1", client);
        banque.setClients(clients);
        assertEquals(clients, banque.getClients());
    }

    @Test
    public void testGetGestionnaires() {
        Map<String, Gestionnaire> gestionnaires = new HashMap<>();
        gestionnaires.put("1", gestionnaire);
        banque.setGestionnaires(gestionnaires);
        assertEquals(gestionnaires, banque.getGestionnaires());
    }

    @Test
    public void testSetGestionnaires() {
        Map<String, Gestionnaire> gestionnaires = new HashMap<>();
        gestionnaires.put("1", gestionnaire);
        banque.setGestionnaires(gestionnaires);
        assertEquals(gestionnaires, banque.getGestionnaires());
    }

    @Test
    public void testGetAccounts() {
        Map<String, Compte> accounts = new HashMap<>();
        accounts.put("1", compte);
        banque.setAccounts(accounts);
        assertEquals(accounts, banque.getAccounts());
    }

    @Test
    public void testSetAccounts() {
        Map<String, Compte> accounts = new HashMap<>();
        accounts.put("1", compte);
        banque.setAccounts(accounts);
        assertEquals(accounts, banque.getAccounts());
    }

    @Test
    public void testDebiter() {
        try {
            banque.debiter(compte, 100.0);
        } catch (InsufficientFundsException | IllegalFormatException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testCrediter() {
        try {
            banque.crediter(compte, 100.0);
        } catch (IllegalFormatException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testDeleteUser() {
        Map<String, Client> clients = new HashMap<>();
        clients.put("1", client);
        banque.setClients(clients);
        banque.deleteUser("1");
        assertFalse(banque.getClients().containsKey("1"));
    }

    @Test
    public void testChangeDecouvert() {
        try {
            banque.changeDecouvert(compteAvecDecouvert, 500.0);
        } catch (IllegalFormatException | IllegalOperationException e) {
            fail("Exception should not be thrown");
        }
    }
}

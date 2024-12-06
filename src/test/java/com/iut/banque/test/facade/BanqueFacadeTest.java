package com.iut.banque.test.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.iut.banque.constants.LoginConstants;
import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.facade.BanqueManager;
import com.iut.banque.facade.LoginManager;


import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class BanqueFacadeTest {

    @InjectMocks
    private BanqueFacade banqueFacade;

    @Mock
    private BanqueManager banqueManager;

    @Mock
    private LoginManager loginManager;

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private Gestionnaire gestionnaire;

    @Mock
    private Client client;

    @Mock
    private Compte compte;

    @Mock
    private CompteAvecDecouvert compteAvecDecouvert;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        banqueFacade = new BanqueFacade(loginManager, banqueManager);
    }

    @Test
    public void testGetConnectedUser() {
        when(loginManager.getConnectedUser()).thenReturn(utilisateur);
        Utilisateur connectedUser = banqueFacade.getConnectedUser();
        assertNotNull(connectedUser);
        assertEquals(utilisateur, connectedUser);
    }

    @Test
    public void testTryLogin() {
        when(loginManager.tryLogin("userCde", "userPwd")).thenReturn(LoginConstants.MANAGER_IS_CONNECTED);
        int result = banqueFacade.tryLogin("userCde", "userPwd");
        assertEquals(LoginConstants.MANAGER_IS_CONNECTED, result);
        verify(banqueManager).loadAllClients();
    }

    @Test
    public void testCrediter() throws IllegalFormatException {
        banqueFacade.crediter(compte, 100.0);
        verify(banqueManager).crediter(compte, 100.0);
    }

    @Test
    public void testDebiter() throws InsufficientFundsException, IllegalFormatException {
        banqueFacade.debiter(compte, 100.0);
        verify(banqueManager).debiter(compte, 100.0);
    }

    @Test
    public void testGetAllClients() {
        Map<String, Client> clients = new HashMap<>();
        clients.put("1", client);
        when(banqueManager.getAllClients()).thenReturn(clients);
        Map<String, Client> result = banqueFacade.getAllClients();
        assertEquals(clients, result);
    }

    @Test
    public void testLogout() {
        banqueFacade.logout();
        verify(loginManager).logout();
    }

    @Test
    public void testCreateAccount() throws TechnicalException, IllegalFormatException {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.createAccount("123", client);
        verify(banqueManager).createAccount("123", client);
    }

    @Test
    public void testCreateAccountWithDecouvert() throws TechnicalException, IllegalFormatException, IllegalOperationException {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.createAccount("123", client, 500.0);
        verify(banqueManager).createAccount("123", client, 500.0);
    }

    @Test
    public void testDeleteAccount() throws IllegalOperationException, TechnicalException {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.deleteAccount(compte);
        verify(banqueManager).deleteAccount(compte);
    }

    @Test
    public void testCreateManager() throws TechnicalException, IllegalArgumentException, IllegalFormatException {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.createManager("userId", "userPwd", "nom", "prenom", "adresse", true);
        verify(banqueManager).createManager("userId", "userPwd", "nom", "prenom", "adresse", true);
    }

    @Test
    public void testCreateClient() throws IllegalOperationException, TechnicalException, IllegalArgumentException, IllegalFormatException {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.createClient("userId", "userPwd", "nom", "prenom", "adresse", true, "numeroClient");
        verify(banqueManager).createClient("userId", "userPwd", "nom", "prenom", "adresse", true, "numeroClient");
    }

    @Test
    public void testDeleteUser() throws IllegalOperationException, TechnicalException {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.deleteUser(utilisateur);
        verify(banqueManager).deleteUser(utilisateur);
    }

    @Test
    public void testLoadClients() {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.loadClients();
        verify(banqueManager).loadAllClients();
    }

    @Test
    public void testGetCompte() {
        when(banqueManager.getAccountById("123")).thenReturn(compte);
        Compte result = banqueFacade.getCompte("123");
        assertEquals(compte, result);
    }

    @Test
    public void testChangeDecouvert() throws IllegalFormatException, IllegalOperationException {
        when(loginManager.getConnectedUser()).thenReturn(gestionnaire);
        banqueFacade.changeDecouvert(compteAvecDecouvert, 500.0);
        verify(banqueManager).changeDecouvert(compteAvecDecouvert, 500.0);
    }
}

package com.iut.banque.test.facade;

import com.iut.banque.constants.LoginConstants;


import com.iut.banque.facade.LoginManager;
import com.iut.banque.modele.Utilisateur;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

//@RunWith indique à JUnit de prendre le class runner de Spirng
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration permet de charger le context utilisé pendant les tests.
// Par défault (si aucun argument n'est précisé), cherche le fichier
/// src/com/iut/banque/test/TestsDaoHibernate-context.xml
@ContextConfiguration("/test/resources/TestsBanqueManager-context.xml")
@Transactional("transactionManager")
public class TestsLoginManager {

    @Autowired
    private LoginManager lm;

    @Test
    public void testLoginManager() {
        int user = lm.tryLogin("admin","adminpass");
        assertEquals(LoginConstants.MANAGER_IS_CONNECTED,user);
    }

    @Test
    public void testLoginClient() {
        int user = lm.tryLogin("g.descomptes","TEST PASS");
        assertEquals(LoginConstants.USER_IS_CONNECTED,user);
    }

    @Test
    public void testLoginFail() {
        int user = lm.tryLogin("aaa","fauxmdp");
        assertEquals(LoginConstants.LOGIN_FAILED,user);
    }

    @Test
    public void testGetConnectedUser(){
        lm.tryLogin("admin","adminpass");
        Utilisateur u = lm.getConnectedUser();
        assertNotEquals(null,u);
    }

    @Test
    public void testSetConnectedUser(){
        lm.tryLogin("admin","adminpass");
        Utilisateur u = lm.getConnectedUser();
        lm.setCurrentUser(u);
        assertEquals(u,lm.getConnectedUser());
    }
}

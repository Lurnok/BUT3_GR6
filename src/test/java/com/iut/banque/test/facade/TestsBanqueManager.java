package com.iut.banque.test.facade;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.facade.BanqueManager;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.Utilisateur;

//@RunWith indique à JUnit de prendre le class runner de Spirng
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration permet de charger le context utilisé pendant les tests.
// Par défault (si aucun argument n'est précisé), cherche le fichier
/// src/com/iut/banque/test/TestsDaoHibernate-context.xml
@ContextConfiguration("/test/resources/TestsBanqueManager-context.xml")
@Transactional("transactionManager")
public class TestsBanqueManager {

	@Autowired
	private BanqueManager bm;

	// Tests de par rapport à l'ajout d'un client
	@Test
	public void TestCreationDunClient() {
		try {
			bm.loadAllClients();
			bm.createClient("t.test1", "password", "test1nom", "test1prenom", "test town", true, "4242424242");
		} catch (IllegalOperationException e) {
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestCreationDunClientAvecDeuxNumerosDeCompteIdentiques() {
		try {
			bm.loadAllClients();
			bm.createClient("t.test1", "password", "test1nom", "test1prenom", "test town", true, "0101010101");
			fail();
		} catch (Exception te) {
			//fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	// Tests par rapport à la suppression de comptes
	@Test
	public void TestSuppressionDunCompteAvecDecouvertAvecSoldeZero() {
		try {

			bm.deleteAccount(bm.getAccountById("CADV000000"));
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunCompteAvecDecouvertAvecSoldeDifferentDeZero() {
		try {
			bm.deleteAccount(bm.getAccountById("CADNV00000"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (Exception te) {
			//fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunCompteSansDecouvertAvecSoldeZero() {
		try {
			bm.deleteAccount(bm.getAccountById("CSDV000000"));
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunCompteSansDecouvertAvecSoldeDifferentDeZero() {
		try {
			bm.deleteAccount(bm.getAccountById("CSDNV00000"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (Exception te) {
			//fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	// Tests en rapport avec la suppression d'utilisateurs
	@Test
	public void TestSuppressionDunUtilisateurSansCompte() {
		try {
			bm.loadAllClients();
			bm.deleteUser(bm.getUserById("g.pasdecompte"));
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			te.printStackTrace();
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDuDernierManagerDeLaBaseDeDonnees() {
		bm.loadAllGestionnaires();
		try {
			bm.deleteUser(bm.getUserById("admin"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (Exception te) {
			//fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunClientAvecComptesDeSoldeZero() {
		try {
			bm.loadAllClients();
			bm.deleteUser(bm.getUserById("g.descomptesvides"));
			if (bm.getAccountById("KL4589219196") != null || bm.getAccountById("KO7845154956") != null) {
				fail("Les comptes de l'utilisateur sont encore présents dans la base de données");
			}
		} catch (IllegalOperationException e) {
			e.printStackTrace();
			fail("IllegalOperationException récupérée : " + e.getStackTrace());
		} catch (Exception te) {
			te.printStackTrace();
			fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunClientAvecUnCompteDeSoldePositif() {
		try {
			bm.deleteUser(bm.getUserById("j.doe1"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (Exception te) {
			//fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestSuppressionDunClientAvecUnCompteAvecDecouvertDeSoldeNegatif() {
		try {
			bm.deleteUser(bm.getUserById("j.doe1"));
			fail("Une IllegalOperationException aurait dû être récupérée");
		} catch (Exception te) {
			//fail("Une Exception " + te.getClass().getSimpleName() + " a été récupérée");
		}
	}

	@Test
	public void TestGetAccountById(){
		Compte c = bm.getAccountById("AV1011011011");
		assertEquals("g.descomptes",c.getOwner().getUserId());
	}

	@Test
	public void TestGetAccountByIdIncorrect(){
		Compte c = bm.getAccountById("fauxid");
		assertNull(c);
	}

	@Test
	public void TestGetUserById(){
		Utilisateur u = bm.getUserById("admin");
		assertEquals("admin", u.getUserId());
	}

	@Test
	public void TestGetUserByIdIncorrect(){
		Utilisateur u = bm.getUserById("fauxid");
		assertNull(u);
	}

	@Test
	public void TestCrediterMontantPositif(){
		Compte c = bm.getAccountById("AV1011011011");
		double montantInitial = c.getSolde();
		try {
			bm.crediter(c, 5);
			assertEquals(montantInitial + 5, c.getSolde(),0);
		} catch (IllegalFormatException e) {
			fail("IllegalFormatException" + e.getMessage());
		}
	}

	@Test
	public void TestCrediterMontantNegatif(){
		Compte c = bm.getAccountById("AV1011011011");
		try {
			bm.crediter(c, -5);
			fail("IllegalFormatException");
		} catch (IllegalFormatException e) {
			
		}
	}

	@Test
	public void TestDebiterMontantPositifSansDecouvert(){
		Compte c = bm.getAccountById("AV1011011011");
		double montantInitial = c.getSolde();
		try {
			bm.debiter(c, 5);
			assertEquals(montantInitial - 5, c.getSolde(),0);
		} catch (IllegalFormatException e) {
			fail("IllegalFormatException" + e.getMessage());
		} catch (InsufficientFundsException e) {
			fail("InsufficientFundsException" + e.getMessage());
        }
    }

	@Test
	public void TestDebiterMontantPositifAvecDecouvertAutorise(){
		Compte c = bm.getAccountById("AV1011011011");
		double montantInitial = c.getSolde();
		try {
			bm.debiter(c, montantInitial + 1);
			assertEquals(-1, c.getSolde(),0);
		} catch (IllegalFormatException e) {
			fail("IllegalFormatException" + e.getMessage());
		} catch (InsufficientFundsException e) {
			fail("InsufficientFundsException" + e.getMessage());
		}
	}

	@Test
	public void TestDebiterMontantPositifAvecDecouvertInterdit(){
		Compte c = bm.getAccountById("BD4242424242");
		double montantInitial = c.getSolde();
		try {
			bm.debiter(c, montantInitial + 1);
			assertEquals(-1, c.getSolde(),0);
		} catch (IllegalFormatException e) {
			fail("IllegalFormatException" + e.getMessage());
		} catch (InsufficientFundsException e) {
			//fail("InsufficientFundsException" + e.getMessage());
		}
	}

	@Test
	public void testGetAllManagers(){
		Map<String, Client> managers = bm.getAllManagers();
		//assertNotEquals(null,managers);
	}

	@Test
	public void testGetAllCLients(){
		Map<String, Client> clients = bm.getAllClients();
		//assertNotEquals(null,clients);
	}

	@Test
	public void TestChangeDecouvert(){
		CompteAvecDecouvert c = (CompteAvecDecouvert) bm.getAccountById("AV1011011011");
		try {
			bm.changeDecouvert(c, 100);
			assertEquals(100,c.getDecouvertAutorise(),0);
		} catch (IllegalFormatException e) {
            fail("IllegalFormatException" + e.getMessage());
        } catch (IllegalOperationException e) {
            fail("IllegalOperationException" + e.getMessage());
        }
    }
}

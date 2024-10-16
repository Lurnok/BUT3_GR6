package com.iut.banque.facade;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iut.banque.exceptions.IllegalFormatException;
import com.iut.banque.exceptions.IllegalOperationException;
import com.iut.banque.exceptions.InsufficientFundsException;
import com.iut.banque.exceptions.TechnicalException;
import com.iut.banque.interfaces.IDao;
import com.iut.banque.modele.Banque;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.CompteAvecDecouvert;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.UserCreationParams;
import com.iut.banque.modele.Utilisateur;

@Component
public class BanqueManager {

	private final Banque bank;
	private final IDao dao;

	/**
	 * Constructeur du BanqueManager
	 * 
	 * @return BanqueManager : un nouvel objet BanqueManager
	 */
	@Autowired
	public BanqueManager(Banque bank, IDao dao) {
		super();
		//C'est fait mais je laisse le commentaire vu que c'était la orignalement et jsp si on peut modifier. (y'avait un t0d0 mais sonar)
		this.bank = bank;
		this.dao = dao;
	}

	/**
	 * Méthode utilisé pour les tests unitaires et ne devrait pas être utilisée
	 * ailleurs.
	 */
	public Compte getAccountById(String id) {
		return dao.getAccountById(id);
	}

	/**
	 * Méthode utilisée pour les tests unitaires et ne devrait pas être utilisée
	 * ailleurs.
	 */
	public Utilisateur getUserById(String id) {
		return dao.getUserById(id);
	}

	/**
	 * Méthode pour créditer un compte en faisant appel à la méthode créditer de
	 * l'objet bank pour mettre à jour localement, et ensuite appeler la méthode
	 * updateAccount de la DAO pour mettre à jour les données dans la base de
	 * données
	 * 
	 * @param compte
	 *            : un objet de type compte représentant le compte à créditer
	 * @param montant
	 *            : un double correspondant au montant à créditer
	 * @throws IllegalFormatException
	 *             : si le param montant est négatif
	 */
	public void crediter(Compte compte, double montant) throws IllegalFormatException {
		bank.crediter(compte, montant);
		dao.updateAccount(compte);
	}

	/**
	 * Méthode pour créditer un compte en faisant appel à la méthode créditer de
	 * l'objet bank pour mettre à jour localement, et ensuite appeler la méthode
	 * updateAccount de la DAO pour mettre à jour les données dans la base de
	 * données
	 * 
	 * @param compte
	 *            : un objet de type compte repr�sentant le compte à créditer
	 * @param montant
	 *            : un double correspondant au montant à créditer
	 * @throws IllegalFormatException
	 *             : si le param montant est négatif; InsufficientFundsException
	 *             : si les fonds sont insuffisants
	 */
	public void debiter(Compte compte, double montant) throws InsufficientFundsException, IllegalFormatException {
		bank.debiter(compte, montant);
		dao.updateAccount(compte);
	}

	/**
	 * Méthode pour mettre à jour la liste des comptes de la banque. Elle
	 * contiendra la liste de tous les comptes de la banque
	 */
	public void loadAllClients() {
		bank.setClients(dao.getAllClients());
	}

	/**
	 * Méthode pour mettre à jour la liste des gestionnaires de la banque. Elle
	 * contiendra la liste de tous les gestionnaires de la banque
	 */
	public void loadAllGestionnaires() {
		bank.setGestionnaires(dao.getAllGestionnaires());
	}

	/**
	 * Cette méthode renvoie tous les clients de la banque
	 * 
	 * @return la liste de tous les clients
	 */
	public Map<String, Client> getAllClients() {
		return bank.getClients();
	}

	/**
	 * Cette méthode renvoie tous les gestionnaires de la banque
	 * 
	 * @return la liste de tous les clients
	 */
	public Map<String, Client> getAllManagers() {
		return bank.getClients();
	}

	/**
	 * Cette méthode appelle la DAO pour cr�er un compte sans découvert dans la
	 * BdD
	 * 
	 * @param numeroCompte
	 *            String correspondant au numéro de compte à créer
	 * @param client
	 *            Client, objet correspondant à l'objet client auquel on veut
	 *            rajouter le compte
	 * @throws TechnicalException
	 * @throws IllegalFormatException
	 */
	public void createAccount(String numeroCompte, Client client) throws TechnicalException, IllegalFormatException {
		dao.createCompteSansDecouvert(0, numeroCompte, client);
	}

	/**
	 * Cette méthode appelle la DAO pour créer un compte avec découvert dans la
	 * BdD
	 * 
	 * @param numeroCompte
	 *            String correspondant au numéro de compte à créer
	 * @param client
	 *            Client, objet correspondant à l'objet client auquel on veut
	 *            rajouter un compte
	 * @param decouvertAutorise
	 *            double correspondant au montant de découvert autorisé pour le
	 *            nouveau compte
	 * @throws TechnicalException
	 * @throws IllegalFormatException
	 * @throws IllegalOperationException 
	 */
	public void createAccount(String numeroCompte, Client client, double decouvertAutorise)
			throws TechnicalException, IllegalFormatException, IllegalOperationException {
		dao.createCompteAvecDecouvert(0, numeroCompte, decouvertAutorise, client);
	}

	/**
	 * Méthode qui va appeler la DAO pour supprimer le compte passé en paramètre
	 * dans la mesure du possible
	 * 
	 * @param c
	 *            Compte correspondant à l'objet à supprimer
	 * @throws IllegalOperationException
	 *             quand on essaie la suppression d'un compte avec un solde
	 *             différent de 0
	 * @throws TechnicalException
	 *             si le compte est null ou si le compte n'est pas un compte
	 *             persistant.
	 */
	public void deleteAccount(Compte c) throws IllegalOperationException, TechnicalException {
		if (c.getSolde() != 0) {
			throw new IllegalOperationException("Impossible de supprimer un compte avec une solde différent de 0");
		}
		dao.deleteAccount(c);
	}

	/**
	 * Méthode qui va appeler la DAO pour créer un nouveau manager dans la BdD
	 * 
	 * @param userId
	 *            String pour le userId à utiliser
	 * @param userPwd
	 *            String pour le password à utiliser
	 * @param nom
	 *            String pour le nom
	 * @param prenom
	 *            String pour le prenom
	 * @param adresse
	 *            String pour l'adresse
	 * @param male
	 *            boolean pour savoir si c'est un homme ou une femme
	 * @throws TechnicalException
	 *             : Si l'id fourni en paramètre est déjà assigné à un autre
	 *             utilisateur de la base
	 * @throws IllegalFormatException
	 * @throws IllegalArgumentException
	 */
	public void createManager(String userId, String userPwd, String nom, String prenom, String adresse, boolean male)
			throws TechnicalException, IllegalArgumentException, IllegalFormatException {
		UserCreationParams params = new UserCreationParams.Builder()
			.setNom(nom)
			.setPrenom(prenom)
			.setAdresse(adresse)
			.setMale(male)
			.setUsrId(userId)
			.setUsrPwd(userPwd)
			.setManager(true)
			.setNumClient(null)
			.build();
		dao.createUser(params);
	}

	/**
	 * Méthode qui va appeler la DAO pour créer un nouveau client dans la BdD
	 * 
	 * @param userId
	 *            String pour le userId à utiliser
	 * @param userPwd
	 *            String pour le password à utiliser
	 * @param nom
	 *            String pour le nom
	 * @param prenom
	 *            String pour le prenom
	 * @param adresse
	 *            String pour l'adresse
	 * @param male
	 *            boolean pour savoir si c'est un homme ou une femme
	 * @param numeroClient
	 *            String pour le numero de client
	 * @throws IllegalOperationException
	 * @throws TechnicalException
	 *             : Si l'id fourni en param�tre est déjà assigné à un autre
	 *             utilisateur de la base
	 * @throws IllegalFormatException
	 * @throws IllegalArgumentException
	 */
	public void createClient(String userId, String userPwd, String nom, String prenom, String adresse, boolean male,
			String numeroClient)
			throws IllegalOperationException, TechnicalException, IllegalArgumentException, IllegalFormatException {
		Map<String, Client> liste = this.getAllClients();
		for (Map.Entry<String, Client> entry : liste.entrySet()) {
			if (entry.getValue().getNumeroClient().equals(numeroClient)) {
				throw new IllegalOperationException(
						"Un client avec le numero de client " + numeroClient + " existe déjà");
			}
		}
		UserCreationParams params = new UserCreationParams.Builder()
			.setNom(nom)
			.setPrenom(prenom)
			.setAdresse(adresse)
			.setMale(male)
			.setUsrId(userId)
			.setUsrPwd(userPwd)
			.setManager(false)
			.setNumClient(numeroClient)
			.build();
		dao.createUser(params);

	}

	/**
	 * Méthode qui va appeler la DAO pour supprimer un utilisateur
	 * 
	 * @param u
	 *            Utilisateur correspondant à l'objet Utilisateur à supprimer
	 * @throws TechnicalException
	 *             si l'user est null ou si l'utilisateur n'est pas un
	 *             utilisateur persistant.
	 * @throws IllegalOperationException
	 *             si le manager à supprimer est le dernier dans la base
	 */
	public void deleteUser(Utilisateur u) throws IllegalOperationException, TechnicalException {
		if (u instanceof Client) {
			Map<String, Compte> liste = ((Client) u).getAccounts();
			for (Map.Entry<String, Compte> entry : liste.entrySet()) {
				this.deleteAccount(entry.getValue());
			}
		} else if (u instanceof Gestionnaire && bank.getGestionnaires().size() == 1) {
				throw new IllegalOperationException("Impossible de supprimer le dernier gestionnaire de la banque");
			}
		
		this.bank.deleteUser(u.getUserId());
		dao.deleteUser(u);
	}

	/**
	 * Change le découvert d'un compte
	 * 
	 * @param compte
	 * @param nouveauDecouvert
	 * @throws IllegalFormatException
	 * @throws IllegalOperationException
	 */
	public void changeDecouvert(CompteAvecDecouvert compte, double nouveauDecouvert) throws IllegalFormatException, IllegalOperationException {
		bank.changeDecouvert(compte, nouveauDecouvert);
		dao.updateAccount(compte);
	}

	

}

package com.iut.banque.controller;

import java.util.Map;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.iut.banque.constants.LoginConstants;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Client;
import com.iut.banque.modele.Compte;
import com.iut.banque.modele.Utilisateur;
import com.opensymphony.xwork2.ActionSupport;
import com.iut.banque.utils.UtilsFunctions;

public class Connect extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String userCde;
	private String userPwd;
	private final transient BanqueFacade banque;

	private static final Logger logger = Logger.getLogger(Connect.class.getName());
	private static final String ERROR = "ERROR";

	/**
	 * Constructeur de la classe Connect
	 * 
	 * @return Un objet de type Connect avec façade BanqueFacade provenant de sa
	 *         factory
	 */
	public Connect() {
		logger.log(Level.INFO, "In Constructor from Connect class ");
		ApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
		this.banque = (BanqueFacade) context.getBean("banqueFacade");

	}

	/**
	 * Méthode pour vérifier la connexion de l'utilisateur basé sur les
	 * paramêtres userCde et userPwd de cette classe
	 * 
	 * @return String, le resultat du login; "SUCCESS" si réussi, ERROR si
	 *         échec
	 */
	public String login() {
		logger.log(Level.INFO,"Essai de login - 20180512...");

		if (userCde == null || userPwd == null) {
			return ERROR;
		}
		userCde = userCde.trim();

		int loginResult;
		try {
			//Hashage de l'input avant comparaison des hash
			String hashedUserPwd = UtilsFunctions.sha512Hash(userPwd);
			loginResult = banque.tryLogin(userCde, hashedUserPwd);
		} catch (Exception e) {
			e.printStackTrace();
			loginResult = LoginConstants.ERROR;
		}

		switch (loginResult) {
			case LoginConstants.USER_IS_CONNECTED:
				 logger.log(Level.INFO, "user logged in");
				 return "SUCCESS";
			case LoginConstants.MANAGER_IS_CONNECTED:
				 logger.log(Level.INFO, "manager logged in");
				 return "SUCCESSMANAGER";
			case LoginConstants.LOGIN_FAILED:
				 logger.log(Level.INFO, "login failed");
				 return ERROR;
			default:
				 logger.log(Level.SEVERE, "Unknown error");
				 return ERROR;
	  }
 }

	/**
	 * Getter du champ userCde
	 * 
	 * @return String, le userCde de la classe
	 */
	public String getUserCde() {
		return userCde;
	}

	/**
	 * Setter du champ userCde
	 * 
	 * @param userCde
	 *            : String correspondant au userCode à établir
	 */
	public void setUserCde(String userCde) {
		this.userCde = userCde;
	}

	/**
	 * Getter du champ userPwd
	 * 
	 * @return String, le userPwd de la classe
	 */
	public String getUserPwd() {
		return userPwd;
	}

	/**
	 * Setter du champ userPwd
	 * 
	 * @param userPwd
	 *            : correspondant au pwdCde à établir
	 */
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	/**
	 * Getter du champ utilisateur (uilisé pour récupérer l'utilisateur
	 * actuellement connecté à l'application)
	 * 
	 * @return Utilisateur, l'utilisateur de la classe
	 */
	public Utilisateur getConnectedUser() {
		return banque.getConnectedUser();
	}

	/**
	 * Méthode qui va récupérer sous forme de map la liste des comptes du client
	 * actuellement connecté à l'application
	 * 
	 * @return Map<String, Compte> correspondant à l'ID du compte et l'objet
	 *         Compte associé
	 */
	public Map<String, Compte> getAccounts() {
		return ((Client) banque.getConnectedUser()).getAccounts();
	}

	public String logout() {
		logger.log(Level.INFO, "Logging out");
		banque.logout();
		return "SUCCESS";
	}

}

package com.iut.banque.constants;

/**
 * Classe abstraite contenant des constantes statiques utilisées pour indiquer
 * le status d'une tentative de login.
 */
public final class LoginConstants {

	public static final int USER_IS_CONNECTED = 1;
	public static final int MANAGER_IS_CONNECTED = 2;
	public static final int LOGIN_FAILED = -1;
	public static final int ERROR = -2;

	private LoginConstants() {
		throw new UnsupportedOperationException("Classe utilitaire n'ayant pas pour but d'être initialisé");
  }
}

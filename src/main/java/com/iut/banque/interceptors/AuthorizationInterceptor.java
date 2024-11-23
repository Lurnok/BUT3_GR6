package com.iut.banque.interceptors;

import java.util.Arrays;
import java.util.List;

import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthorizationInterceptor implements Interceptor {

    private BanqueFacade banqueFacade;

    public AuthorizationInterceptor(BanqueFacade banqueFacade) {
        this.banqueFacade = banqueFacade;
    }

    @Override
    public void destroy() {
        // Not implemented yet
    }

    @Override
    public void init() {
        // Not implemented yet
    }

    @Override
    public String intercept(ActionInvocation inv) throws Exception {
        ActionContext context = inv.getInvocationContext();

        // Ignorer pages de login et redirection après login
        if (context.getName().equalsIgnoreCase("redirectionLogin") || context.getName().equalsIgnoreCase("controller.Connect.login"))  {
            return inv.invoke();
        }

        // Récupération de l'user connecté
        Utilisateur user = banqueFacade.getConnectedUser();

        // Pas d'user connecté
        if (user == null) {
            return "redirectToLogin";
        }

        // Listes des action réservées aux admins
        String[] adminActions = {"listeCompteManager","retourTableauDeBordManager","urlAjoutUtilisateur"};
        List<String> actionsList = Arrays.asList(adminActions);

        // Si l'action est réservée et que l'user n'est pas un admin
        if(actionsList.contains(context.getName()) && !(user instanceof Gestionnaire)){
            return "accessDenied";
        }

        // allow
        return inv.invoke();
    }
}

package com.iut.banque.interceptors;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.Arrays;
import java.util.List;

public class AuthorizationInterceptor implements Interceptor {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub
    }

    @Override
    public String intercept(ActionInvocation inv) throws Exception {
        ActionContext context = inv.getInvocationContext();

        // Ignorer pages de login et redirection après login
        if (context.getName().equalsIgnoreCase("redirectionLogin") || context.getName().equalsIgnoreCase("controller.Connect.login"))  {
            return inv.invoke();
        }

        // Récupération de l'user connecté
        ApplicationContext appContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        BanqueFacade banque = (BanqueFacade) appContext.getBean("banqueFacade");
        Utilisateur user = banque.getConnectedUser();

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

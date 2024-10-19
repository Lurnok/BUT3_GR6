package com.iut.banque.interceptors;
import com.iut.banque.controller.Connect;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Utilisateur;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.SessionMap;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class AuthorizationInterceptor implements Interceptor {

    private static final long serialVersionUID = 1841289944579731267L;

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init() {
        System.out.println("AuthorizationInterceptor initialized.");
    }

    @Override
    public String intercept(ActionInvocation inv) throws Exception {
        ActionContext context = inv.getInvocationContext();
        System.out.println("Interceptor invoked for action: " + context.getName());

        // Check for login or registration actions
        if (context.getName().equalsIgnoreCase("redirectionLogin") || context.getName().equalsIgnoreCase("controller.Connect.login"))  {
            return inv.invoke();
        }

        ApplicationContext appContext = WebApplicationContextUtils
                .getRequiredWebApplicationContext(ServletActionContext.getServletContext());
        BanqueFacade banque = (BanqueFacade) appContext.getBean("banqueFacade");
        Utilisateur user = banque.getConnectedUser();

        if (user == null) {
            System.out.println("User is not authenticated, redirecting to login.");
            return "redirectToLogin";
        }

        System.out.println("User is authenticated.");
        return inv.invoke();
    }


}

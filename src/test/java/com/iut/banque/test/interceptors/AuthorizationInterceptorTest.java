package com.iut.banque.test.interceptors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.iut.banque.interceptors.AuthorizationInterceptor;
import com.iut.banque.facade.BanqueFacade;
import com.iut.banque.modele.Gestionnaire;
import com.iut.banque.modele.Utilisateur;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationInterceptorTest {

    @Mock
    private ActionInvocation invocation;

    @Mock
    private ActionContext context;

    @Mock
    private BanqueFacade banqueFacade;

    @Mock
    private Utilisateur utilisateur;

    @Mock
    private Gestionnaire gestionnaire;

    private AuthorizationInterceptor authorizationInterceptor;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authorizationInterceptor = new AuthorizationInterceptor(banqueFacade);
        when(invocation.getInvocationContext()).thenReturn(context);
    }

    @Test
    public void testInterceptLoginPages() throws Exception {
        when(context.getName()).thenReturn("redirectionLogin");
        String result = authorizationInterceptor.intercept(invocation);
        assertEquals(invocation.invoke(), result);
    }

    @Test
    public void testInterceptNoUserConnected() throws Exception {
        when(context.getName()).thenReturn("someAction");
        when(banqueFacade.getConnectedUser()).thenReturn(null);
        String result = authorizationInterceptor.intercept(invocation);
        assertEquals("redirectToLogin", result);
        verify(invocation, never()).invoke();
    }

    @Test
    public void testInterceptAdminActionWithNonAdminUser() throws Exception {
        when(context.getName()).thenReturn("listeCompteManager");
        when(banqueFacade.getConnectedUser()).thenReturn(utilisateur);
        String result = authorizationInterceptor.intercept(invocation);
        assertEquals("accessDenied", result);
        verify(invocation, never()).invoke();
    }

    @Test
    public void testInterceptAdminActionWithAdminUser() throws Exception {
        when(context.getName()).thenReturn("listeCompteManager");
        when(banqueFacade.getConnectedUser()).thenReturn(gestionnaire);
        String result = authorizationInterceptor.intercept(invocation);
        assertEquals(invocation.invoke(), result);
    }

    @Test
    public void testInterceptNonAdminActionWithNonAdminUser() throws Exception {
        when(context.getName()).thenReturn("someAction");
        when(banqueFacade.getConnectedUser()).thenReturn(utilisateur);
        String result = authorizationInterceptor.intercept(invocation);
        assertEquals(invocation.invoke(), result);
    }

    @Test
    public void testInterceptNonAdminActionWithAdminUser() throws Exception {
        when(context.getName()).thenReturn("someAction");
        when(banqueFacade.getConnectedUser()).thenReturn(gestionnaire);
        String result = authorizationInterceptor.intercept(invocation);
        assertEquals(invocation.invoke(), result);
    }
}

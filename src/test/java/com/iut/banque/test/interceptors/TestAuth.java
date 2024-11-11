package com.iut.banque.test.interceptors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.iut.banque.interceptors.AuthorizationInterceptor;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


import com.iut.banque.utils.UtilsFunctions;
import org.junit.Test;

public class TestAuth {

    AuthorizationInterceptor ai = new AuthorizationInterceptor();

    @Test
    public void testInterception() {


    }
}

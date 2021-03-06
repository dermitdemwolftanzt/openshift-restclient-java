/*******************************************************************************
 * Copyright (c) 2015 Red Hat, Inc. Distributed under license by Red Hat, Inc.
 * All rights reserved. This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Red Hat, Inc.
 ******************************************************************************/
package com.openshift.internal.restclient.http;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.openshift.internal.restclient.http.UrlConnectionHttpClient;
import com.openshift.restclient.authorization.IAuthorizationStrategy;
import com.openshift.restclient.authorization.IRequest;

/**
 * @author Jeff Cantrill
 */
@RunWith(MockitoJUnitRunner.class)
public class UrlConnectionHttpClientTest {
	
	@Mock
	private IAuthorizationStrategy strategy;
	private UrlConnectionHttpClient client;
	@Mock
	private HttpURLConnection connection;
	
	@Before
	public void setUp(){
		client = new UrlConnectionHttpClient("testagent", "application/json", "1");
	}
	
	@Test
	public void nullAuthStrategyShouldNotThrowNPEWhenSetAuthorization() {
		client.setAuthorizationStrategy(null);
		client.setAuthorization(connection );
		verify(connection, never()).setRequestProperty(anyString(), anyString());
	}
	
	@Test
	public void setAuthorizationWhenStrategyIsSetShouldUseTheStrategy() {
		client.setAuthorizationStrategy(strategy);

		client.setAuthorization(connection);
		verify(connection, never()).setRequestProperty(anyString(), anyString());
		verify(strategy).authorize(any(IRequest.class));
	}

}

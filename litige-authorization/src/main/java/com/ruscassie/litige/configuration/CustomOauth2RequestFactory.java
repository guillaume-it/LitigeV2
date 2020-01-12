package com.ruscassie.litige.configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

class CustomOauth2RequestFactory extends DefaultOAuth2RequestFactory {
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UserDetailsService userDetailsService;

	public CustomOauth2RequestFactory(final ClientDetailsService clientDetailsService) {
		super(clientDetailsService);
	}

	@Override
	public TokenRequest createTokenRequest(final Map<String, String> requestParameters,
			final ClientDetails authenticatedClient) {
		if (requestParameters.get("grant_type").equals("refresh_token")) {
			final OAuth2Authentication authentication = tokenStore.readAuthenticationForRefreshToken(
					tokenStore.readRefreshToken(requestParameters.get("refresh_token")));
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(authentication.getName(), null,
							userDetailsService.loadUserByUsername(authentication.getName()).getAuthorities()));
		}
		return super.createTokenRequest(requestParameters, authenticatedClient);
	}
}
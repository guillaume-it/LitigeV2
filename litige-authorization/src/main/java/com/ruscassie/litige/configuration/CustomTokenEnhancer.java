package com.ruscassie.litige.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.ruscassie.litige.entity.User;

/*
 * Add custom user principal information to the JWT token
 */
class CustomTokenEnhancer extends JwtAccessTokenConverter {
	@Override
	public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken, final OAuth2Authentication authentication) {
		final User user = (User) authentication.getPrincipal();

		final Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

		info.put("email", user.getEmail());

		final DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);

		return super.enhance(customAccessToken, authentication);
	}
}
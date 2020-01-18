package com.ruscassie.litige.configuration;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.ruscassie.litige.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {
	class CustomOauth2RequestFactory extends DefaultOAuth2RequestFactory {
		@Autowired
		private TokenStore tokenStore;

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

	/*
	 * Add custom user principal information to the JWT token
	 */
	class CustomTokenEnhancer extends JwtAccessTokenConverter {
		@Override
		public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken,
				final OAuth2Authentication authentication) {
			final User user = (User) authentication.getPrincipal();

			final Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

			info.put("email", user.getEmail());

			final DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
			customAccessToken.setAdditionalInformation(info);

			return super.enhance(customAccessToken, authentication);
		}
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Bean
	public JdbcClientDetailsService clientDetailsService() {
		return new JdbcClientDetailsService(dataSource);
	}

	@Bean
	public ClientRegistrationService clientRegistrationService() {
		return new JdbcClientDetailsService(dataSource);
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtAccessTokenConverter())
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
		endpoints.requestFactory(requestFactory());
	}

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
	}

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		final JwtAccessTokenConverter converter = new CustomTokenEnhancer();
		converter.setSigningKey("password");
		return converter;
	}

	@Bean
	public OAuth2RequestFactory requestFactory() {
		final CustomOauth2RequestFactory requestFactory = new CustomOauth2RequestFactory(clientDetailsService);
		requestFactory.setCheckUserScopes(true);
		return requestFactory;
	}

	@Bean
	public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
		return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

}
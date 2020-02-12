package com.ruscassie.litige.config;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import java.util.List;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	@Autowired
	private EurekaClient eurekaClient;
	@Autowired
	private DataSource dataSource;

	@Value("${properties.auth-server-url}")
	private String authEndpoint;

	@Value("${properties.resource-id}")
	private String ressourceId;

	@Value("${properties.client-secret}")
	private String clientSecret;

	@Value("${properties.client-id}")
	private String clientId;

	@Value("${properties.auth-check-token-uri}")
	private String uriCheckToken;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll().and().cors().disable().csrf().disable().httpBasic().disable()
				.exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.accessDeniedHandler(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(ressourceId).tokenStore(tokenStore());
	}

	@Bean
	public ResourceServerTokenServices tokenService() {
		final RemoteTokenServices tokenServices = new RemoteTokenServices();
		tokenServices.setClientId(clientId);
		tokenServices.setClientSecret(clientSecret);
		Application application = eurekaClient.getApplication(authEndpoint);

		tokenServices.setCheckTokenEndpointUrl(application.getInstances().get(0).getHomePageUrl()+uriCheckToken);

		return tokenServices;
	}

	@Bean
	public JdbcTokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

}

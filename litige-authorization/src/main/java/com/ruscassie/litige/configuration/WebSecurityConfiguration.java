package com.ruscassie.litige.configuration;

import javax.servlet.http.HttpServletResponse;

import com.ruscassie.litige.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder passwordEncoder() {
		     return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;

	@Autowired
	private UserDetailsService userDetailsService;
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and()
				.logout().logoutUrl("/oauth/logout").logoutSuccessHandler(customLogoutSuccessHandler).invalidateHttpSession(true).clearAuthentication(true).deleteCookies() //.logoutSuccessHandler(customLogoutSuccessHandler)
				.and()
				.authorizeRequests().antMatchers("/auth/uaa").anonymous()
				.and()
				.authorizeRequests().antMatchers("/auth/h2-console").anonymous()
				.and()
				.authorizeRequests().antMatchers("/auth/**").authenticated()
				.and()
				.httpBasic();

	this.enableFrameOption(http);
		// @formatter:on
	}

	@Profile( "dev")
	private void enableFrameOption(final HttpSecurity http) throws Exception {
		// add this line to use H2 web console
		http.headers().frameOptions().disable();
	}


}
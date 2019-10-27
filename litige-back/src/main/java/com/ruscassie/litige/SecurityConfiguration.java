package com.ruscassie.litige;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().antMatchers("/token", "/login","/logout","/").permitAll()
        .anyRequest().authenticated()
		.and().httpBasic().and().logout().logoutUrl("logout").logoutSuccessUrl("/").invalidateHttpSession(true)
		.deleteCookies("X-Auth-Token").clearAuthentication(true).deleteCookies("JSESSIONID")
          .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
		


	}
	

//    @Autowired
//public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//              auth.userDetailsService(myAppUserDetailsService).passwordEncoder(passwordEncoder());
//}
//      @Bean
//      public PasswordEncoder passwordEncoder() {
//           BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//           return passwordEncoder;
//      }
//}   
}

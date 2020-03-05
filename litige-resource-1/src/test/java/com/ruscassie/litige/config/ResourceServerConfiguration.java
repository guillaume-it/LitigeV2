package com.ruscassie.litige.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@ActiveProfiles("test")
// TODO find a solution to test the roles
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(ResourceServerSecurityConfigurer security) throws Exception {
    super.configure(security);

    // Using OAuth with distant authorization service, stateless implies that the request tokens
    // are verified each time against this service. In test, we don't want that because we need
    // properly isolated tests. Setting this implies that the security is checked only locally
    // and allows us to mock it with @WithMockUser, @AutoConfigureMockMvc and autowired MockMVC
    security.stateless(false);
  }

}
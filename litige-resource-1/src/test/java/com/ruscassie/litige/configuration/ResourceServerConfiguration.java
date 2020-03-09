package com.ruscassie.litige.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@EnableResourceServer
@ActiveProfiles("test")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration   {

}
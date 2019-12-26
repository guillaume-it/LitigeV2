package com.ruscassie.litige.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ruscassie.litige.dto.CustomPrincipal;

@Configuration
@EnableWebSecurity
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(currentUserHandlerMethodArgumentResolver());
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(final CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("PUT", "DELETE", "POST", "GET", "PATCH").allowedOrigins("*");
			}
		};
	}

	@Bean
	public HandlerMethodArgumentResolver currentUserHandlerMethodArgumentResolver() {
		return new HandlerMethodArgumentResolver() {
			@Override
			public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
					final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) throws Exception {
				try {
					return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				} catch (final Exception e) {
					return null;
				}
			}

			@Override
			public boolean supportsParameter(final MethodParameter parameter) {
				return parameter.getParameterType().equals(CustomPrincipal.class);
			}
		};
	}
}
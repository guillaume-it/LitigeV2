package com.ruscassie.litige.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

	@Value("${properties.cors.access-control-allow-origin}")
	private String accessControlAllowOrigin;

	@Value("${properties.cors.access-control-allow-methods}")
	private String accessControlAllowMethods;

	@Value("${properties.cors.access-control-max-age}")
	private String accessControlMaxAge;

	@Value("${properties.cors.access-control-allow-headers}")
	private String accessControlAllowHeaders;

	public SimpleCorsFilter() {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) res;
		final HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader("Access-Control-Allow-Origin", accessControlAllowOrigin);
		response.setHeader("Access-Control-Allow-Methods", accessControlAllowMethods);
		response.setHeader("Access-Control-Max-Age", accessControlMaxAge);
		response.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);

		// TODO Voir à quoi cela sert
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(final FilterConfig filterConfig) {
	}
}
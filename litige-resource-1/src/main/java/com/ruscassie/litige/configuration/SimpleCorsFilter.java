package com.ruscassie.litige.configuration;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

	@Value("${properties.zuul-server-url}")
	private String zuulEndPoint;

	@Autowired
	private EurekaClient eurekaClient;


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

		InstanceInfo instance = eurekaClient.getNextServerFromEureka(zuulEndPoint, false);
// 				registry.addMapping("/**").allowedMethods("PUT", "DELETE", "POST", "GET", "PATCH").allowedOrigins("*");
		// TODO TEST With docker and 2 differences origin for resource and auth
		// must authorise only the connection from zuul
		response.setHeader("Access-Control-Allow-Origin",instance.getHomePageUrl());
		response.setHeader("Access-Control-Allow-Methods", accessControlAllowMethods);
	//	response.setHeader("Access-Control-Max-Age", accessControlMaxAge);
	//	response.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);

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
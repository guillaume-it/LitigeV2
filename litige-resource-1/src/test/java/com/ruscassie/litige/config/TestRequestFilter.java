//package com.ruscassie.litige.config;
//
// import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Optional;
//
//@Component
//@ActiveProfiles("test")
//public class TestRequestFilter extends OncePerRequestFilter {
//
//  private Optional<InfoConf> nextInfoConf = Optional.empty();
//
//  // Request info is our request-scoped bean that holds JWT info
//  @Autowired
//  private RequestInfo info;
//
//  @Override
//  protected void doFilterInternal(HttpServletRequest httpServletRequest,
//                                  HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//    if (nextInfoConf.isPresent()) {
//      info.setInfoConf(nextInfoConf.get());
//    }
//    filterChain.doFilter(httpServletRequest, httpServletResponse);
//  }
//
//  public void setNextInfoConf(InfoConf nextInfoConf) {
//    this.nextInfoConf = Optional.of(nextInfoConf);
//  }
//
//  public void clearNextInfoConf() {
//    nextInfoConf = Optional.empty();
//  }
//
//}
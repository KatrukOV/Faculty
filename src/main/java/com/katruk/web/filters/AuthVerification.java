package com.katruk.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public final class AuthVerification extends BaseFilter {

//  private static final Logger logger = Logger.getLogger(AuthVerification.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    System.out.println(">>> AuthVerification Filter");

    chain.doFilter(request, response);
  }
}

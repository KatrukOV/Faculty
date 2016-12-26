package com.katruk.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class BaseFilter implements Filter {

  protected FilterConfig filterConfig;

  @Override
  public void init(FilterConfig config) {
    System.out.println(">>> BaseFilter init");
    this.filterConfig = config;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    this.filterConfig = null;
  }
}

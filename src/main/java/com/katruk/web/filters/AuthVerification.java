package com.katruk.web.filters;

import static java.util.Objects.nonNull;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import org.apache.log4j.Logger;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class AuthVerification extends BaseFilter implements PageAttribute {

  private final Logger logger = Logger.getLogger(AuthVerification.class);

  @Override
  public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String username = (String) ((HttpServletRequest) request).getSession().getAttribute(USERNAME);
    String url = ((HttpServletRequest) request).getRequestURL().toString();
    if (nonNull(username) || url.contains("registration")) {
      logger.info(
          String.format("Received a request from user with username: %s to %s", username, url));
      chain.doFilter(request, response);
    } else {
      logger.info(
          String.format("Unauthorized access to %s. Denied. Redirecting to login page...", url));
      ((HttpServletResponse) response)
          .sendRedirect(PageConfig.getInstance().getValue(PageConfig.INDEX));
    }
  }
}
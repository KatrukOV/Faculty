package com.katruk.web.controller;

import com.katruk.web.PageAttribute;

import org.apache.log4j.Logger;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class DispatcherServlet extends HttpServlet implements PageAttribute {

  private final DispatcherHelper helper;
  private final Logger logger;
  private final static String DISPATCHER_ERROR = "dispatcher error";

  public DispatcherServlet() {
    super();
    this.helper = new DispatcherHelper();
    this.logger = Logger.getLogger(DispatcherServlet.class);
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) {
    System.out.println(">>> helper=" + helper);
    ICommand command = this.helper.getCommand(request);
    System.out.println(">>> command=" + command);

    String page = command.execute(request, response);

    // TODO: 16.12.2016  dell
    System.out.println(">>>>>>>>>>>> servlet page=" + page);

    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
    System.out.println(">>> dispatcher=" + dispatcher);

    try {
      dispatcher.forward(request, response);
    } catch (ServletException | IOException e) {
      logger.error(DISPATCHER_ERROR, e);
    }

  }
}

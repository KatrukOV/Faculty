package com.katruk.web.controller.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.katruk.entity.Period;
import com.katruk.entity.Student;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.ServiceException;
import com.katruk.service.PeriodService;
import com.katruk.service.StudentService;
import com.katruk.service.TeacherService;
import com.katruk.service.UserService;
import com.katruk.service.impl.PeriodServiceImpl;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class Login implements Command, PageAttribute {

  private final static String ERROR_LOGIN_EMPTY = "Username or password is empty";
  private final static String ERROR_LOGIN_WRONG = "Wrong username or password";
  private final static int MAX_INACTIVE_INTERVAL = 60 * 60 * 24;
  private final Logger logger;
  private final PeriodService periodService;
  private final UserService userService;
  private final StudentService studentService;
  private final TeacherService teacherService;

  public Login() {
    this.periodService = new PeriodServiceImpl();
    this.userService = new UserServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.teacherService = new TeacherServiceImpl();
    this.logger = Logger.getLogger(Login.class);
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    final String username = request.getParameter(USERNAME);
    final String password = request.getParameter(PASSWORD);
    final HttpSession session = request.getSession();
    String page = PageConfig.getInstance().getValue(PageConfig.INDEX);
    if (isNull(username) || isNull(password)) {
      request.setAttribute(ERROR, ERROR_LOGIN_EMPTY);
      this.logger.error(ERROR_LOGIN_EMPTY);
    } else {
      try {
        final User user = this.userService.getUserByUsername(username);
        if (user.getPassword().equals(DigestUtils.sha1Hex(password))) {
          session.setAttribute(LAST_NAME, user.getPerson().getLastName());
          session.setAttribute(NAME, user.getPerson().getName());
          session.setAttribute(USERNAME, user.getUsername());
          session.setAttribute(ROLE, user.getRole());
          session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
          page = PageConfig.getInstance().getValue(PageConfig.PROFILE);
          if (nonNull(user.getRole())) {
            switch (user.getRole()) {
              case STUDENT: {
                final Student student = this.studentService.getStudentById(user.getId());
                session.setAttribute(CONTRACT, student.getContract());
                session.setAttribute(FORM, student.getForm());
                break;
              }
              case TEACHER: {
                final Teacher teacher = this.teacherService.getTeacherById(user.getId());
                session.setAttribute(POSITION, teacher.getPosition());
                break;
              }
              case ADMIN: {
                page = PageConfig.getInstance().getValue(PageConfig.ADMIN_PROFILE);
                Period period = this.periodService.getLastPeriod();
                request.setAttribute(PERIOD_STATUS, period.getStatus());
                request.setAttribute(PERIOD_DATE, period.getDate());
                break;
              }
            }
          }
        }
      } catch (ServiceException e) {
        request.getSession().setAttribute(ERROR, ERROR_LOGIN_WRONG);
        logger.error(ERROR_LOGIN_WRONG, e);
        page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      }
    }
    return page;
  }
}

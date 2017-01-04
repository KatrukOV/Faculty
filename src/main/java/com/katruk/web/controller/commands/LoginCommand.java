package com.katruk.web.controller.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import com.katruk.entity.Student;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;
import com.katruk.service.TeacherService;
import com.katruk.service.UserService;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class LoginCommand implements ICommand, PageAttribute {

  private final static String ERROR_LOGIN_EMPTY = "Username or password is empty";
  private final static String ERROR_LOGIN_WRONG = "Wrong username or password";
  private final static int MaxInactiveInterval = 60 * 60 * 24;
  private final Logger logger;
  private final UserService userService;
  private final StudentService studentService;
  private final TeacherService teacherService;

  public LoginCommand() {
    this.userService = new UserServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.teacherService = new TeacherServiceImpl();
    this.logger = Logger.getLogger(LoginCommand.class);
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {

    final String username = request.getParameter(USERNAME);
    final String password = request.getParameter(PASSWORD);
    final HttpSession session = request.getSession();
    String page = Config.getInstance().getValue(Config.INDEX);

    if (isNull(username) || isNull(password)) {
      request.setAttribute(ERROR, ERROR_LOGIN_EMPTY);
      this.logger.error(ERROR_LOGIN_EMPTY);
    } else {
      System.out.println(">> else ");
      try {
        final User user = this.userService.getUserByUsername(username);
        System.out.println(">>> user="+user);
        if (user.getPassword().equals(DigestUtils.sha1Hex(password))) {
          session.setAttribute(LAST_NAME, user.getPerson().getLastName());
          session.setAttribute(NAME, user.getPerson().getName());
          session.setAttribute(USERNAME, user.getUsername());
          session.setAttribute(ROLE, user.getRole());
          session.setMaxInactiveInterval(MaxInactiveInterval);
          System.out.println(">>> user name="+user.getPerson().getName());
          System.out.println(">>> user role="+user.getRole());
          page = Config.getInstance().getValue(Config.PROFILE);
          if (nonNull(user.getRole())){
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
                page = Config.getInstance().getValue(Config.ADMIN_PROFILE);
                System.out.println(">>> Admin");
                break;
              }
              default:
                System.out.println("!!!!! null ? ");
            }
          }
        }
      } catch (ServiceException e) {
        request.getSession().setAttribute(ERROR, ERROR_LOGIN_WRONG);
        logger.error(ERROR_LOGIN_WRONG, e);
        page = Config.getInstance().getValue(Config.ERROR_PAGE);
      }
    }
    return page;
  }
}

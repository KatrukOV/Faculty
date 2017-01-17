package com.katruk.web.controller.commands.teacher;

import com.katruk.converter.SubjectConverter;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.service.SubjectService;
import com.katruk.service.TeacherService;
import com.katruk.service.UserService;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GetTeacherSubjects implements Command, PageAttribute {

  private final Logger logger;
  private final SubjectService subjectService;
  private final UserService userService;
  private final TeacherService teacherService;

  public GetTeacherSubjects() {
    this.logger = Logger.getLogger(GetTeacherSubjects.class);
    this.subjectService = new SubjectServiceImpl();
    this.userService = new UserServiceImpl();
    this.teacherService = new TeacherServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.TEACHER_SUBJECTS);
    try {
      String username = (String) request.getSession().getAttribute(USERNAME);
      User user = this.userService.getUserByUsername(username);
      Teacher teacher = this.teacherService.getTeacherById(user.getId());
      Collection<Subject> subjects = this.subjectService.getSubjectByTeacher(teacher);
      List subjectList = Collections.EMPTY_LIST;
      if (!subjects.isEmpty()) {
        subjectList = new SubjectConverter().convertToDto(subjects);
      }
      request.setAttribute(SUBJECT_LIST, subjectList);
      logger.info(String.format("get all subjects = %d", subjectList.size()));
    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get all subjects", e);
    }
    return page;
  }
}

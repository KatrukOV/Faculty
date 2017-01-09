package com.katruk.web.controller.commands;

import com.katruk.converter.TeacherConverter;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.service.SubjectService;
import com.katruk.service.TeacherService;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class RemoveSubject implements Command, PageAttribute {

  private final Logger logger;
  private final SubjectService subjectService;
  private final TeacherService teacherService;

  public RemoveSubject() {
    this.logger = Logger.getLogger(RemoveSubject.class);
    this.subjectService = new SubjectServiceImpl();
    this.teacherService = new TeacherServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ADD_SUBJECT);
    try {
      String title = request.getParameter(TITLE);
      System.out.println(">>>> title=" + title);
      Long teacherId = Long.parseLong(request.getParameter(TEACHER_ID));
      System.out.println(">>>> teacher id=" + teacherId);
      Teacher teacher = this.teacherService.getTeacherById(teacherId);
      System.out.println(">>>> teacher=" + teacher);
      Subject subject = new Subject();
      subject.setTitle(title);
      subject.setTeacher(teacher);
      System.out.println(">>>> subject=" + subject);
      this.subjectService.save(subject);
      System.out.println(">>>> subject id=" + subject.getId());

      Collection<Teacher> teachers = this.teacherService.gatAll();
      List teacherList = Collections.EMPTY_LIST;
      if (!teachers.isEmpty()) {
        teacherList = new TeacherConverter().convertToDto(teachers);
      }
      request.setAttribute(TEACHER_LIST, teacherList);
//      logger.info(String.format("s"));
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable to create a subject", e);
    }
    return page;
  }
}

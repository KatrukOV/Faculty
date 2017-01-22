package com.katruk.web.controller.commands.admin;

import com.katruk.converter.TeacherConverter;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;
import com.katruk.service.SubjectService;
import com.katruk.service.TeacherService;
import com.katruk.service.impl.SubjectServiceImpl;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CreateSubject implements Command, PageAttribute {

  private final Logger logger;
  private final SubjectService subjectService;
  private final TeacherService teacherService;

  public CreateSubject() {
    this.logger = Logger.getLogger(CreateSubject.class);
    this.subjectService = new SubjectServiceImpl();
    this.teacherService = new TeacherServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.ADD_SUBJECT);
    try {
      String title = request.getParameter(TITLE);
      Long teacherId = Long.parseLong(request.getParameter(TEACHER_ID));
      Teacher teacher = this.teacherService.getTeacherById(teacherId);
      Subject subject = new Subject(title, teacher);
      subject.setTitle(title);
      subject.setTeacher(teacher);
      this.subjectService.save(subject);
      Collection<Teacher> teachers = this.teacherService.getAll();
      List teacherList = Collections.EMPTY_LIST;
      if (!teachers.isEmpty()) {
        teacherList = new TeacherConverter().convertToDto(teachers);
      }
      request.setAttribute(TEACHER_LIST, teacherList);
      logger.info("Subject was created");
    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable to create a subject", e);
    }
    return page;
  }
}

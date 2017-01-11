package com.katruk.web.controller.commands;

import com.katruk.converter.SubjectConverter;
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
import java.util.Objects;

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
    String page = Config.getInstance().getValue(Config.SUBJECTS);
    try {
      Long subjectId = Long.parseLong(request.getParameter(SUBJECT_ID));
      this.subjectService.remove(subjectId);
      Collection<Subject> subjects = this.subjectService.getAll();
      List subjectList = Collections.EMPTY_LIST;
      if (!subjects.isEmpty()) {
        Collection<Teacher> teachers = this.teacherService.gatAll();
        for (Subject subject : subjects) {
          for (Teacher teacher : teachers) {
            if (Objects.equals(teacher.getId(), subject.getTeacher().getId())) {
              subject.setTeacher(teacher);
            }
          }
        }
        subjectList = new SubjectConverter().convertToDto(subjects);
      }
      request.setAttribute(SUBJECT_LIST, subjectList);
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable to create a subject", e);
    }
    return page;
  }
}

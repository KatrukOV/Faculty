package com.katruk.web.controller.commands.admin;

import com.katruk.converter.TeacherConverter;
import com.katruk.entity.Teacher;
import com.katruk.service.TeacherService;
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

public final class SetPosition implements Command, PageAttribute {

  private final Logger logger;
  private final TeacherService teacherService;

  public SetPosition() {
    this.logger = Logger.getLogger(SetPosition.class);
    this.teacherService = new TeacherServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.TEACHERS);
    try {
      System.out.println("ff=" + POSITION);
      System.out.println("ff11=" + request.getParameter(POSITION));
      Teacher.Position position = Teacher.Position.valueOf(request.getParameter(POSITION));
      System.out.println(">>>>>>>>>>>> position= " + position);
      Long teacherId = Long.parseLong(request.getParameter(TEACHER_ID));
      Teacher teacher = this.teacherService.getTeacherById(teacherId);
      System.out.println(">>>>>>>>>>>> teacher = " + teacher);
      if (!position.equals(teacher.getPosition())) {
        teacher.setPosition(position);
        this.teacherService.save(teacher);
        logger.info(String.format("set position=%s for teacher= %s", position, teacher));
      }
      Collection<Teacher> teachers = this.teacherService.getAll();
      List teacherList = Collections.EMPTY_LIST;
      if (!teachers.isEmpty()) {
        teacherList = new TeacherConverter().convertToDto(teachers);
      }
      request.setAttribute(TEACHER_LIST, teacherList);
    } catch (Exception e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable set position for teacher", e);
    }
    return page;
  }
}

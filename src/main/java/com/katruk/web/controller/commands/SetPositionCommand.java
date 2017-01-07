package com.katruk.web.controller.commands;

import com.katruk.converter.TeacherConverter;
import com.katruk.converter.UserConverter;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.entity.dto.TeacherDto;
import com.katruk.entity.dto.UserDto;
import com.katruk.service.TeacherService;
import com.katruk.service.impl.TeacherServiceImpl;
import com.katruk.util.Config;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.ICommand;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetPositionCommand implements ICommand, PageAttribute {

  private final Logger logger;
  private final TeacherService teacherService;

  public SetPositionCommand() {
    this.logger = Logger.getLogger(SetPositionCommand.class);
    this.teacherService = new TeacherServiceImpl();
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) {
    String page = Config.getInstance().getValue(Config.ALL_TEACHERS);
    try {
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
      Collection<Teacher> teachers = this.teacherService.gatAll();
      List<TeacherDto> teacherList = new TeacherConverter().convertToDto(teachers);
      request.setAttribute(TEACHER_LIST, teacherList);
    } catch (Exception e) {
      page = Config.getInstance().getValue(Config.ERROR_PAGE);
      logger.error("Unable set position for teacher", e);
    }
    return page;
  }
}

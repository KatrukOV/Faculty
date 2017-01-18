package com.katruk.web.controller;

import static java.util.Objects.isNull;

import com.katruk.web.PageAttribute;
import com.katruk.web.controller.commands.GetSubjects;
import com.katruk.web.controller.commands.Login;
import com.katruk.web.controller.commands.Logout;
import com.katruk.web.controller.commands.Registration;
import com.katruk.web.controller.commands.SetDeclare;
import com.katruk.web.controller.commands.ToProfile;
import com.katruk.web.controller.commands.Unknown;
import com.katruk.web.controller.commands.admin.AddSubject;
import com.katruk.web.controller.commands.admin.CreateSubject;
import com.katruk.web.controller.commands.admin.GetStudents;
import com.katruk.web.controller.commands.admin.GetTeachers;
import com.katruk.web.controller.commands.admin.GetUsers;
import com.katruk.web.controller.commands.admin.RemoveSubject;
import com.katruk.web.controller.commands.admin.SetContract;
import com.katruk.web.controller.commands.admin.SetDistribution;
import com.katruk.web.controller.commands.admin.SetForm;
import com.katruk.web.controller.commands.admin.SetLearning;
import com.katruk.web.controller.commands.admin.SetPosition;
import com.katruk.web.controller.commands.admin.SetRole;
import com.katruk.web.controller.commands.student.GetEvaluationsByStudent;
import com.katruk.web.controller.commands.teacher.Evaluate;
import com.katruk.web.controller.commands.teacher.GetEvaluationsBySubject;
import com.katruk.web.controller.commands.teacher.GetTeacherSubjects;
import com.katruk.web.controller.commands.teacher.SetConfirmOrReject;
import com.katruk.web.controller.commands.teacher.ToEvaluation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

final class DispatcherHelper implements PageAttribute {

  private Map<String, Command> commands;

  DispatcherHelper() {
    commands = new HashMap<>();
    commands.put("login", new Login());
    commands.put("registration", new Registration());
    commands.put("logout", new Logout());
    commands.put("toProfile", new ToProfile());
    commands.put("getUsers", new GetUsers());
    commands.put("getStudents", new GetStudents());
    commands.put("getTeachers", new GetTeachers());
    commands.put("getSubjects", new GetSubjects());
    commands.put("setRole", new SetRole());
    commands.put("setPosition", new SetPosition());
    commands.put("setForm", new SetForm());
    commands.put("setContract", new SetContract());
    commands.put("addSubject", new AddSubject());
    commands.put("createSubject", new CreateSubject());
    commands.put("removeSubject", new RemoveSubject());
    commands.put("getTeacherSubjects", new GetTeacherSubjects());
    commands.put("getEvaluationsBySubject", new GetEvaluationsBySubject());
    commands.put("evaluate", new Evaluate());
    commands.put("toEvaluation", new ToEvaluation());
    commands.put("getEvaluationsByStudent", new GetEvaluationsByStudent());
    commands.put("declare", new SetDeclare());
    commands.put("setConfirmOrReject", new SetConfirmOrReject());
    commands.put("setDistribution", new SetDistribution());
    commands.put("setLearning", new SetLearning());
  }

  Command getCommand(final HttpServletRequest request) {
    Command resultCommand = this.commands.get(request.getParameter(COMMAND));
    if (isNull(resultCommand)) {
      resultCommand = new Unknown();
    }
    return resultCommand;
  }
}
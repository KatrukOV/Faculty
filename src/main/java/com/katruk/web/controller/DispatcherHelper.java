package com.katruk.web.controller;

import static java.util.Objects.isNull;

import com.katruk.web.PageAttribute;
import com.katruk.web.controller.commands.AddSubject;
import com.katruk.web.controller.commands.CreateSubject;
import com.katruk.web.controller.commands.GetStudents;
import com.katruk.web.controller.commands.GetSubjects;
import com.katruk.web.controller.commands.GetTeachers;
import com.katruk.web.controller.commands.GetUsers;
import com.katruk.web.controller.commands.Login;
import com.katruk.web.controller.commands.Logout;
import com.katruk.web.controller.commands.Registration;
import com.katruk.web.controller.commands.SetContract;
import com.katruk.web.controller.commands.SetForm;
import com.katruk.web.controller.commands.SetPosition;
import com.katruk.web.controller.commands.SetRole;
import com.katruk.web.controller.commands.ToProfile;
import com.katruk.web.controller.commands.Unknown;

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

//    commands.put("redirectToTeacherDisciplines", new RedirectToTeacherDisciplinesCommand());
//    commands.put("redirectToConfirmed", new RedirectToConfirmedCommand());
//    commands.put("redirectToEvaluation", new RedirectToEvaluationCommand());
//    commands.put("feedback", new FeedbackCommand());
//    commands.put("addDiscipline", new AddDisciplineCommand());
//
//    commands.put("redirectToMarks", new RedirectToMarksCommand());
//    commands.put("redirectToDeclaredDisciplines", new RedirectToDeclaredDisciplinesCommand());
//
//    commands.put("declared", new DeclaredCommand());
//    commands.put("revoked", new RevokedCommand());
//    commands.put("confirmed", new ConfirmedCommand());
//    commands.put("deleted", new DeletedCommand());
  }

  Command getCommand(final HttpServletRequest request) {
    Command resultCommand = this.commands.get(request.getParameter("command"));
    if (isNull(resultCommand)) {
      resultCommand = new Unknown();
    }
    System.out.println(">>> getCommand resultCommand=" + resultCommand);
    return resultCommand;
  }
}
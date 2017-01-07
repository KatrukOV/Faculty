package com.katruk.web.controller;

import static java.util.Objects.isNull;

import com.katruk.web.PageAttribute;
import com.katruk.web.controller.commands.GetStudentsCommand;
import com.katruk.web.controller.commands.GetSubjectsCommand;
import com.katruk.web.controller.commands.GetTeachersCommand;
import com.katruk.web.controller.commands.GetUsersCommand;
import com.katruk.web.controller.commands.LoginCommand;
import com.katruk.web.controller.commands.LogoutCommand;
import com.katruk.web.controller.commands.RegistrationCommand;
import com.katruk.web.controller.commands.SetContractCommand;
import com.katruk.web.controller.commands.SetFormCommand;
import com.katruk.web.controller.commands.SetPositionCommand;
import com.katruk.web.controller.commands.SetRoleCommand;
import com.katruk.web.controller.commands.ToProfileCommand;
import com.katruk.web.controller.commands.UnknownCommand;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

final class DispatcherHelper implements PageAttribute {

  private Map<String, ICommand> commands;

  DispatcherHelper() {
    commands = new HashMap<>();

    commands.put("login", new LoginCommand());
    commands.put("registration", new RegistrationCommand());
    commands.put("logout", new LogoutCommand());

    commands.put("toProfile", new ToProfileCommand());

    commands.put("getUsers", new GetUsersCommand());
    commands.put("getStudents", new GetStudentsCommand());
    commands.put("getTeachers", new GetTeachersCommand());
    commands.put("getSubjects", new GetSubjectsCommand());

    commands.put("setRole", new SetRoleCommand());
    commands.put("setPosition", new SetPositionCommand());
    commands.put("setForm", new SetFormCommand());
    commands.put("setContract", new SetContractCommand());
//
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

  ICommand getCommand(final HttpServletRequest request) {
    ICommand resultCommand = this.commands.get(request.getParameter("command"));
    if (isNull(resultCommand)) {
      resultCommand = new UnknownCommand();
    }
    System.out.println(">>> getCommand resultCommand=" + resultCommand);
    return resultCommand;
  }
}
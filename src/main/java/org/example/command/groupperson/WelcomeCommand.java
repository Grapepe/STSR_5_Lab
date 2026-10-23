package org.example.command.groupperson;

import org.example.Model.Person;
import org.example.command.Command;
import org.example.command.CommandResult;
import org.example.exceptions.IncorrectDataException;
import org.example.exceptions.ServiceException;
import org.example.service.PersonService;
import org.example.util.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.example.command.groupperson.constant.GroupConstant.LISTGROUP;

public class WelcomeCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, IncorrectDataException {
        PersonService personService = new PersonService();
        List<Person> clients = personService.findAll();
        if (!clients.isEmpty()) {
            request.setAttribute(LISTGROUP, clients);
        }
        return new CommandResult(Page.WELCOME_PAGE.getPage(), false);
    }
}
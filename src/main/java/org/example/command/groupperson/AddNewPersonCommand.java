package org.example.command.groupperson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.Optional;

import static org.example.command.groupperson.constant.GroupConstant.*;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AddNewPersonCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddNewPersonCommand.class.getName());

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, IncorrectDataException {
        PersonService personService = new PersonService();
        Optional<String> newName = of(request).map(r -> r.getParameter(NEWNAME));
        Optional<String> newPhone = of(request).map(r -> r.getParameter(NEWPHONE));
        Optional<String> newEmail = of(request).map(r -> r.getParameter(NEWEMAIL));
        if (isEmpty(newName.get()) || isEmpty(newPhone.get()) || isEmpty(newEmail.get())) {
            logger.info("missing parameter for new person in addition mode");
            request.setAttribute(ERROR_MESSAGE, ERROR_MESSAGE_TEXT);
        } else {
            Person newperson = new Person(newName.get(), newPhone.get(), newEmail.get());
            personService.save(newperson);
        }
        List<Person> clients = personService.findAll();
        if (!clients.isEmpty()) {
            request.setAttribute(LISTGROUP, clients);
        }
        return new CommandResult(Page.WELCOME_PAGE.getPage(), false);
    }
}
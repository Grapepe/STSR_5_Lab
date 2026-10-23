package org.example.command.authorisation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.command.Command;
import org.example.command.CommandResult;
import org.example.command.session.SessionAttribute;
import org.example.exceptions.IncorrectDataException;
import org.example.exceptions.ServiceException;
import org.example.util.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SignOutCommand.class.getName());

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, IncorrectDataException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute(SessionAttribute.NAME);
        logger.info("user was above: name:" + username);
        session.removeAttribute(SessionAttribute.NAME);
        return new CommandResult(Page.LOGIN_PAGE.getPage(), false);
    }
}
package org.example.command.groupperson; // или новый пакет

import org.example.Model.Discipline;
import org.example.command.Command;
import org.example.command.CommandResult;
import org.example.exceptions.IncorrectDataException;
import org.example.exceptions.ServiceException;
import org.example.service.DisciplineService;
import org.example.util.pages.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowDisciplinesCommand implements Command {
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
            throws ServiceException, IncorrectDataException {
        DisciplineService service = new DisciplineService();
        List<Discipline> disciplines = service.findAll();
        if (!disciplines.isEmpty()) {
            request.setAttribute("disciplines", disciplines);
        }
        return new CommandResult("/WEB-INF/views/disciplines.jsp", false); // Создадим эту JSP
    }
}
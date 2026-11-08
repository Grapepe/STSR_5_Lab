package org.example.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.command.session.SessionAttribute;
import org.example.util.pages.Page;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/controller")
public class LoginRequiredFilter implements Filter {
    private static final String COMMAND = "command";
    private static final String WELCOME = "welcome";
    private static final String ERROR_MESSAGE = "error_message";
    private static final String ERROR_TEXT = "Нет авторизации для выполнения данной команды";
    private static final Logger logger = LogManager.getLogger(LoginRequiredFilter.class.getName());
    private static final String SHOW_DISCIPLINES = "show_disciplines"; // Новая команда

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws ServletException, IOException {
        // ... установка кодировки
        HttpServletRequest request = (HttpServletRequest) req;
        String command = request.getParameter(COMMAND);
        logger.info("Filter is working " + COMMAND + "= " + command);

        // Если команда НЕ требует авторизации - пропускаем
        if (!WELCOME.equals(command) && !SHOW_DISCIPLINES.equals(command)) {
            chain.doFilter(req, resp);
        }
        // Если команда требует авторизации, проверяем сессию
        else if (request.getSession().getAttribute(SessionAttribute.NAME) != null) {
            chain.doFilter(req, resp);
        }
        // Если сессии нет - кидаем на ошибку
        else {
            request.setAttribute(ERROR_MESSAGE, ERROR_TEXT);
            request.getRequestDispatcher(Page.ERROR_PAGE.getPage()).forward(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}


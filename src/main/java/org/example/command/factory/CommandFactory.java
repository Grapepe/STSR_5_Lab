package org.example.command.factory;

import org.example.command.Command;
import org.example.command.LoginPageCommand;
import org.example.command.RegisterPageCommand;
import org.example.command.authorisation.LoginCommand;
import org.example.command.authorisation.RegisterNewUserCommand;
import org.example.command.authorisation.SignOutCommand;
import org.example.command.groupperson.AddNewPersonCommand;
import org.example.command.groupperson.WelcomeCommand;

public class CommandFactory {
    public static Command create(String command) {
        if (command == null || command.isEmpty()) {
            return new LoginPageCommand();       // дефолт вместо исключения
        }
        command = command.toUpperCase();
        System.out.println(command);
        CommandType commandEnum;
        try {
            commandEnum = CommandType.valueOf(command);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }
        Command resultCommand;
        switch (commandEnum) {
            case LOGIN:
                resultCommand = new LoginCommand();
                break;
            case REGISTER_NEW_USER:
                resultCommand = new RegisterNewUserCommand();
                break;
            case SIGN_OUT:
                resultCommand = new SignOutCommand();
                break;
            case ADD_NEW_PERSON:
                resultCommand = new AddNewPersonCommand();
                break;
            case LOGIN_PAGE:
                resultCommand = new LoginPageCommand();
                break;
            case WELCOME:
                resultCommand = new WelcomeCommand();
                break;
            case REGISTRATION_PAGE:
                resultCommand = new RegisterPageCommand();
                break;
            default:
                return new LoginPageCommand();
        }
        return resultCommand;
    }
}
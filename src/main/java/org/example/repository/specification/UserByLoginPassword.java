package org.example.repository.specification;

import java.util.Arrays;
import java.util.List;

public class UserByLoginPassword implements Parameter {
    private final String login;
    private final byte[] password;

    public UserByLoginPassword(String login, byte[] password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public List<Object> getParameters() {
        return Arrays.asList(login, password);
    }
}
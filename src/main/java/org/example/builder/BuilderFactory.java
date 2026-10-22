package org.example.builder;

import java.util.Locale;

public class BuilderFactory {
    private static final String USER = "user";
    private static final String PERSON = "person";
    private static final String MESSAGE = "Unknown builder name: ";

    @SuppressWarnings("unchecked")
    public static <T> Builder<T> create(String builderName) {
        if (builderName == null) {
            throw new IllegalArgumentException(MESSAGE + "null");
        }

        switch (builderName.toLowerCase(Locale.ROOT)) {
            case USER:
            case "users":
                return (Builder<T>) new UserBuilderFactory();
            case PERSON:
            case "persons":
                return (Builder<T>) new PersonBuilderFactory();
            default:
                throw new IllegalArgumentException(MESSAGE + builderName);
        }
    }
}
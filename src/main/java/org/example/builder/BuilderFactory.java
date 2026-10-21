package org.example.builder;

public class BuilderFactory
{
    private static final String USER = "user";
    private static final String PERSON = "person";
    private static final String MESSAGE = "unknown";

    public static Builder create(String builderName)
    {
        switch (builderName)
        {
            case USER:
                return new UserBuilderFactory();
                break;
            case PERSON:
                return new PersonBuilderFactory();
                break;
            default:
                throw new IllegalArgumentException(MESSAGE);
        }
    }
}

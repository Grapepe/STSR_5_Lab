package org.example.exceptions;

public class IncorrectDataException extends Exception
{
    public IncorrectDataException(String message)
    {
        super(message);
    }
    public IncorrectDataException(Throwable cause)
    {
        super(cause);
    }
    public IncorrectDataException(String message, Throwable cause)
    {
        super(message, cause);
    }

}

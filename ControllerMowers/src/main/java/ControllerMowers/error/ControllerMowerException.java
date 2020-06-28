package ControllerMowers.error;

public class ControllerMowerException extends Exception
{

    static final long serialVersionUID = 3905376721543319761L;

    public ControllerMowerException(final String message)
    {
        System.out.println(message);
    }

    public ControllerMowerException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

}

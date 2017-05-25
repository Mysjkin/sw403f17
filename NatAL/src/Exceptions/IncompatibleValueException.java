package Exceptions;

/**
 * Created by Mathias on 22-03-2017.
 */
public class IncompatibleValueException extends Error
{
    public IncompatibleValueException(String message)
    {
        super(message);
    }
    public IncompatibleValueException(Object left, Object right, int lineNumber)
    {
        super("Incompatible types " + right + " can not be cast to " + left + " on line " + lineNumber);
    }
    public IncompatibleValueException(Object left, Object right)
    {
        super("Incompatible types " + right + " can not be cast to " + left);
    }
}

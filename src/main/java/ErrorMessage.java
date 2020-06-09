package lab6;
import lombok.Getter;
@Getter
public class ErrorMessage
{
    private static String error401 = "Authentication failed.";
    private static String error422 = "Data validation failed. Please check the response body for detailed error messages.";
    private static String error404 = "The requested resource does not exist.";
    private static String error405 = "Method not allowed. Please check the Allow header for the allowed HTTP methods.";

    public static String getError401Message()
    {
        return error401;
    }
    public static String getError422Message()
    {
        return error422;
    }
    public static String getError404Message()
    {
        return error404;
    }
    public static String getError405Message()
    {
        return error405;
    }
}

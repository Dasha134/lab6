package lab6;
// класс, содержит константы и формирует ссылки
public class EndPoints
{
    public static final String token = "shI583x5C2peQb_aQjO-ymzFXUzmhXsQsLyM";
    public static final String users = "/public-api/users";
    public static final String usersName = "/public-api/users?first_name=";
    public static final String usersID = "/public-api/users/";
    public static final String usersEmail = "/public-api/users?email=";
    public static String usersWithName(String name)
    {
        return usersName + name;
    }
    public static String usersWithID(int ID)
    {
        return usersID + Integer.toString(ID);
    }
    public static String usersWithEmail(String email)
    {
        return usersEmail + email;
    }
}

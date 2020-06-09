package lab6;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Random;
// аннотации lombok
@Getter
@Setter
@NoArgsConstructor
public class User
{
    Integer ID;
    String Name;
    String Surname;
    String Gender;
    String Email;
    public User(String name, String surname, String gender, String email)
    {
        this.ID = null;
        this.Name = name;
        this.Surname = surname;
        this.Gender = gender;
        this.Email = email;
    }
    public User(String name, String surname, String gender)
    {
        this.ID = null;
        this.Name = name;
        this.Surname = surname;
        this.Gender = gender;
        this.Email = GenerateEmail();
    }
    // метод для генерации случайного адреса
    public String GenerateEmail()
    {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        String RandomEmail = "";
        Random rand = new Random();
        // генерация случайной строки из 10 символов
        while (RandomEmail.length() < 10)
        {
            int index = rand.nextInt(chars.length());  // выбор случайного символа из строки chars
            RandomEmail += chars.charAt(index);        // формирование строки из 10 символов
        }
        // получение итогового адреса
        RandomEmail += "@mail.example";
        return RandomEmail;
    }
}
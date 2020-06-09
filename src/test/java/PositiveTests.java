import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab6.EndPoints;
import lab6.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class PositiveTests
{
    ObjectMapper objectMapper = new ObjectMapper();  // объект для JSON сериализации
    // метод, который выполняется перед классом
    @BeforeClass
    public void beforeClass()
    {
        RestAssured.baseURI = "https://gorest.co.in";
        RestAssured.urlEncodingEnabled = false;
    }
    // 1. GET /public-api/users : получить список всех пользователей
    @Test
    public void GetAllUsers()
    {
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token)
            // запрос GET
            .when()
            .get(EndPoints.users)
            .then()
            .assertThat()                      // проверка
            .body("_meta.code", equalTo(200))  // что тело ответа содержит код 200
            .log().body();                     // лог в консоли
    }
    // 2. GET /public-api/users?first_name=<user_name> : получить список пользователей с указанным именем
    @Test(dataProvider = "ValidUser")
    public void GetUsersWithNames(User user)
    {
        String name = user.getName();
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token)
            // запрос GET
            .get(EndPoints.usersWithName(name))
            .then()
            .assertThat()                      // проверка
            .body("_meta.code", equalTo(200))  // что тело ответа содержит код 200
            .and()
            .body("result.first_name", hasItem(name))
            .log().body();
    }
    // 3. POST /public-api/users : создать нового пользователя
    @Test(dataProvider = "ValidUser")
    public void NewUser(User user) throws JsonProcessingException
    {
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token)
            .body(objectMapper.writeValueAsString(user))
            // запрос POST
            .when()
            .post(EndPoints.users)
            .then()
            .assertThat()                      // проверка
            .body("_meta.code", equalTo(201))  // что тело ответа содержит код 201
            .and()
            .body("result.email", equalTo(user.getEmail()))
            .body("result.first_name", equalTo(user.getName()))
            .body("result.last_name", equalTo(user.getSurname()))
            .body("result.gender", equalTo(user.getGender()))
            .log().body();
    }
    // 4. GET /public-api/users/<user_id> : получить пользователя по его ID
    @Test(dataProvider = "UserID")
    public void GetUserWithID(int ID)
    {
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token)
            // запрос GET
            .when()
            .get(EndPoints.usersWithID(ID))
            .then()
            .assertThat()                       // проверка
            .body("_meta.code", equalTo(200))   // что тело ответа содержит код 200
            .and()
            .body("result.id", equalTo(Integer.toString(ID)))
            .log().body();
    }
    // 5. PUT /public-api/users/<user_id> : изменить пользователя с указанным ID
    @Test(dataProvider = "UserID")
    public void PutUserWithID(int ID) throws JsonProcessingException
    {
        User user = new User("AAA", "bbb", "male","akfj@dfjn.dksfr");
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token)
            .body(objectMapper.writeValueAsString(user))
            // запрос PUT
            .when()
            .put(EndPoints.usersWithID(ID))
            .then()
            .assertThat()                       // проверка
            .body("_meta.code", equalTo(200))   // что тело ответа содержит код 200
            .and()
            .body("result.id", equalTo(Integer.toString(ID)))
            .body("result.email", equalTo(user.getEmail()))
            .body("result.first_name", equalTo(user.getName()))
            .body("result.last_name", equalTo(user.getSurname()))
            .body("result.gender", equalTo(user.getGender()))
            .log().body();
    } 
    // 6. DELETE /public-api/users/<user_id> : удалить пользователя с указанным ID
    @Test(dataProvider = "UserID")
    public void DeleteUserWithID(int ID)
    {
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token)
            // запрос DELETE
            .when()
            .delete(EndPoints.usersWithID(ID))
            .then()
            .assertThat()                       // проверка
            .body("_meta.code", equalTo(204))   // что тело ответа содержит код 204
            .log().body();
    }
	// DataProvider данных пользователя
    @DataProvider(name = "ValidUser")
    public Object[][] ValidUser()
    {
        User user = new User("Hayley", "Smith", "female");
        return new Object[][]
        {
            {user}
        };
    }
	// DataProvider ID пользователя
    @DataProvider(name = "UserID")
    public Object[][] userID()
    {
        return new Object[][]
        {
            {1744}
        };
    }
}

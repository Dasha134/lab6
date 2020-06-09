import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab6.EndPoints;
import lab6.ErrorMessage;
import lab6.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
public class NegativeTests
{
    ObjectMapper objectMapper = new ObjectMapper();  // объект для JSON сериализации
    // метод, который выполняется перед классом
    @BeforeClass
    public void beforeClass()
    {
        RestAssured.baseURI = "https://gorest.co.in";
        RestAssured.urlEncodingEnabled = false;
    }
    // 1. GET /public-api/users без указания токена авторизации
    @Test
    public void NoToken()
    {
        when()
            // запрос GET
            .get(EndPoints.users)
            .then()
            .assertThat()                     // проверка
            .log().all()
            .body("_meta.code", equalTo(401)) // что тело ответа содержит код 401
            .body("_meta.message", equalTo(ErrorMessage.getError401Message()));
    }
    // 1. GET /public-api/users с некорректным токеном
    @Test
    public void WrongToken()
    {
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token + "w13")
            // запрос GET
            .when()
            .get(EndPoints.users)
            .then()
            .assertThat()                      // проверка
            .log().body()
            .body("_meta.code", equalTo(401))  // что тело ответа содержит код 401
            .body("_meta.message", equalTo(ErrorMessage.getError401Message()));
    }
    // 2. POST /public-api/users с некорректным форматом тела запроса
    @Test
    public void PostInvalidUser() throws JsonProcessingException
    {
        User user = new User();
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
            .body("_meta.code", equalTo(422))  // что тело ответа содержит код 422
            .body("_meta.message", equalTo(ErrorMessage.getError422Message()))
            .log().all();
    }
    // 3. DELETE /public-api/users
    @Test
    public void DeleteInvalidUser()
    {
        given()
            .contentType(ContentType.JSON)
            .header(
                "Authorization",
                "Bearer " + EndPoints.token)
            // запрос DELETE
            .when()
            .delete(EndPoints.users)
            .then()
            .assertThat()                      // проверка
            .body("_meta.code", equalTo(405))  // что тело ответа содержит код 405
            .body("_meta.message", equalTo(ErrorMessage.getError405Message()))
            .log().all();
    }
}

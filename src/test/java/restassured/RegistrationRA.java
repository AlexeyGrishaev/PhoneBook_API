package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.AuthRequestDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class RegistrationRA {

    String endPoint = "/v1/user/registration/usernamepassword";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
    }

    @Test
    public void registrationSuccess(){
        int i =(int)(System.currentTimeMillis()/1000)%3600;
        AuthRequestDTO  auth = AuthRequestDTO.builder()
                .username("locker"+i+"@gmail.com")
                .password("Qwerty1234!")
                .build();

        String token = given()
                .body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endPoint)
                .then()
                .assertThat().statusCode(200)
                .extract()
                .response()
                .path("token");
        System.out.println(token);

    }
    @Test
    public void registrationWrongEmail(){
        AuthRequestDTO  auth = AuthRequestDTO.builder()
                .username("lockergmail.com")
                .password("Qwerty1234!")
                .build();

        given().body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endPoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.username",containsString("must be a well-formed email adress"));

    }

    @Test
    public void registrationWrongPassword(){
        AuthRequestDTO  auth = AuthRequestDTO.builder()
                .username("locker@gmail.com")
                .password("Qwerty123")
                .build();

        given().body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endPoint)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.password",containsString("At least 8 characters"));

    }
    @Test
    public void registrationDuplicateUser(){
        AuthRequestDTO  auth = AuthRequestDTO.builder()
                .username("locker@gmail.com")
                .password("Qwerty1234!")
                .build();

        given().body(auth)
                .contentType(ContentType.JSON)
                .when()
                .post(endPoint)
                .then()
                .assertThat().statusCode(409)
                .assertThat().body("message",containsString("User already exists"));

    }
}

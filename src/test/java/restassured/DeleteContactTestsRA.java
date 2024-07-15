package restassured;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import dto.ContactDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class DeleteContactTestsRA {
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG9ja2VyQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIxNjQ0MDA0LCJpYXQiOjE3MjEwNDQwMDR9.Gpxy-8NPmPxRFkLRlTmR-fgQ_nAJNmccdZfF5CY0jA4\"";

    String id;
    String endPoint = "contacts";

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Max")
                .lastName("SEc")
                .email("KUre@gmail.com")
                .phone("123456789"+i)
                .address("NY")
                .description("Friend")
                .build();

        String message = given()
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract()
                .path("message");
        String[] all = message.split(": ");
        id=all[1];
    }

    @Test
    public void deleteContactSuccess(){
        given()
                .header("Authorization",token)
                .when()
                .delete("contacts/"+id)
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",equalTo("Contacts was deleted!"));


    }
    @Test
    public void deleteContactWrongToken(){
        given()
                .header("Authorization","ASasd")
                .when()
                .delete("contacts/"+id)
                .then()
                .assertThat().statusCode(401);
    }
}

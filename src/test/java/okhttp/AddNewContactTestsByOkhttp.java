package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ErrorDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class AddNewContactTestsByOkhttp {
    Gson gson = new Gson();
    public static final MediaType JSON =MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG9ja2VyQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIxNjQ0MDA0LCJpYXQiOjE3MjEwNDQwMDR9.Gpxy-8NPmPxRFkLRlTmR-fgQ_nAJNmccdZfF5CY0jA4";


    @Test
    public void addContactSuccessAllFields() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Max")
                .lastName("Satin"+i)
                .phone("12345446"+i)
                .email("Max@gmail.com")
                .address("NY")
                .description("Friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(),MessageDTO.class);
        Assert.assertTrue(messageDTO.getMessage().contains("Contact was added!"));

    }
    @Test
    public void addContactSuccessFields() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Max")
                .lastName("Satin"+i)
                .phone("12345446"+i)
                .email("Max@gmail.com")
                .address("NY")

                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(),MessageDTO.class);
        Assert.assertTrue(messageDTO.getMessage().contains("Contact was added!"));

    }
    @Test
    public void addContactUnauthorized() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Max")
                .lastName("Satin"+i)
                .phone("12345446"+i)
                .email("Max@gmail.com")
                .address("NY")
                .address("NY")
                .description("Friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization","asdsa")
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());


    }
    @Test
    public void addContactWrongName() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("")
                .lastName("Satin"+i)
                .phone("12345446"+i)
                .email("Max@gmail.com")
                .address("NY")
                .description("Friend")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        ErrorDTO errorDTO = gson.fromJson(response.body().string(),ErrorDTO.class);
        Assert.assertEquals(errorDTO.getMessage().toString(),"{name=must not be blank}");

    }
}

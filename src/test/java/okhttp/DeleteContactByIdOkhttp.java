package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.MessageDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdOkhttp {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibG9ja2VyQGdtYWlsLmNvbSIsImlzcyI6IlJlZ3VsYWl0IiwiZXhwIjoxNzIwODcwNTQ4LCJpYXQiOjE3MjAyNzA1NDh9.fgqOnHO8qywxo2Ha67f8AM0Ql26KMZpVmsyNFm2swX8";
    Gson gson = new Gson();
    public static final MediaType JSON =MediaType.get("application/json;charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    //b60efd09-e890-492e-944e-28b3a50b7593

    String id;


    @BeforeMethod
    public void preCondition() throws IOException {
        //createContact
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDTO = ContactDTO.builder()
                .name("Max")
                .lastName("OIR")
                .phone("12345446"+i)
                .email("wews"+i+"@gmail.com")
                .address("NY")
                .description("friend")
                .build();
        RequestBody body = RequestBody.create(gson.toJson(contactDTO),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .post(body)
                .addHeader("Authorization",token)
                .build();
        //getMessage id from message
        //id=*;
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        MessageDTO messageDTO = gson.fromJson(response.body().string(),MessageDTO.class);
        String message = messageDTO.getMessage();
        String[] all = message.split(": ");
        id =all[1];
        System.out.println(id);

    }
    @Test
    public void deleteContactByIdSuccess() throws IOException {
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .delete()
                .addHeader("Authorization",token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        MessageDTO dto = gson.fromJson(response.body().string(), MessageDTO.class);
        Assert.assertEquals(dto.getMessage(),"Contact was deleted!");

    }
}

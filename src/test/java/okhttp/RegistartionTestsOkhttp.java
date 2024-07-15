package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistartionTestsOkhttp {

    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    @Test
    public void registrationSuccess() throws IOException {

        int i = (int)(System.currentTimeMillis()/1000)%3600;

        AuthRequestDTO auth =  AuthRequestDTO.builder()
                .username("locker"+i+"@gmail.com")
                .password("Qwerty1234!")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);

        Request request =new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();


        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());

    }
    @Test
    public void registrationSuccessSecond() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("locker"+i+"@gmail.com")
                .password("Qwerty1234!")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);
        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }
    @Test
    public void registrationWrongPasswrod() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("locker"+i+"@gmail.com")
                .password("Qwerty123")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getMessage().toString(),"{password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");

    }
    @Test
    public void registrationWrongEmail() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("locker"+i+"gmail.com")
                .password("Qwerty1234!")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),400);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getMessage().toString(),"{username=must be a well-formed email address}");

    }
    @Test
    public void registrationUserAlreadyExist() throws IOException {
        int i = (int)(System.currentTimeMillis()/1000)%3600;
        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("locker@gmail.com")
                .password("Qwerty1234!")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),409);
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getMessage());
        Assert.assertEquals(errorDTO.getMessage().toString(),"User already exists");

    }
//int i = (int)(System.currentTimeMillis()/1000)%3600;
}

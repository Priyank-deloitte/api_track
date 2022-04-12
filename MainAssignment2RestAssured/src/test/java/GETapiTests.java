import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

import static java.util.concurrent.CompletableFuture.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;


public class GETapiTests {


    @Test
    public void get_api() {
        Response response = RestAssured.get("https://gorest.co.in/public/v1/users");

        System.out.println("Response : " + response.asString());
        System.out.println("Status code : " + response.statusCode());
        System.out.println("Header : " + response.getHeader("content-type"));


    }

    @Test
    public void genderValidation(){
        Response response = RestAssured.get("https://gorest.co.in/public/v1/users");

        JSONObject object = new JSONObject(response.asString());
        JSONArray arr = object.getJSONArray("data");

        for (int i = 0; i < arr.length(); i++) {
            assertThat(arr.getJSONObject(i).get("gender"), anyOf(is("male"), is("female")));

        }

    }
    @Test
    public void emailValidation() {
        Response response = RestAssured.get("https://gorest.co.in/public/v1/users");

        JSONObject object = new JSONObject(response.asString());
        JSONArray arr = object.getJSONArray("data");
        int count = 0;
        for (int i = 0; i < arr.length(); i++) {
            String userEmail = (String) arr.getJSONObject(i).get("email");
            if (userEmail.endsWith(".biz")) {
                count = count + 1;

            }
        }
        if(count<2){
            Assert.fail(".biz extension should be present in atleast 2 user's email!!");
        }

    }

    @Test
    public void uniqueIdValidation(){
        Response response = RestAssured.get("https://gorest.co.in/public/v1/users");

        JSONObject object = new JSONObject(response.asString());
        JSONArray arr = object.getJSONArray("data");
        int[] idValue = new int[arr.length()];
        for (int i = 0; i < arr.length(); i ++){
            idValue[i] = (int) arr.getJSONObject(i).get("id");

        }
        Arrays.sort(idValue);
        for (int i = 0; i< idValue.length-1; i++){
            if(idValue[i] == idValue[i+1]){
                Assert.fail();

            }
        }
    }

    @Test
    public void JsonSchemaValidation(){
        Response response = RestAssured.get("https://gorest.co.in/public/v1/users");
        response.body(matchesJsonSchemaInClasspath("JSON_schema.json"));

    }

}

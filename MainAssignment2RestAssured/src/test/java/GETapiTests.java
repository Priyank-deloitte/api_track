import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Arrays;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;

public class GETapiTests {


    @BeforeMethod
    public void get_api() {
        given().
                baseUri("https://gorest.co.in/public/v1").
                header("Content-Type", "application/json").
                when().
                get("/users").
                then().
                statusCode(200);

    }

    @Test
    public void genderValidation(){
        Response response = given().
                when().
                get("https://gorest.co.in/public/v1/users").
                then().extract().response();

        JSONObject object = new JSONObject(response.asString());
        JSONArray arr = object.getJSONArray("data");

        for (int i = 0; i < arr.length(); i++) {
            assertThat(arr.getJSONObject(i).get("gender"), anyOf(is("male"), is("female")));

        }

    }
    @Test
    public void emailValidation() {
        Response response = given().
                when().
                get("https://gorest.co.in/public/v1/users").
                then().extract().response();
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
        Response response = given().
                when().
                get("https://gorest.co.in/public/v1/users").
                then().extract().response();
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
        given().
                when().
                get("https://gorest.co.in/public/v1/users").
                then().
                body(matchesJsonSchemaInClasspath("SampleJsonSchema.json"));


    }

}

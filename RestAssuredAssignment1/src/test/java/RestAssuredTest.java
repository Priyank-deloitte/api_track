import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {

    private RequestSpecification given;

    @Test
    public void get_call(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("content type","applicaion/json").
                when().
                get("/posts").
                then().
                statusCode(200);
    }

    @Test
    public void get_verify(){

        Response response= given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then().extract().response();
        JSONArray arr= new JSONArray(response.asString());
        Object id= arr.getJSONObject(39).get("id");
        Object userId= arr.getJSONObject(39).get("userId");
        System.out.println("User with "+id+"have userId= "+userId);

        for(int i =0; i <arr.length();i++){
            Object obj= arr.getJSONObject(i).get("title");
            System.out.println(obj.getClass().getName());
        }
    }

    @Test
    public void put_call(){
        File jsonData= new File("C:\\Users\\priyankverma\\IdeaProjects\\RestAssuredAssignment1\\src\\test\\resources\\PutData.json");
        given()
                .baseUri("https://reqres.in/api").
                body(jsonData)
                .header("Content-Type","application/json")
                .when()
                .put("/users")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("name", equalTo("Arun"))
                .body("job",equalTo("Manager"));

    }
}
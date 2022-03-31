import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTest {

    String uri1= "https://jsonplaceholder.typicode.com";

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(uri1)
                .addHeader("Content-Type","application/json");

        requestSpecification= RestAssured.with().spec(requestSpecBuilder.build());
        responseSpecification=RestAssured.expect()
                .contentType(ContentType.JSON);
    }

    @Test
    public void get_call(){
        Response response= requestSpecification.get("/posts");
        assertThat(response.statusCode(), equalTo(200));

        JSONArray arr= new JSONArray(response.asString());
        Object id= arr.getJSONObject(39).get("id");
        Object userId= arr.getJSONObject(39).get("userId");
        System.out.println("User with "+id+"have userId= "+userId);

        for(int i =0; i <arr.length();i++){
            Object obj= arr.getJSONObject(i).get("title");
            System.out.println(obj.getClass().getName());
        }

    }

}

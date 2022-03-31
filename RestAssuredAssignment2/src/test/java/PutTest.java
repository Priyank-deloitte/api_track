import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PutTest {

    String uri2= "https://reqres.in/api";

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeTest
    public void setup(){
        RequestSpecBuilder requestSpecBuilder= new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(uri2)
                .addHeader("Content-Type","application/json");

        requestSpecification= RestAssured.with().spec(requestSpecBuilder.build());
        responseSpecification=RestAssured.expect()
                .contentType(ContentType.JSON);

    }
    @Test
    public void put_call(){
        Response response=requestSpecification.put("/users");
        assertThat(response.statusCode(), equalTo(200));

    }
}

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredLoginTest {

    String apiLink = "https://api-nodejs-todolist.herokuapp.com/user";

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeTest
    public void setup() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(apiLink)
                .addHeader("Content-Type", "application/json");
        requestSpecification = RestAssured.with().spec(requestSpecBuilder.build());

        ResponseSpecBuilder specBuilder= new ResponseSpecBuilder().
                expectContentType(ContentType.HTML);
        responseSpecification=specBuilder.build();

    }

    @Test
        public void get_call_Test(){

            given()
                    .spec(requestSpecification)
                    .when()
                    .get("/register")
                    .then()
                    .spec(responseSpecification).statusCode(404).log().all();



    }

}
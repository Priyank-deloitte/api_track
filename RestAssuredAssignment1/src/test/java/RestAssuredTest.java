import com.sun.org.apache.xerces.internal.util.PropertyState;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class RestAssuredTest {

    @Test
    public void get_call(){
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                header("content type","applicaion/json").
                when().
                get("/posts").
                then().body("id", hasItems(1,2),
                        "[39].id)", is(equalTo(4))).
                statusCode(200);


    }


    @Test
    public void put_call(){
        File jsonData= new File("src//test//resources//PutData.json");
        given().
                baseUri("https://jsonplaceholder.typicode.com").
                body(jsonData).
                header("content type","application/json").
                when().
                put("/posts").
                then().
                statusCode(200);

    }


    }

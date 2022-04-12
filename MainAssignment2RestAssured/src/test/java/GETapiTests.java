import io.restassured.RestAssured;
import io.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import javax.xml.bind.annotation.XmlElementWrapper;

public class GETapiTests {


    @Test
    void test1() {
        Response response = RestAssured.get("https://gorest.co.in/public/v1/users");

        System.out.println("Response : " + response.asString());
        System.out.println("Status code : " + response.statusCode());
        System.out.println("Header : " + response.getHeader("content-type"));

        JSONObject object = new JSONObject(response.asString());
        JSONArray arr = object.getJSONArray("data");

        for (int i = 0; i < arr.length(); i++) {
            System.out.println(arr.getJSONObject(i).get("gender"));


        }

    }

}

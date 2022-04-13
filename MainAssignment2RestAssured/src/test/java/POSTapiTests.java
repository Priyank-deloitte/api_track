import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class POSTapiTests extends javaUtility {
    Response response;

    // TO ADD LOGGING IN OUR PROGRAM
    public static Logger log = LogManager.getLogger(POSTapiTests.class);

    @BeforeSuite
    public void nothing() {
        RestAssured.useRelaxedHTTPSValidation();
    }


    //Path and sheet name of the database(Excel File)
    String Path_Of_Excel_File = "PostDataBase.xlsx";
    String SHEET_NAME_INSIDE_THE_EXCEL = "DataSheet";

    //Registering new user with the required details
    @Test(priority = 1)
    public void postData() throws IOException {

        log.info("Fetching the user database from excel file");
        //Fetching number of rows we have in excl file
        int rowCount = javaUtility.getRowCount(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL);
        System.out.println(rowCount);
        //Iterating through the rows
        for (int i = 1; i <=rowCount; i++) {
            String name = javaUtility.getCellValue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 0);
            String gender = javaUtility.getCellValue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 1);
            String email = javaUtility.getCellValue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 2);
            String status = javaUtility.getCellValue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 3);
            Map bodyParameters = new LinkedHashMap();
            bodyParameters.put("name", name);
            bodyParameters.put("gender", gender);
            bodyParameters.put("email", email);
            bodyParameters.put("status", status);
            //Google Gson is a simple Java-based library to serialize Java objects to JSON and vice versa
            //Converting hashmap to json object
            Gson gson = new Gson();
            String json = gson.toJson(bodyParameters, LinkedHashMap.class);
            log.info("name, gender, email and status added");
            response = (Response) given().baseUri("https://gorest.co.in").
                    header("Content-Type","application/json").
                    header("Authorization","Bearer "+ tokenValue).
                    body(json).
                    when().
                    post("/public/v1/users").
                    then().extract();
            System.out.println("Account registered");
            System.out.println(response.asString());

        }

    }
    @Test(priority = 2)
    public void UserValidation(){
        Assert.assertEquals(response.statusCode(),422);
    }


}

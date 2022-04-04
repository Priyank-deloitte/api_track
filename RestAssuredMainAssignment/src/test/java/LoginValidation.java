import com.google.gson.Gson;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class LoginValidation {

    String Path_Of_Excel_File = "C:\\Users\\priyankverma\\Desktop\\database.xlsx";
    String SHEET_NAME_INSIDE_THE_EXCEL = "Sheet1";


    private  static final String LOG_FILE = "log4j.properties";

    // TO ADD LOGGING IN OUR PROGRAM
    private static Logger log  = LogManager.getLogger(LoginValidation.class);


    @Test(priority = 2)
    public void user_Login_and_Validation() throws IOException {

        log.info("LOGGING IN");
        int rowCount = javaUtility.getRowCount(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL);


        for (int i = 1; i <=rowCount; i++) {


            String email = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 1);
            String password = javaUtility.getCellvalue(Path_Of_Excel_File, SHEET_NAME_INSIDE_THE_EXCEL, i, 2);


            log.info("Entered id and password");

            Map bodyParameters = new LinkedHashMap();

            bodyParameters.put("email", email);
            bodyParameters.put("password", password);

            Gson gson = new Gson();
            String json = gson.toJson(bodyParameters, LinkedHashMap.class);



            Response response = (Response) given().baseUri("https://api-nodejs-todolist.herokuapp.com/user/login").
                    contentType("application/json").
                    body(json).
                    when().
                    post().
                    then().statusCode(200).extract();

            System.out.println(response.asString());

            log.info("Successfully logged in");


            //Validating Credentials
            JSONObject arr = new JSONObject(response.asString());
            //System.out.println(arr.get("token"));
            assertThat(arr.getJSONObject("user").get("email"),equalTo(email));

            if(arr.getJSONObject("user").get("email").equals(email))
            {
                System.out.println("VALID CREDENTIALS EMAIL GOT MATCHED");
                log.info("VALID CREDENTIALS EMAIL GOT MATCHED");
            }
            else
            {
                System.out.println("INVALID CREDENTIAL EMAIL DOES NOT MATCHED");
                log.info("INVALID CREDENTIAL EMAIL DOES NOT MATCHED");
            }

        }

    }

}
package userManagement;

import core.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ExtentReport;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class getErgast extends BaseTest {
    @Test(description = "Validate the status code for GET users endpoint", groups = "RegressionSuite")
    public void validateResponseBodyGetPathParam() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validateResponseBodyGetPathParam", "Validate 200 Status Code for GET Ergast");
        Response resp = given()
                .pathParam("raceSeason", 2016)
                .when()
                .get("http://ergast.com/api/f1/{raceSeason}/circuits.json"); //RestAssured
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println(resp.body().asString());
    }
}

package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.Constants;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class CoopApplicationAPI {

    private static final String RESPONSE_MESSAGE = "Hey look at that";
    private static final String ERROR_MESSAGE = "The access token provided is invalid";

    @Test
    public void validatePostEggsCollect() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer a26ede1c63ec581a997b4df97428b08476f09317")
                .when()
                .post("http://coop.apps.symfonycasts.com/api/4491/eggs-collect");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        assertTrue(response.getBody().path("message").toString().contains(RESPONSE_MESSAGE));
        assertTrue(response.getBody().path("success"));
        System.out.println("validatePostEggsCollect executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validatePostEggsCollectInvalidToken() {
        Response response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer a26ede1c63ec581a997b4df97428b08476f0931")
                .when()
                .post("http://coop.apps.symfonycasts.com/api/4491/eggs-collect");
        assertEquals(response.getStatusCode(), StatusCode.UNAUTHORIZED.code);
        assertEquals(response.getBody().path("error_description"), ERROR_MESSAGE);
        System.out.println("validatePostEggsCollectInvalidToken executed successfully");
        System.out.println(response.getBody().asString());
    }

    @Test
    public void validateGenerateTokenWithFormParam() {
        Response response = given()
                .header("Content-Type", Constants.CONTENT_TYPE_FORM_PARAM)
                .formParam("client_id", "TestWithSidharth")
                .formParam("client_secret", "c506f6a65f3566468cac23adfd3da1a6")
                .formParam("grant_type", Constants.GRANT_TYPE)
                .when()
                .post("http://coop.apps.symfonycasts.com/token");
        assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code);
        assertNotNull(response.getBody().path("access_token"));
        assertEquals(response.getBody().path("token_type"), "Bearer");
        assertEquals(response.getBody().path("scope"), "chickens-feed eggs-collect");
        System.out.println("validateGenerateTokenWithFormParam executed successfully");
        System.out.println(response.getBody().asString());
    }
}

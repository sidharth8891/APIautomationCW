package userManagement;

import core.StatusCode;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class AutomateChatGPTAPI {

    @Test(description = "Validate status code for GET list models")
    public void getListModels() {
        Response resp = given().
                header("Authorization", "Bearer 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").
                when().get("https://api.openai.com/v1/models");
        assertEquals(resp.statusCode(), StatusCode.SUCCESS.code);
        System.out.println(resp.getBody().asString());
        System.out.println("TestCase getListModels executed successfully");
    }

    @Test(description = "Validate status code for GET retrieve models")
    public void getRerieveModel() {
        Response resp = given().
                header("Authorization", "Bearer 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").
                pathParam("model_id","babbage").
                when().get("https://api.openai.com/v1/models/:model_id");
        assertEquals(resp.statusCode(), StatusCode.SUCCESS.code);
        System.out.println(resp.getBody().asString());
        System.out.println("TestCase getRerieveModel executed successfully");
    }
}

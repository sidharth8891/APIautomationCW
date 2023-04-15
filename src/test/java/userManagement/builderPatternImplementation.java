package userManagement;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class builderPatternImplementation {

    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @Test
    public void testRestAssuredNormalApproach() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .contentType(ContentType.JSON)
                .queryParam("userId", "1")
                .when()
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testRestAssuredBuilderPattern() {
        given()
                .spec(getRequestSpecificationBuilder("1", "application/json"))
                .when()
                .get("/posts")
                .then()
                .spec(getResponseSpecificationBuilder(200, "application/json"));
    }

    private RequestSpecification getRequestSpecificationBuilder(String queryParam, String contentType) {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com")
                .setContentType(contentType)
                .addQueryParam("userId", queryParam)
                .build();
        return requestSpec;
    }

    private ResponseSpecification getResponseSpecificationBuilder(int statusCode, String contentType) {
        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .expectContentType(contentType)
                .build();
        return responseSpec;
    }

}

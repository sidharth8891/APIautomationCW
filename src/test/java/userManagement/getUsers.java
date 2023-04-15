package userManagement;

import core.BaseTest;
import core.StatusCode;
import io.restassured.RestAssured;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.JsonReader.getJsonArray;

public class getUsers extends BaseTest {
    String serverAddress = PropertyReader.propertyReader("config.properties", "server");
    String endpoint = getUrl("endpoint");
    String URL = serverAddress + endpoint;
    //SoftAssertionUtil softAssertion = new SoftAssertionUtil();

    @Test
    public void getUserData() {
        given().
                when().get("https://reqres.in/api/users?page=2").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void validateGetResponseBody() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and validate the response body using 'then'
        given()
                .when()
                .get("/todos/1")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(isEmptyString()))
                .body("title", equalTo("delectus aut autem"))
                .body("userId", equalTo(1));
    }

    @Test
    public void validateResponseHasItems() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response =
                given()
                        .when()
                        .get("/posts")
                        .then()
                        .extract()
                        .response();

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("title"), hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "qui est esse"));
    }

    @Test
    public void validateResponseHasSize() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body has a specific size
        assertThat(response.jsonPath().getList(""), hasSize(500));
    }

    @Test
    public void validateListContainsInOrder() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments?postId=1")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items in a specific order
        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");
        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
    }

    //validate single field from an list of map
    @Test
    public void testGetUserList() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        given()
                .when()
                .get("/comments?postId=1")
                .then()
                .statusCode(200)
                .body("email[1]", containsString("Jayne_Kuhic@sydney.com"));
    }

    @Test
    public void testGetUsersWithQueryParameters() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert that the response contains 6 users
        response.then().body("data", hasSize(6));

        // Assert that the first user in the list has the correct values
        response.then().body("data[1].id", is(8));
        response.then().body("data[1].email", is("lindsay.ferguson@reqres.in"));
        response.then().body("data[1].first_name", is("Lindsay"));
        response.then().body("data[1].last_name", is("Ferguson"));
        response.then().body("data[1].avatar", is("https://reqres.in/img/faces/8-image.jpg"));
    }

    @Test()
    public void validateStatusCodeGetUser() {
        System.out.println("*****************");
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get("https://reqres.in/api/users");
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
    }

    @Test
    public void testGetUsersWithMultipleQueryParams() {
        Response response =
                given()
                        .queryParam("page", 2)
                        .queryParam("per_page", 3)
                        .queryParam("rtqsdr", 4)
                        .when()
                        .get("https://reqres.in/api/users")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response();
    }

    @Test
    public void testCreateUserWithFormParam() {
        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "John Doe")
                .formParam("job", "Developer")
                .when()
                .post("https://reqres.in/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("John Doe"));
        response.then().body("job", equalTo("Developer"));
    }

    @Test
    public void testGetUserListWithHeader() {
        given()
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testGetUserListWithHeader Executed Successfully");
    }

    @Test
    public void testWithTwoHeaders() {
        given()
                .header("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx")
                .header("Content-Type", "application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testWithTwoHeaders Executed Successfully");
    }

    @Test
    public void testTwoHeadersWithMap() {

        // Create a Map to hold headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx");

        // Send a GET request with headers
        given()
                .headers(headers)
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);

        System.out.println("testTwoHeadersWithMap Executed Successfully");
    }

    @Test
    public void testFetchHeaders() {
        Response response = given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .extract().response();

        Headers headers = response.getHeaders();
        //if you want to print all headers then comment 244,246,247
        for (Header h : headers) {
            if (h.getName().contains("Server")) {
                System.out.println(h.getName() + " : " + h.getValue());
                assertEquals(h.getValue(), "cloudflare");
                System.out.println("testFetchHeaders Executed Successfully");
            }
        }
    }

    @Test
    public void testUseCookies() {
        given()
                .cookie("cookieKey1", "cookieValue1")
                .cookie("cookieKey2", "cookieValue2")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200);
        System.out.println("testUseCookies Executed Successfully");
    }

    @Test
    public void testFetchCookies() {
        Response response = given()
                .when()
                .get(URL + "?page=2")
                .then()
                .extract().response();

        Map<String, String> cookies = response.getCookies();
        Cookies cookies1 = response.getDetailedCookies();
        cookies1.getValue("server");
        assertEquals(cookies1.getValue("server"), "cloudflare");
        assertThat(cookies, hasKey("JSESSIONID"));
        assertThat(cookies, hasValue("ABCDEF123456"));
    }

    @Test(groups = {"SmokeSuite", "RegressionSuite"})
    public void verifyStatusCodeDelete() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("verifyStatusCodeDelete", "Validate 204 status code for DELETE Method");
        Response resp = given()
                .delete("https://reqres.in/api/users/2");
        assertEquals(resp.getStatusCode(), 200);
        System.out.println("verifyStatusCodeDelete executed successfully");
    }

    @Test(groups = "RegressionSuite")
    public void validateWithDataFromPropertiesFile() {
        ExtentReport.extentlog =
                ExtentReport.extentreport.
                        startTest("validateWithDataFromPropertiesFile", "Validate 200 Status Code for GET method");
        String serverAddress = PropertyReader.propertyReader("config.properties", "serverAddress");
        System.out.println("Server Address is : " + serverAddress);
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get(serverAddress);
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateWithDataFromPropertiesFile executed successfully" + serverAddress);
    }

    @Test()
    public void validateFromProperties_TestData() throws IOException, ParseException {
        String serverAddress = PropertyReader.propertyReader("config.properties", "server");
        String endpoint = JsonReader.getTestData("endpoint");
        String URL = serverAddress + endpoint;
        System.out.println("URL  is : " + URL);
        Response resp =
                given()
                        .queryParam("page", 2)
                        .when()
                        .get(URL);
        int actualStatusCode = resp.statusCode();  //RestAssured
        assertEquals(actualStatusCode, 200); //Testng
        System.out.println("validateFromProperties_TestData executed successfully" + URL);
    }

    public String getUrl(String key) {
        String endpoint = null;
        try {
            endpoint = JsonReader.getTestData(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return endpoint;
    }

    @Test
    public void hardAssertion() {
        System.out.println("hardAssert");
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        Assert.assertTrue(false);
        System.out.println("hardAssert");
    }

    @Test
    public void softAssertion() {

        System.out.println("softAssert");
        SoftAssertionUtil.assertTrue(true, "");
        SoftAssertionUtil.assertAll();
    }

    @Test
    public void validateWithSoftAssertUtil() {
        RestAssured.baseURI = "https://reqres.in/api";
        Response response = given()
                .queryParam("page", 2)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        SoftAssertionUtil.assertEquals(response.getStatusCode(), StatusCode.SUCCESS.code, "Status code is not 200");
        SoftAssertionUtil.assertAll();
        System.out.println("validateWithSoftAssertUtil executed successfully");
    }

    @DataProvider(name = "testdata")
    public Object[][] testData() {
        return new Object[][]{
                {"1", "John"},
                {"2", "Jane"},
                {"3", "Bob"}
        };
    }

    @Test(dataProvider = "testdata")
    @Parameters({"id", "name"})
    public void testEndpoint(String id, String name) {
        given()
                .queryParam("id", id)
                .queryParam("name", name)
                .when()
                .get("https://reqres.in/api/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void Test() throws IOException, ParseException {
        JsonReader.getJsonArrayData("technology", 2);
        JSONArray jsonArray = getJsonArray("contact");
        Iterator<String> iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}

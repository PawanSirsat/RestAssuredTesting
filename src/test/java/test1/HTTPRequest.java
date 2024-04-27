package test1;

import org.testng.annotations.Test;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class HTTPRequest {


    int userId;
    @Test
    void getUser() {
         given()
        .when()
           .get("https://reqres.in/api/users?page=2")
        .then()
           .statusCode(200)
           .body("page", equalTo(2))
           .log().all();
    }
    
  
    @Test(priority = 1)
    void createUser() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put("name", "pawan");
        user.put("job", "leader");
        user.put("email", "pawan@gmail.com");

        userId=given()
            .contentType("application/json")
            .body(user)
        .when()
            .post("https://reqres.in/api/users")
            .jsonPath().getInt("id");
//        .then()
//            .statusCode(201)
//            .body("name", notNullValue()) // Asserting that "name" is not null
//            .body("job", notNullValue()) // Asserting that "job" is not null
//            .body("email", notNullValue()) // Asserting that "email" is not null
//            .body("email", matchesRegex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) // Validating email format
//            .log().all()
//            .extract().path("id"); // Extract the user ID and store it
//        
    }
    
    @Test(priority = 2 ,dependsOnMethods= {"createUser"})
    void updateUser() {
        HashMap<String, String> updatedUserData = new HashMap<String, String>();
        updatedUserData.put("name", "PawanSirsat");
        updatedUserData.put("job", "Devops");

        given()
            .contentType("application/json")
            .body(updatedUserData)
        .when()
            .put("https://reqres.in/api/users/{userId}", userId) // Provide the userId here
        .then()
            .statusCode(200)
            .log().all();
    }
    
    @Test(priority = 3,dependsOnMethods= {"createUser"})
    void deleteUser() {
        given()
        .when()
            .delete("https://reqres.in/api/users/{userId}", userId) // Provide the userId here
        .then()
            .statusCode(204)
            .log().all();
    }
//    
//    @Test
//    void getUserById() {
//        int userId = 84; // Replace this with the actual user ID you want to retrieve
//
//        given()
//            .pathParam("userId", userId)
//        .when()
//            .get("https://reqres.in/api/users/{userId}")
//        .then()
//            .statusCode(200)
//            .body("data.id", equalTo(userId)) // Validate that the returned user ID matches the requested ID
//            .log().all();
//    }


}

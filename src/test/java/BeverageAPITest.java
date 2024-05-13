import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class BeverageAPITest {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
        RestAssured.basePath = "/beverages";
    }

    @Test
    public void testGetAllBeverages() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("", hasSize(greaterThan(0)));
    }

    @Test
    public void testGetOneBeverage() {
        given()
                .pathParam("id", 1)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("green tea"));
    }

    @Test
    public void testCreateBeverage() {
        String newBeverage = "{ \"name\": \"mocha\", \"rating\": 6 }";
        given()
                .contentType("application/json")
                .body(newBeverage)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("name", equalTo("mocha"))
                .body("rating", equalTo(6));
    }

    @Test
    public void testDeleteBeverage() {
        given()
                .pathParam("id", 1)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateBeverage() {
        String updatedBeverage = "{ \"name\": \"nice coffee\", \"rating\": 6 }";

        given()
                .contentType("application/json")
                .body(updatedBeverage)
                .pathParam("id", 1)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("nice coffee"))
                .body("rating", equalTo(6));
    }

    @Test
    public void testPatchBeverage() {
        String patchBeverage = "{ \"name\": \"bad coffee\" }";

        given()
                .contentType("application/json")
                .body(patchBeverage)
                .pathParam("id", 1)
                .when()
                .patch("/{id}")
                .then()
                .statusCode(200)
                .body("name", equalTo("bad coffee"));
    }

}


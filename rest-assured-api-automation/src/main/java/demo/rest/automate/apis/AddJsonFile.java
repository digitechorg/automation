package demo.rest.automate.apis;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import demo.rest.auto.apis.files.PayLoad;
import demo.rest.auto.apis.files.Reuasablemathod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class AddJsonFile {

	public static void main(String[] args) throws IOException {
		// given - all input details
				// when - resources and https
				// Then - submit
				RestAssured.baseURI = "https://rahulshettyacademy.com";
				String response = given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json")
						.body( new String (Files.readAllBytes(Paths.get("C:\\Users\\44745\\OneDrive\\Desktop\\addPlace.json")))).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
						.body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response()
						.asString();

				System.out.println(" Responses got extracted :  " + response);

				JsonPath js = new JsonPath(response);
				String placeId = js.getString("place_id");
				System.out.println("Place id for https : " + placeId);

				// Update place

				
				String newaddress = "India,Bihar";
				given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json")
						.body(PayLoad.updatePlace(placeId,newaddress)).when().put("/maps/api/place/update/json").then().assertThat().log()
						.all().statusCode(200).body("msg", equalTo("Address successfully updated"));

				// Get Place

				 String getPlace = given().log().all().queryParam("key", "qaclick123").queryParam("place_id",placeId )
				.when().get("/maps/api/place/get/json").then().assertThat().log()
						.all().statusCode(200).extract().response().asString();
				 System.out.println(getPlace);
				 
				 //JsonPath js1 = new JsonPath(getPlace);
				 JsonPath js1 = Reuasablemathod.jsonpath1(getPlace);
				 String address = js1.getString("address");
				 System.out.println("The new address updated As : "+address);
				 
				 Assert.assertEquals(newaddress, address);
			}


	}



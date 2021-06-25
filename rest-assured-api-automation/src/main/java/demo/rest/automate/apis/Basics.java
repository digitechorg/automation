package demo.rest.automate.apis;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import demo.rest.auto.apis.files.PayLoad;
import demo.rest.auto.apis.files.Reuasablemathod;

public class Basics {

	public static void main(String[] args) {
		// given - all input details
		// when - resources and https
		// Then - submit
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("content-type", "application/json")
				.body(PayLoad.addPlace()).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
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

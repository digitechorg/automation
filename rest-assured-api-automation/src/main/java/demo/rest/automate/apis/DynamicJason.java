package demo.rest.automate.apis;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import demo.rest.auto.apis.files.PayLoad;
import demo.rest.auto.apis.files.Reuasablemathod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class DynamicJason {

	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type", "application/json")
				.body(PayLoad.addBook(isbn, aisle)).when().post("/Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();
		JsonPath js = Reuasablemathod.jsonpath1(response);
		String id = js.get("ID");
		System.out.println(id);

		String responseDelete = given().log().all().header("Content-Type", "application/json")
				.body(PayLoad.addBook(isbn, aisle)).when().delete("/Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath js2 = Reuasablemathod.jsonpath1(responseDelete);
		String id2 = js2.get("ID");
		System.out.println(id2);

	}

	@Test(dataProvider = "DeleteBookData")
	public void deletebook(String isbn, String aisle) {
		String responseDelete = given().log().all().header("Content-Type", "application/json")
				.body(PayLoad.addBook(isbn, aisle)).when().delete("/Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath js2 = Reuasablemathod.jsonpath1(responseDelete);
		String id2 = js2.get("ID");
		System.out.println(id2);

	}

	// Post
	@DataProvider(name = "BookData")

	public Object[][] getData() {
		return new Object[][] { { "isbdd23", "239223" }, { "isbn8223", "349253" }, { "isbn9564", "670254" } };
	}

	// Delete
	@DataProvider(name = "DeleteBookData")

	public Object[][] deleteGetData() {
		return new Object[][] { { "isbdd23", "239223" }, { "isbn8223", "349253" }, { "isbn9564", "670254" } };
	}
}

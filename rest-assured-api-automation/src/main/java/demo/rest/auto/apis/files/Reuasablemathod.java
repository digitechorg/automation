	package demo.rest.auto.apis.files;

import io.restassured.path.json.JsonPath;

public class Reuasablemathod {

	public static JsonPath jsonpath1(String response) {

		JsonPath js1 = new JsonPath(response);
		return js1;

	}

}

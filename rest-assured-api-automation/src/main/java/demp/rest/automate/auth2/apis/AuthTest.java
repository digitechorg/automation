package demp.rest.automate.auth2.apis;

import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class AuthTest {

	public static void main(String[] args) {

		// GetAuthorizationCode

		// GetAccessTokenRequest
		String accessTokenRes = given().queryParams("code", "")
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "application_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = new JsonPath(accessTokenRes);
		String accessToken = js.getString("access_token");

		// AccessToken
		String response = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(response);

	}

}

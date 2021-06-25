package demo.rest.automate.apis;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraRestAsuuredApi {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080";
		SessionFilter session = new SessionFilter();
		// Login
		String response = given().header("Content-Type", "application/json")
				.body("{ \"username\": \"testuserapi123\", \"password\": \"Password1!\" }").log().all().filter(session)
				.when().post("/rest/auth/1/session").then().log().all().extract().response().asString();

		// Adding comment
		String addComment = given().pathParam("key", "10102").log().all().header("Content-Type", "application/json")
				.body("{\r\n"
						+ "    \"body\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eget venenatis elit. Duis eu justo eget augue iaculis fermentum. Sed semper quam laoreet nisi egestas at posuere augue semper.\",\r\n"
						+ "    \"visibility\": {\r\n" + "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n" + "    }\r\n" + "}")
				.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
				.statusCode(201).extract().response().asString();
		JsonPath js = new JsonPath(addComment);
		String commentId = js.getString("id");

		// Add attachment
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10102").log().all()
				.header("Content-Type", "multipart/form-data").multiPart("file", new File("jira.txt")).when()
				.post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

		// Get issue
		String issueDetails = given().filter(session).pathParam("key", "10102").queryParam("fields", "comments").log()
				.all().when().get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
		System.out.println(issueDetails);
		JsonPath js1 = new JsonPath(issueDetails);
		int commentCount = js1.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentCount; i++) {

			String commentIdIssue = js1.get("fields.comment.comments[" + i + "].id").toString();
			if (commentIdIssue.equalsIgnoreCase(commentId)) {
				String message = js1.get("fields.comment.comments[" + i + "].body").toString();
				System.out.println(message);

			}

		}

	}

}

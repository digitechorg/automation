package demo.rest.automate.apis;

import demo.rest.auto.apis.files.PayLoad;
import io.restassured.path.json.JsonPath;

public class NestedJsonParse {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(PayLoad.nestedJson());

		// 1. Print No of courses returned by API
		int countCourses = js.getInt("courses.size()");
		System.out.println("Print No of courses returned by API : " + countCourses);
		// 2.Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Print Purchase Amount : " + purchaseAmount);
		// Print Title of the first course
		String titleFirstCourse = js.getString("courses[0].title");
		System.out.println("Print Title of the first course : " + titleFirstCourse);
		// Print All course titles and their respective Prices
		for (int i = 0; i < countCourses; i++) {
			String courseTitles = js.get("courses[" + i + "].title");
			System.out.println("courses title : " + courseTitles);
			int coursePrices = js.getInt("courses[" + i + "].price");
			System.out.println("Courses price : " + coursePrices);
			// Print no of copies sold by RPA Course
			System.out.println("Print no of copies sold by RPA Course");
			if (courseTitles.equalsIgnoreCase("RPA")) {
				int noCopiesSold = js.getInt("courses[" + i + "].copies");
				System.out.println(noCopiesSold);
				break;

			}

		}

		// Verify if Sum of all Course prices matches with Purchase Amount

	}

}

package demo.rest.automate.apis;

import org.testng.annotations.Test;

import demo.rest.auto.apis.files.PayLoad;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class SumValidation {

	@Test
	public void sumOfCourses() {
		int sum = 0;
		JsonPath js = new JsonPath(PayLoad.nestedJson());
		int countCourses = js.getInt("courses.size()");
		for (int i = 0; i < countCourses; i++) {

			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");
			int total = price * copies;
			System.out.println(total);
			sum = sum + total;

		}
		System.out.println(sum);
		int totalPurchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(totalPurchaseAmount, sum);

	}

}

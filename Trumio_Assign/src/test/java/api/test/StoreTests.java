package api.test;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.PetEndPoints;
import api.endpoints.StoreEndPoints;
import api.utils.FileUtils;
import io.restassured.response.Response;

public class StoreTests {
	Integer newOrderId;

	public Logger logger;

	@BeforeClass
	public void setup() {
		 logger = LogManager.getLogger(StoreTests.class);
	}

	@Test(priority = 1)
	public void testOrderPet() throws FileNotFoundException {

		logger.info(" Placing order for a pet ");
		String FilePath = "./src/test/resources/storeInputData/testorderpetdata.json";
		JSONObject storePayload = FileUtils.readFile(FilePath);

		Response res = StoreEndPoints.orderPet(storePayload.toString());
		res.then().log().body();
		newOrderId = res.jsonPath().getInt("id");
		Assert.assertEquals(res.getStatusCode(), 200, "Order not placed");

		logger.info(" Order Placed successfully ");

	}

	@Test(description = "Fetching order details by a valid order Id i.e btw 1 and 10", priority = 3)
	public void testGetOrder_validId() throws FileNotFoundException {

		logger.info(" Fetching pet details by a Valid Id ");

		String FilePath = "./src/test/resources/storeInputData/testgetorderdata.json";
		JSONObject petPayload = FileUtils.readFile(FilePath);

		newOrderId = petPayload.getJSONObject("ValidCase").getInt("id");
		Response res = PetEndPoints.getPet(newOrderId);
		res.then().log().body();

		Assert.assertEquals(res.getStatusCode(), 200, "Status code doesn't match");
		Assert.assertEquals(res.jsonPath().getInt("id"), newOrderId, "Order Id doesn't match ");

	}

	@Test(description = "Fetching order details by a invalid Id", priority = 4)
	public void testGetOrder_InvalidId() throws FileNotFoundException {

		logger.info(" Fetching order details by a Invalid Id ");

		String FilePath = "./src/test/resources/storeInputData/testgetorderdata.json";
		JSONObject petPayload = FileUtils.readFile(FilePath);

		newOrderId = petPayload.getJSONObject("InvalidCase").getInt("id");
		Response res = PetEndPoints.getPet(newOrderId);
		res.then().log().body();

		Assert.assertEquals(res.getStatusCode(), 404, "Status code doesn't match");

	}

	@Test(description = "Delete the order created", dependsOnMethods = { "testOrderPet" }, priority = 2)
	public void testDeleteOrder() {

		logger.info(" Delete the order created ");
		Response res = StoreEndPoints.deleteOrder(newOrderId);
		res.then().log().body();

		Assert.assertEquals(res.getStatusCode(), 200, "Cannot Delete Order");
		logger.info(" Deleted Successfully ");

	}
}

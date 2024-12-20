package api.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class StoreEndPoints {

	public static Response orderPet(String payload) {
		Response res=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(Routes.store_post_url);
		
		return res;
		
	}
	
	public static Response getOrder(int orderId) {
		Response res=given()
			.pathParam("orderId", orderId)
			
		.when()
			.get(Routes.store_get_url);
		
		return res;
		
	}
	
	public static Response deleteOrder(int orderId) {
		Response res=given()
			.pathParam("orderId", orderId)
			
		.when()
			.delete(Routes.store_delete_url);
		
		return res;
		
	}
}

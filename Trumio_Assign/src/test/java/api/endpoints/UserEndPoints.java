package api.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UserEndPoints {
	
	public static Response getUser(String userName) {
		Response res=given()
			.pathParam("username", userName)
			
		.when()
			.get(Routes.user_get_url);
		
		return res;
		
	}

	public static Response createUser(String payload) {
		Response res=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(Routes.user_post_url);
		
		return res;
		
	}
	public static Response updateUser(String payload, String userName) {
		Response res=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.pathParam("username", userName)
			.body(payload)
		.when()
			.put(Routes.user_update_url);
		
		return res;
		
	}
	
	
	
	public static Response deleteUser(String userName) {
		Response res=given()
			.pathParam("username", userName)
			
		.when()
			.delete(Routes.user_delete_url);
		
		return res;
		
	}

}

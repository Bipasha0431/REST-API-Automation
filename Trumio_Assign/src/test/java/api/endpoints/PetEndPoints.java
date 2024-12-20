package api.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

// created to perform CRUD operations

public class PetEndPoints {
	

	public static Response getPet(int petId) {
		Response res=given()
			.pathParam("petId", petId)
			
		.when()
			.get(Routes.pet_get_url);
		
		return res;
		
	}

	public static Response createPet(String payload) {
		Response res=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.post(Routes.pet_post_url);
		
		return res;
		
	}
	
	public static Response updatePet(String payload) {
		Response res=given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.put(Routes.pet_update_url);
		
		return res;
		
	}
	public static Response updatePetById(int petId, String name,String status) {
		Response res=given()
			.contentType(ContentType.URLENC)
			.accept(ContentType.JSON)
			.pathParam("petId", petId)
			.formParam("name", name)  
			.formParam("status", status)     
			
		.when()
			.post(Routes.pet_updateById_url);
		
		return res;
		
	}
	
	
	public static Response deletePet(int petId) {
		Response res=given()
			.pathParam("petId", petId)
			
		.when()
			.delete(Routes.pet_get_url);
		
		return res;
		
	}
}

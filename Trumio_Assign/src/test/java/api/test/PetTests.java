package api.test;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.PetEndPoints;
import api.utils.FileUtils;
import io.restassured.response.Response;

public class PetTests {
	
	Integer  newPetId;
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		 logger = LogManager.getLogger(PetTests.class);
	}
	
	@Test(description="Adding a new pet to the store",priority=1)
	public void testAddNewPet() throws FileNotFoundException {
		
		logger.info(" Fetching pet details ");
		
		String FilePath="./src/test/resources/petInputData/testInputdata.json";
		JSONObject petPayload=FileUtils.readFile(FilePath);
		
		logger.info(" Adding new pet ");
		Response res=PetEndPoints.createPet(petPayload.toString());
		logger.info(" New pet details below ");
		res.then().log().body();
		newPetId=res.jsonPath().getInt("id");
		Assert.assertEquals(res.getStatusCode(), 200,"Pet not created");
		Assert.assertNotNull(newPetId, "Pet ID is null");
		
		logger.info(" New Pet Added successfully ");
		
	}
	
	
	
	@Test(description="Update existing pet details",priority=2)
	public void testUpdatePet() throws FileNotFoundException {
	
		logger.info(" Updating existing pet details ");
		
		String FilePath="./src/test/resources/petInputData/testupdatedata.json";
		JSONObject petPayload=FileUtils.readFile(FilePath);
		
		Response res=PetEndPoints.updatePet(petPayload.getJSONObject("data").toString());
		res.then().log().body();
		
		Assert.assertEquals(res.getStatusCode(), 200,"Failed to Update");
		
		
		logger.info(" Pet details Updated ");
		
		
	}
	@Test(description="Update pet details by Id with form data",priority=4)
	public void testUpdatePetByForm() throws FileNotFoundException {
	
		logger.info(" Updating existing pet details with only Id ");
		
		String FilePath="./src/test/resources/petInputData/testupdatedata.json";
		JSONObject petPayload=FileUtils.readFile(FilePath);
		
		int id=petPayload.getJSONObject("data1").getJSONObject("ValidCase").getJSONObject("OnlyId").getInt("id");
		Response res=PetEndPoints.updatePetById(id,"","");
		res.then().log().body();
		
		Assert.assertEquals(res.getStatusCode(), 200,"Failed to Update");
		Assert.assertEquals(res.jsonPath().getString("message"),String.valueOf(id));
		
		logger.info(" Pet details Updated ");
		
		
		logger.info(" Updating existing pet details with form data ");
		
		String name=petPayload.getJSONObject("data1").getJSONObject("ValidCase").getJSONObject("data").getString("name");
		String status=petPayload.getJSONObject("data1").getJSONObject("ValidCase").getJSONObject("data").getString("status");
		
		res=PetEndPoints.updatePetById(id,name,status);
		res.then().log().body();
		
		Assert.assertEquals(res.getStatusCode(), 200,"Failed to Update");
		Assert.assertEquals(res.jsonPath().getString("message"),String.valueOf(id));
		
		logger.info(" Pet details Updated ");
		
		logger.info(" Getting Updated details ");
		res=PetEndPoints.getPet(id);
		res.then().log().body();
		
	
		logger.info(" Updating existing pet details with Invalid Id ");
		
		
		int InvalidId=petPayload.getJSONObject("data1").getJSONObject("InvalidCase").getInt("id");
		 res=PetEndPoints.updatePetById(InvalidId,"","");
		res.then().log().body();
		
		Assert.assertEquals(res.getStatusCode(), 404,"Invalid Id");
		Assert.assertEquals(res.jsonPath().getString("message"),"not found");
		
		logger.info(" Pet details Updated ");
		
	}
	
	
	@Test(description="Delete the created pet ",dependsOnMethods = {"testAddNewPet"},priority=3)
	public void testDeletePet() throws FileNotFoundException {
	
		logger.info(" Delete the created pet ");
		
		Response res=PetEndPoints.deletePet(newPetId);
		res.then().log().body();
		
	    Assert.assertEquals(res.getStatusCode(), 200, "Status code doesn't match");
	    
	    logger.info(" Deleted Successfully ");
	    
	}
	
	@Test(description="Fetching pet details by a valid Id",priority=5)
	public void testGetExistingPet_validId() throws FileNotFoundException {
		
		logger.info(" Fetching pet details by a valid Id  ");
		
		String FilePath="./src/test/resources/petInputData/testgetexistingdata.json";
		JSONObject petPayload=FileUtils.readFile(FilePath);
		
		newPetId=petPayload.getJSONObject("ValidCase").getInt("id");
		Response res=PetEndPoints.getPet(newPetId);
		res.then().log().body();
		
	    Assert.assertEquals(res.getStatusCode(), 200, "Status code doesn't match");
	    Assert.assertEquals(res.jsonPath().getInt("id"), newPetId, "Pet Id doesn't match ");

	}
	
	@Test(description="Fetching pet details by a invalid Id",priority=6)
	public void testGetExistingPet_InvalidId() throws FileNotFoundException {
		
		logger.info(" Fetching pet details by a Invalid Id ");
		
		String FilePath="./src/test/resources/petInputData/testgetexistingdata.json";
		JSONObject petPayload=FileUtils.readFile(FilePath);
		
		newPetId=petPayload.getJSONObject("InvalidCase").getInt("id");
		Response res=PetEndPoints.getPet(newPetId);
		res.then().log().body();
		
	    Assert.assertEquals(res.getStatusCode(), 404, "Status code doesn't match");
	    

	}
}

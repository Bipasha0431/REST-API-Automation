package api.test;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.utils.FileUtils;
import io.restassured.response.Response;

public class UserTests {
	
	String userName;
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		 logger = LogManager.getLogger(UserTests.class);
	}
	
	@Test(description="Create new user",priority=1)
	public void testAddUser() throws FileNotFoundException {
		
		logger.info(" Fetching user data  ");
		
		String FilePath="./src/test/resources/userInputData/testuserInputdata.json";
		JSONObject userPayload=FileUtils.readFile(FilePath);
		int id=userPayload.getInt("id");
		
		logger.info(" Creating new user ");
		Response res=UserEndPoints.createUser(userPayload.toString());
		
		logger.info(" User details ");
		res.then().log().body();
		System.out.println(userPayload);
	
		Assert.assertEquals(res.getStatusCode(), 200,"User not created");
		Assert.assertEquals(res.jsonPath().getString("message"),String.valueOf(id));
		
		logger.info(" User Created successfully ");
		
		
	}
	
	@Test(description="Get User Details by Username",priority=2)
	public void testGetUser_validUsername() throws FileNotFoundException {
		
		logger.info(" Fetching user details by valid username ");
		
		String FilePath="./src/test/resources/userInputData/usergetInputdata.json";
		JSONObject userPayload=FileUtils.readFile(FilePath);
		
		userName=userPayload.getJSONObject("ValidCase").getString("username");
		Response res=UserEndPoints.getUser(userName);
		res.then().log().body();
		
	    Assert.assertEquals(res.getStatusCode(), 200, "Status code doesn't match");
	    Assert.assertEquals(res.jsonPath().getString("username"), userName, "Username doesn't match ");
	    
	    logger.info(" User Fetched successfully ");
	    
	    
	    
	    logger.info(" Fetching user details by Invalid username- usergetInputdata.json ");
		
		userName=userPayload.getJSONObject("InvalidCase").getString("username");
		res=UserEndPoints.getUser(userName);
		res.then().log().body();
	    Assert.assertEquals(res.getStatusCode(), 404, "Status code doesn't match");
	    Assert.assertEquals(res.jsonPath().getString("message"), "User not found");
	    
	    
	  
	}
	
	@Test(description="Update User Details by Username",priority=3)
	public void testUpdateUser() throws FileNotFoundException {
		
		logger.info(" Fetching testupdatedata.json ");
		
		String FilePath="./src/test/resources/userInputData/testupdatedata.json";
		JSONObject userPayload=FileUtils.readFile(FilePath);
		
		userName=userPayload.getJSONObject("Data").getString("username");
		JSONObject updatedPayload=userPayload.getJSONObject("Data").getJSONObject("newData");
		
		logger.info(" Updating user data except username ");
		Response res=UserEndPoints.updateUser(updatedPayload.toString(),userName);
		res.then().log().body();
		
	    Assert.assertEquals(res.getStatusCode(), 200, "Status code doesn't match");
	    
	    res=UserEndPoints.getUser(userName);
		res.then().log().body();
		
		 JSONObject responseBody = new JSONObject(res.getBody().asString());
		 Assert.assertEquals(responseBody.toString(), updatedPayload.toString(), "Updated data does not match.");
	    
	    logger.info(" User updated Successfully ");
	
}
	

	@Test(description="Delete User",priority=5)
	public void testDeleteUser() throws FileNotFoundException {
		
		logger.info(" Fetching valid username from ");
		
		String FilePath="./src/test/resources/userInputData/testdeleteInputdata.json";
		JSONObject userPayload=FileUtils.readFile(FilePath);
		
		userName=userPayload.getJSONObject("ValidCase").getString("username");
		Response res=UserEndPoints.deleteUser(userName);
		res.then().log().body();
		
	    Assert.assertEquals(res.getStatusCode(), 200, "Status code doesn't match");
	    
	    logger.info(" Deleted Successfully ");
	    
	    logger.info(" Fetching Invalid username testupdatedata.json");
	    userName=userPayload.getJSONObject("InvalidCase").getString("username");
		res=UserEndPoints.deleteUser(userName);
		res.then().log().body();
		
	    Assert.assertEquals(res.getStatusCode(), 404, "Status code doesn't match");
	    
	    logger.info(" Deletion failed- Successfully ");
	    
	}
	
	
}

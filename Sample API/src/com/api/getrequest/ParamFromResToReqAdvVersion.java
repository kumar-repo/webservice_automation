package com.api.getrequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import com.api.files.GetResourcePath;
import com.api.files.Payload;



public class ParamFromResToReqAdvVersion {
	
	// Using propertyFile variable we can access properties file varaiable that's the reason to we declared this variable as global
	Properties propertyFile = new Properties();
	
	
	//Before running the actual test scenario BeforeTest annotation will load all variable and end points required to run that test case 
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fileLocation = new FileInputStream("E:\\BacktoBasics\\Basic Concepts\\Sample API\\src\\com\\api\\files\\env.properties");
		propertyFile.load(fileLocation);
		}
	
		
	// Test annotation is actual test scenario 
		@Test
		public void passingParamToReq()  
		{
			//using propertyFile variable we getting the base URl from env.properties file 
			RestAssured.baseURI=propertyFile.getProperty("HOST");
	
						
			// Task 1 grab the Response
			Response capturedRes= given().
			queryParams("key",propertyFile.getProperty("KEY")).
			body(Payload.getPayload()).
			when().
			post(GetResourcePath.resourcePath()).
			then().assertThat().statusCode(200).and().
			body("status",equalTo("OK")).
			extract().response();
			String responseValue=capturedRes.asString();
			System.out.println(responseValue);
			
			// converting String into json format because response will return in raw format 
			JsonPath convertJsonFormate= new JsonPath(responseValue);
			
				
			//Task 2 Grab the placeID from repose
			String placeId=convertJsonFormate.get("place_id");
			System.out.println(placeId);
			
			
			//Task3 place this place id in delete request
			given().
			queryParams("key",propertyFile.getProperty("KEY")).
			body(
			"{"+
			"\"place_id\":\""+placeId+"\""+
			"} ").
			when().
			post(GetResourcePath.resourceDeletePath()).
			then().assertThat().statusCode(200).and().
			body("status",equalTo("OK"));
			
			
			
		}

	}


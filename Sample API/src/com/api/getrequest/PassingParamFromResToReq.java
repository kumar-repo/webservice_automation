package com.api.getrequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PassingParamFromResToReq {
	
	@Test
	public void passingParamToReq()
	{
		RestAssured.baseURI="http://216.10.245.166";
		String bodyParamaters = "{ "+

    "\"location\":"+
	"{"+
	
       " \"lat\" : -38.383494,"+

       " \"lng\" : 33.427362 "+

    "},"+

   " \"accuracy\":50,"+

   " \"name\":\"Frontline house\","+

   " \"phone_number\":\"(+91) 983 893 3937\","+

    "\"address\" : \"29, side layout, cohen 09\","+

    "\"types\": [\"shoe park\",\"shop\"],"+

    "\"website\" : \"http://google.com\","+

    "\"language\" : \"French-IN\""+
 "}";
		// Task 1 grab the Response
		Response capturedRes= given().
		queryParams("key","qaclick123").
		body(bodyParamaters).
		when().
		post("/maps/api/place/add/json").
		then().assertThat().statusCode(200).and().
		body("status",equalTo("OK")).
		extract().response();
		String responseValue=capturedRes.asString();
		System.out.println(responseValue);
		// converting String into json format because response will return in raw format 
		JsonPath convertJsonFormate= new JsonPath(responseValue);
		
		//Task 2 Grab the placeID from reponse
		
		String placeId=convertJsonFormate.get("place_id");
		
		System.out.println(placeId);
		
		//Task3 place this place id in delete request
		given().
		queryParams("key","qaclick123").
		body(
		"{"+
		"\"place_id\":\""+placeId+"\""+
		"} ").
		when().
		post("/maps/api/place/delete/json").
		then().assertThat().statusCode(200).and().
		body("status",equalTo("OK"));
		
	}

}

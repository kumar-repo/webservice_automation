package com.api.getrequest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;


public class ReadJsonResponse {
	
	@Test
	public void readResponse() {	
		//Base URL
		RestAssured.baseURI="https://maps.googleapis.com";
		
		//passing parameters such as header, cookies, body using given () block
		//passing request type such as GET,PUT,POST,DELETE using when () block
		//passing assertions in then () block in order to validate response
		//In then () blcok we can multiple assertions like statusCode and contentType of responce
		given().
		param("location","-33.8670522,151.1957362").
		param("radius","1500").
		param("key","AIzaSyDd5YzH5yRRtwjODD2b3C2GQJm09hcDzds").
		when().
		get("/maps/api/place/nearbysearch/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("results[0].geometry.location.lat",equalTo("-33.8670522"));

	}
}

package com.api.getrequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class SampleGetRequest {

	@Test
	public  void acrTestCase() {
			
		//Base URL
		RestAssured.baseURI="https://maps.googleapis.com";
		
		//passing parameters such as header, cookies, body using given () block
		//passing request type such as GET,PUT,POST,DELETE using when () block
		//passing assertions in then () block in order to validate response
		//In then () blcok we can multiple assertions like statusCode and contentType of responce
		given().
		param("query","restaurants+in+Losangles").
		param("key","AIzaSyDd5YzH5yRRtwjODD2b3C2GQJm09hcDzds").
		when().
		get("/maps/api/place/textsearch/json").
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("results[0].name",equalTo("Truxton's American Bistro")).and().body("results[0].id", equalTo("961ff689b8a8f6acd668aec694df6e66852c5c75")).and().
		header("server","scaffolding on HTTPServer2");
		//System.out.println(.body("results[0].geometry.location.lat"));
	}

}

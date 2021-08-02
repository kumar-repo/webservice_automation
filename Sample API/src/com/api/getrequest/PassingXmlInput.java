package com.api.getrequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PassingXmlInput {
	
	@Test
	public void passingParamToReq() throws IOException
	{
		RestAssured.baseURI="http://216.10.245.166";
		String postBodyData= GenerateStringFromResource("E:\\BacktoBasics\\Basic Concepts\\Sample API\\inputfiles\\xmlpayload.xml");
		
	    Response resp=given().
		queryParams("key","qaclick123").
		body(postBodyData).
		when().
		post("/maps/api/place/add/xml").
		then().assertThat().statusCode(200).and().contentType(ContentType.XML).and().extract().response();
	    String printResponse= resp.asString();
	    System.out.println(printResponse);
	    XmlPath xpath = new XmlPath(printResponse);
	  String printPath= xpath.get("response.place_id");
		System.out.println(printPath);
	}
	
	//This class will convert XML payload into String 

	public static String GenerateStringFromResource(String path) throws IOException
	{
		return new String (Files.readAllBytes(Paths.get(path)));
	}
	
}

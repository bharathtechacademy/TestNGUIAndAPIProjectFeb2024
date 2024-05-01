package com.framework.commons;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;

import com.framework.base.Reports;
import com.framework.utilities.ReadProp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiCommons extends Reports{
	
	public Properties prop = ReadProp.readData("Config.properties");
	
	//method to get the response
	public Response getResponse(String requestType, String endPoint, String reqBody) {
		Response resp = null;
		// RestAssured.baseURI = prop.getProperty("baseurl");
		// String token = prop.getProperty("token");
		// String owner= prop.getProperty("owner");
		RestAssured.baseURI = "https://api.github.com";
		String token = "Bearer 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";
		String owner= "bharathtechacademy";
		
		if(requestType.equalsIgnoreCase("GET")) {
			resp = given().headers("Authorization",token).when().get(endPoint);
		}else if(requestType.equalsIgnoreCase("DELETE")) {
			resp = given().headers("Authorization",token).when().delete(endPoint);
		}else if(requestType.equalsIgnoreCase("POST")) {
			resp = given().headers("Authorization",token).body(reqBody).when().post(endPoint);
		}else if(requestType.equalsIgnoreCase("PATCH")) {
			resp = given().headers("Authorization",token).body(reqBody).when().patch(endPoint);
		}else if(requestType.equalsIgnoreCase("PUT")) {
			resp = given().headers("Authorization",token).body(reqBody).when().put(endPoint);
		}		
		return resp;		
	}
	
	//method to verify status code
	public void verifyStatusCode(Response response, int expCode) {
		Assert.assertEquals(response.getStatusCode(), expCode);
		Reports.logger.pass("Status code is matching and actual status code is "+response.getStatusCode());
	}

	//method to verify status message
	public void verifyStatusMesage(Response response, String expMsg) {
		Assert.assertTrue(response.getStatusLine().contains(expMsg));
		Reports.logger.pass("Status message is matching and actual status is "+response.getStatusLine());
	}
	
	//method to verify response time
	public void verifyResponseTime(Response response, long expTime) {
		Assert.assertTrue(response.getTimeIn(TimeUnit.MILLISECONDS)<expTime);
		Reports.logger.pass("Response time is as expected and actual time is  "+response.getTimeIn(TimeUnit.MILLISECONDS));
	}
	
	//method to verify response body
	public void verifyResponseBody(Response response, String key, String expValue) {
		Assert.assertEquals(response.getBody().jsonPath().getString(key), expValue);
		Reports.logger.pass("Response body value is matching and actual value for "+key+" is "+response.getBody().jsonPath().getString(key));
	}
	
	public void verifyResponseBody(Response response, String key, boolean expValue) {
		Assert.assertEquals(response.getBody().jsonPath().getBoolean(key), expValue);
		Reports.logger.pass("Response body value is matching and actual value for "+key+" is "+response.getBody().jsonPath().getString(key));
	}
}

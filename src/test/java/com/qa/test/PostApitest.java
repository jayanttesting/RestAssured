package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostApitest extends TestBase {

	TestBase testbase;
	String serviceURL;
	String apiURL;
	String url;
	RestClient client;
	CloseableHttpResponse closeablehttpresponse;

	@BeforeMethod
	public void setup() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		serviceURL = prop.getProperty("URL");
		apiURL = prop.getProperty("serviceURL");

		url = serviceURL + apiURL;

	}

	@Test
	public void postapitest() throws JsonGenerationException, JsonMappingException, IOException {

		client = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");

		// jackson API : For masrshling and unmarshling (Java to json and json
		// to JAVA)

		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("Jayant", "Leader"); // Expected usef object

		// object to json
		mapper.writeValue(new File("D:/Project/restapitest/src/main/java/com/qa/data/users.json"), users);

		// object to json string
		String userjosnstring = mapper.writeValueAsString(users);
		System.out.println("Json String-------------->" + " " + userjosnstring);

		closeablehttpresponse = client.post(url, userjosnstring, headermap);
		int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();
		
		// Json string
		String responsestring = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");
		JSONObject responsejson = new JSONObject(responsestring);
		System.out.println("The response of API is :------>"+" "+responsejson);
		
		//validating data
		
		Users userresobj   = mapper.readValue(responsestring, Users.class); // actual usef object
		System.out.println(userresobj);
		
		Assert.assertTrue(users.getName().equals(userresobj.getName()));
		Assert.assertTrue(users.getJob().equals(userresobj.getJob()));
		

	}
}

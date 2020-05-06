package com.qa.test;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.*;
import com.qa.client.RestClient;
import com.qa.util.Testutil;

public class GetApiTest extends TestBase {

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

	@Test(priority = 1)
	public void getapitest() throws ClientProtocolException, IOException {
		client = new RestClient();
		closeablehttpresponse = client.get(url);

		int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();
		System.out.println("status code------------" + statuscode);

		Assert.assertEquals(statuscode, RESPONSE_CODE_200, "Status code is not ---->200");

		// TO get entire response JSON string
		String response = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");

		JSONObject responsejson = new JSONObject(response);
		System.out.println("Response--------------------------->" + responsejson);

		String pagevalue = Testutil.getValueByJPath(responsejson, "/per_page");
		System.out.println("JSON RESULT ---------------->" + pagevalue);

		// Single value assertions

		Assert.assertEquals(Integer.parseInt(pagevalue), 6, "Page value passed");

		Header[] headerarray = closeablehttpresponse.getAllHeaders();

		String totalvalue = Testutil.getValueByJPath(responsejson, "/total");
		System.out.println("JSON RESULT ---------------->" + totalvalue);

		Assert.assertEquals(Integer.parseInt(totalvalue), 12, "Page value passed");

		// Get value from JSON Array

		String lastname = Testutil.getValueByJPath(responsejson, "/data[0]/last_name");
		System.out.println("lastname---------------------->" + lastname);

		HashMap<String, String> allheaders = new HashMap<String, String>();
		for (Header header : headerarray) {
			allheaders.put(header.getName(), header.getValue());

		}
		System.out.println("HeadersArray----------" + allheaders);

	}

	@Test(priority = 2)
	public void getapitestwithheaders() throws ClientProtocolException, IOException {
		client = new RestClient();

		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json");
		closeablehttpresponse = client.get(url);

		int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();
		System.out.println("status code------------" + statuscode);

		Assert.assertEquals(statuscode, RESPONSE_CODE_200, "Status code is not ---->200");

		// TO get entire response JSON string
		String response = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");

		JSONObject responsejson = new JSONObject(response);
		System.out.println("Response--------------------------->" + responsejson);

		String pagevalue = Testutil.getValueByJPath(responsejson, "/per_page");
		System.out.println("JSON RESULT ---------------->" + pagevalue);

		// Single value assertions

		Assert.assertEquals(Integer.parseInt(pagevalue), 6, "Page value passed");

		Header[] headerarray = closeablehttpresponse.getAllHeaders();

		String totalvalue = Testutil.getValueByJPath(responsejson, "/total");
		System.out.println("JSON RESULT ---------------->" + totalvalue);

		Assert.assertEquals(Integer.parseInt(totalvalue), 12, "Page value passed");

		// Get value from JSON Array

		String lastname = Testutil.getValueByJPath(responsejson, "/data[0]/last_name");
		System.out.println("lastname---------------------->" + lastname);

		HashMap<String, String> allheaders = new HashMap<String, String>();
		for (Header header : headerarray) {
			allheaders.put(header.getName(), header.getValue());

		}
		System.out.println("HeadersArray----------" + allheaders);

	}

}
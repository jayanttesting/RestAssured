package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RestClient {

	// 1. get Method without headers

	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault(); // creating
																		// connection
		HttpGet httpget = new HttpGet(url);
		CloseableHttpResponse closeablehttpresponse = httpclient.execute(httpget);// Hit
																					// url
		return closeablehttpresponse;
	}

	// Get method with headers
	public CloseableHttpResponse get(String url, HashMap<String, String> hmap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault(); // creating
																		// connection
		HttpGet httpget = new HttpGet(url);

		for (Map.Entry<String, String> entry : hmap.entrySet()) {
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		CloseableHttpResponse closeablehttpresponse = httpclient.execute(httpget);// Hit
																					// url
		return closeablehttpresponse;
	}

	// Post method

	public CloseableHttpResponse post(String url, String entitystring, HashMap<String, String> headermap)
			throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(new StringEntity(entitystring));
		for (Map.Entry<String, String> entry : headermap.entrySet()) {
			httppost.addHeader(entry.getKey(), entry.getValue());
		}

		httpclient.execute(httppost);
		CloseableHttpResponse closeablehttpresponse = httpclient.execute(httppost);// Hit API
		return closeablehttpresponse;

	}
}

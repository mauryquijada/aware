package com.aware.aware;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class ServerMessager
{
	DefaultHttpClient httpClient;
	HttpContext localContext;
	private String ret;

	HttpResponse response = null;
	HttpPost httpPost = null;
	HttpGet httpGet = null;
	String webServiceUrl;

	public ServerMessager(String serviceName)
	{
		HttpParams myParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		HttpConnectionParams.setSoTimeout(myParams, 10000);
		httpClient = new DefaultHttpClient(myParams);       
		localContext = new BasicHttpContext();
		webServiceUrl = serviceName;
	}

	public String sendItem(String methodName, String data, String contentType, String deviceID) {
		ret = null;

		httpPost = new HttpPost(webServiceUrl + methodName);
		response = null;

		StringEntity tmp = null;        

		//httpPost.setHeader("User-Agent", "SET YOUR USER AGENT STRING HERE");
		httpPost.setHeader("Device", deviceID);
		httpPost.setHeader("Accept", 
				"text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,*/*;q=0.5");

		if (contentType != null) {
			httpPost.setHeader("Content-Type", contentType);
		} else {
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		}

		try {
			tmp = new StringEntity(data,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			Log.e("Groshie", "HttpUtils : UnsupportedEncodingException : "+e);
		}

		httpPost.setEntity(tmp);

		Log.d("Groshie", webServiceUrl + "?" + data);

		try {
			response = httpClient.execute(httpPost,localContext);

			if (response != null) {
				ret = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			Log.e("Groshie", "HttpUtils: " + e);
		}

		return ret;
	}

	public String returnReport(String deviceID) {
		String getUrl = webServiceUrl + "/report";

		httpGet = new HttpGet(getUrl);
		httpGet.setHeader("Device", deviceID);
		
		Log.e("WebGetURL: ",getUrl);

		try {
			response = httpClient.execute(httpGet);  
		} catch (Exception e) {
			Log.e("Groshie:", e.getMessage());
		}

		// we assume that the response body contains the error message  
		try {
			ret = EntityUtils.toString(response.getEntity());  
		} catch (IOException e) {
			Log.e("Groshie:", e.getMessage());
		}

		return ret;
	}

	public InputStream getHttpStream(String urlString) throws IOException {
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString); 
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))                     
			throw new IOException("Not an HTTP connection");

		try{
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect(); 

			response = httpConn.getResponseCode();                 

			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();                                 
			}                     
		} catch (Exception e) {
			throw new IOException("Error connecting");            
		} // end try-catch

		return in;     
	}

	public void abort() {
		try {
			if (httpClient != null) {
				System.out.println("Abort.");
				httpPost.abort();
			}
		} catch (Exception e) {
			System.out.println("Aware" + e);
		}
	}
}

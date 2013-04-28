package com.aware.aware;

import java.net.*;
import java.io.*;
import android.os.AsyncTask;

public class ServerMessenger extends AsyncTask<ServerRequest, Integer, String> {

	private Callback cb;
	private String domain;
	private String device;
	private boolean failed = false;
	
	public ServerMessenger(String domain, Callback cb, String device) {
		this.domain = domain;
		this.cb = cb;
		this.device = device;
	}

	protected String doInBackground(ServerRequest... requests) {
		ServerRequest request = requests[0];
		HttpURLConnection connection = null;
		
		try {
			connection = (HttpURLConnection) (new URL(domain + "/" + request.verb)).openConnection();
			connection.setRequestProperty("Device", device);
			connection.setRequestMethod(request.method);
			
			if (request.data != null) {
				connection.setDoOutput(true);
				OutputStream out = connection.getOutputStream();
				out.write(request.data.getBytes());
			}
	
			// Failed. :[
			if (connection.getResponseCode() / 100 != 2) {
				failed = true;
				return "";
			}
			
			BufferedReader r = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			// Read into a string
			StringBuilder response = new StringBuilder();
			String input;
			while ((input = r.readLine()) != null) 
				response.append(input);
			r.close();
			return response.toString();
		} catch (MalformedURLException e) {
			failed = true;
			return "";
		} catch (IOException e) {
			failed = true;
			return "";
		} finally {
			connection.disconnect();
		}
	}
	
	protected void onPostExecute(String data) {
		cb.handler(data);
	}
	
	public interface Callback {
		public void handler(String data);
	}
}
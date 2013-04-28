package com.aware.aware;


import org.json.*;

public class Report {
	
	// Location data
	double location[];
	
	// Identifiers
	String id;
	String device;
	
	// Time
	long time;
	
	// Description
	String description;
	
	// JSON for O
	
	public Report()
	{
		location = new double[] {0.0, 0.0};
		time = 0;
		id = "";
		device = "";
		description = "";
	}
	
	public Report(double[] location, long time, String id, String device, String description)
	{
		this.location = location.clone();
		this.time = time;
		this.id = id;
		this.device = device;
		this.description = description;
	}
	
	public Report(String data) throws JSONException
	{
		JSONObject json = new JSONObject(data);
		
		this.location = (double[]) json.get("location");
		this.time = (long) json.getLong("time");
		this.id = (String) json.get("id");
		this.device = (String) json.getString("device");
		this.description = (String) json.getString("description");
	}
	
	public String toString()
	{
		JSONObject json = new JSONObject();
		try {
			json.put("location", location);
			json.put("time", time);
			json.put("id", id);
			json.put("device", device);
			json.put("description", description);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
}
package com.aware.aware;

import org.json.*;

public class Report {
	
	String id = null; // Report ID
	String device = null; // Device ID
	long time = 0; // Timestamp
	
	Location location;
	String description; // Descriptions
	
	
	public Report(Location location, String description)
	{
		this.location = location;
		this.description = description;
	}
	
	public Report(JSONObject json) throws JSONException
	{
		this.location = new Location((JSONArray) json.get("location"));
		this.time = (long) json.getLong("time");
		this.id = (String) json.get("id");
		this.device = (String) json.getString("device");
		this.description = (String) json.getString("description");
	}
	
	public Report(String data) throws JSONException
	{
		this(new JSONObject(data));
	}
	
	public String toString()
	{
		JSONObject json = new JSONObject();
		try {
			json.put("location", location.toJSON());
			json.put("time", time);
			json.put("id", id);
			json.put("device", device);
			json.put("description", description);
		} catch (JSONException e) {}
		
		return json.toString();
	}
	
	
}

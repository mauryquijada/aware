package com.aware.aware;

import org.json.*;

public class Report {
	
	// Location data
	Location location;
	
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
		location = null;
		time = 0;
		id = "";
		device = "";
		description = "";
	}
	
	public Report(Location location, long time, String id, String device, String description)
	{
		this.location = location;
		this.time = time;
		this.id = id;
		this.device = device;
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
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json.toString();
	}
	
	
}

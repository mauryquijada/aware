package com.aware.aware;

import org.json.*;

public class Device {
	
	String id;
	String registrationId;
	Location location;
	
	public Device(String id, String registrationId, Location location) {
		this.id = id;
		this.registrationId = registrationId;
		this.location = location;
	}
	
	public String toString()
	{
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("registration_id", registrationId);
			json.put("location", location.toJSON());
		} catch (JSONException e) {}
		return json.toString();
	}

}
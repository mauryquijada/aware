package com.aware.aware;
import org.json.*;

public class Location  {
	
	double lat;
	double lon;
	
	public Location(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
	
	public Location(JSONArray loc) throws JSONException {
		this(loc.getDouble(0), loc.getDouble(1));
	}
	
	public JSONArray toJSON() throws JSONException {
		JSONArray arr = new JSONArray();
		arr.put(lat);
		arr.put(lon);
		return arr;
	}

}
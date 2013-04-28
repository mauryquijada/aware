package com.aware.aware;
import org.json.*;
import com.google.android.gms.maps.model.LatLng;

public class Location extends LatLng {
	
	public Location(double lat, double lon) {
		super(lat, lon);
	}
	
	public Location(JSONArray loc) {
		super(loc.get(0), loc.get(1));
	}
	
	public JSONArray toJSON() {
		JSONArray arr = new JSONArray();
		arr.put(latitude);
		arr.put(longitude);
		return arr;
	}

}
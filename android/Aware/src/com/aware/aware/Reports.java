package com.aware.aware;

import java.util.ArrayList;
import org.json.*;

public class Reports extends ArrayList<Report> {
	
	public Reports() {
		super();
	}
	
	public Reports(String jsonReports) throws JSONException {
		this(new JSONArray(jsonReports));
	}
	
	public Reports(JSONArray jsonReports) throws JSONException {
		for (int i = 0; i < jsonReports.length(); i++)
			add(new Report(jsonReports.getJSONObject(i)));
	}
	
	public JSONArray toJSON() {
		JSONArray arr = new JSONArray();
		for (Report report : this)
			arr.put(report.toJSON());
		return arr;
	}

	public String toString() {
		return toJSON().toString();
	}
}
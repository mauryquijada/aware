package com.aware.aware;

import org.json.*;
//import android.provider.Settings.Secure;Secure.getString(getContext().getContentResolver(), Secure.ANDROID_ID)
import java.util.ArrayList;

public class AwareAPI {

	private String domain;
	private String deviceId;
	
	public AwareAPI(String domain, String deviceId) {
		this.domain = domain;
		this.deviceId = deviceId;
	}
	
	public void addReport(Report report, final ReportAddedCallback cb) {
		ServerMessenger.Callback scb = new ServerMessenger.Callback() {
			public void handler(String s) {
				cb.handler();
			}
		};
		(new ServerMessenger(domain, scb, deviceId)).execute(new ServerRequest("report", report.toString()));
	}
	
	public void registerDevice(Device device) {
		ServerMessenger.Callback scb = new ServerMessenger.Callback() {
			public void handler(String s) {}
		};
		(new ServerMessenger(domain, scb, deviceId)).execute(new ServerRequest("device", device.toString()));
	}
	
	public void requestReports(final ReportsCallback cb) {
		ServerMessenger.Callback scb = new ServerMessenger.Callback() {
			public void handler(String s) {
				Reports reports = new Reports();
				try {
					reports = new Reports(new JSONArray(s));
				} catch (JSONException e) {
					
				} finally {
					cb.handler(reports);
				}
			}
		};
		
		(new ServerMessenger(domain, scb, deviceId)).execute(new ServerRequest("report"));
	}
	
	public interface ReportAddedCallback {
		public void handler();
	}
	
	public interface ReportsCallback {
		public void handler(Reports reports);
	}
	
}
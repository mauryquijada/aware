package com.aware.aware;

import com.google.android.gcm.GCMBaseIntentService;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;

public class GCMIntentService extends GCMBaseIntentService {
	public void onMessage(Context context, Intent intent) {
	}
	
	public void onRegistered(Context context, String registrationId) {
		// Get the device ID
		SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
		String deviceId = pref.getString("deviceId", null);
		
		// Load the API
		AwareAPI aware = new AwareAPI("http://www.bitsofpancake.com:7333", deviceId);
		
		// Register the device
		Location location = new Location(0.0, 0.0);
		Device d = new Device(deviceId, registrationId, location);
		aware.registerDevice(d);
	}
	
	public void onUnregistered(Context context, String regId) {}
	public void onError(Context context, String errorId) {}
}
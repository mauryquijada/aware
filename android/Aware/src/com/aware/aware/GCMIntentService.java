package com.aware.aware;

import com.google.android.gcm.GCMBaseIntentService;
import android.content.Context;
import android.content.Intent;

public class GCMIntentService extends GCMBaseIntentService {
	public void onMessage(Context context, Intent intent) {
	
	}
	
	public void onRegistered(Context context, String regId) {
		
	}
	
	public void onUnregistered(Context context, String regId) {}
	public void onError(Context context, String errorId) {}
	public void onRecoverableError(Context context, String errorId) {}
}
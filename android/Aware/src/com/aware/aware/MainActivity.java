package com.aware.aware;

import android.provider.Settings.Secure;
import android.content.SharedPreferences;
import com.google.android.gcm.GCMRegistrar;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;

public class MainActivity extends ListActivity implements OnClickListener {
	
	ViewFlipper flipper1;
	ViewFlipper flipper2;
	
	AwareAPI aware;
	ReportStore reports;
	
    public void onCreate(Bundle savedInstanceState) {
	
		// Load GCM
		String registrationId = "";
		try {
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			registrationId = GCMRegistrar.getRegistrationId(this);
		} catch (UnsupportedOperationException e) {
			
		}
		
		// Subscribe to GCM updates
		if (registrationId.equals(""))
			GCMRegistrar.register(this, "54748435983");
		
		// Device ID
		SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
		String deviceId = pref.getString("deviceId", null);
		if (deviceId == null) {
			deviceId = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
			
			SharedPreferences.Editor editor = pref.edit();
			editor.putString("deviceId", deviceId);
			editor.commit();
		}
		
		// Load the API
		aware = new AwareAPI("http://www.bitsofpancake.com:7333", deviceId);
		
		// Register the device
		Location location = new Location(0.0, 0.0);
		Device d = new Device(deviceId, registrationId, location);
		aware.registerDevice(d);
		
		AwareAPI.ReportsCallback cb = new AwareAPI.ReportsCallback() {
			public void handler(Reports reports) {
				
				
			}
		};
		reports = new ReportStore(aware, cb, this);
		
		// test code below
		Report x = new Report(location, "He stole my kidney");
		AwareAPI.ReportAddedCallback cb2 = new AwareAPI.ReportAddedCallback() {
			public void handler() {
				// This code will get called when the report is successfully added
			} 
		}; 
		aware.addReport(x, cb2); 
		
		x = new Report(location, "He stole my muffin");
		cb2 = new AwareAPI.ReportAddedCallback() {
			public void handler() {
				// This code will get called when the report is successfully added
			}
		}; 
		aware.addReport(x, cb2);
		 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        flipper1 = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flipper2 = (ViewFlipper) findViewById(R.id.viewFlipper2);
	}
	
	public void onClick(View view)
	{
		// TODO Auto-generated method stub
		flipper2.showNext();
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            
            case R.id.menu_toggle:
            	flipper2.showNext();
            	String title = (String) item.getTitle();
            	if(title.equals("Map"))
            		item.setTitle("List");
            	else
            		item.setTitle("Map");
            case R.id.menu_report:
            	flipper1.showNext();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

/* 
	String deviceId = // an id unique to the device
	AwareAPI aware = new AwareAPI("http://www.bitsofpancake.com:7333", deviceId);


	// Request reports.
	AwareAPI.ReportsCallback cb1 = new AwareAPI.ReportsCallback() {
		public void handler(ArrayList<Report> reports) {
			// This code will get called when the reports are available
		}
	};
	aware.getReports(cb1);


	// Add a report.
	Report x = new Report();
	AwareAPI.ReportAddedCallback cb2 = new AwareAPI.ReportAddedCallback() {
		public void handler() {
			// This code will get called when the report is successfully added
		}
	};
	aware.addReport(x, cb2);


	// Register this device's location.
	String registrationId = // registration id from Google Cloud Messaging
	Location location = // current location
	Device d = new Device(deviceId, registrationId, location);
*/

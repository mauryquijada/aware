package com.aware.aware;

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

public class MainActivity extends ListActivity implements OnClickListener{
	
	ViewFlipper flipper1;
	ViewFlipper flipper2;
	
	AwareAPI aware;
	public ArrayList<HashMap<String,String>> list = 
			new ArrayList<HashMap<String,String>>();

	
    public void onCreate(Bundle savedInstanceState) {
    	String registrationId = "G";// registration id from Google Cloud Messaging
    	String deviceId = "AlexP";
		Location location = new Location(0.0,0.0);
		
		aware = new AwareAPI("http://www.bitsofpancake.com:7333", deviceId);
		Device d = new Device(deviceId, registrationId, location);
		aware.registerDevice(d);
		
		Report x = new Report(location, 0, null, deviceId, "He stole my kidney");
		AwareAPI.ReportAddedCallback cb2 = new AwareAPI.ReportAddedCallback() {
			public void handler() {
				// This code will get called when the report is successfully added
			}
		};
		aware.addReport(x, cb2); 
		
		x = new Report(location, 1, null, deviceId, "He stole my muffin");
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
		
		SimpleAdapter adapter = new SimpleAdapter(
        	this,
        	list,
        	R.layout.crowview,
        	new String[] {"title","author","price"},
        	new int[] {R.id.text1,R.id.text2, R.id.text3}
        );     
        populateList();
        
        setListAdapter(adapter);  
	}
	
	public void onClick(View view)
	{
		// TODO Auto-generated method stub
		flipper2.showNext();
		
	}

	private void populateList() {
    	HashMap<String,String> map = new HashMap<String,String>();
    	map.put("title",
	"Programming Android ");
    	map.put("author", " Zigurd Mednieks..");
    	map.put("price", "$44.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("title",
    "The Android Developer's Cookbook: Building Applications with the Android SDK ");
    	map.put("author", 
	"James Steele and Nelson To ");
    	map.put("price", "$44.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("title","Pro Android 3 ");
    	map.put("author", "Satya Komatineni, Dave MacLean and Sayed Hashimi ");
    	map.put("price", "$49.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("title",
    "Beginning Android Application Development (Wrox Programmer to Programmer) ");
    	map.put("author", "Wei Meng Lee");
    	map.put("price", "$39.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("title",
    			"Learning Android");
    	map.put("author", "Marko Gargenta");
    	map.put("price", "$34.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("title","Android for Programmers: An App-Driven Approach");
    	map.put("author", "Paul J. Deitel, Harvey M. Deitel, ...");
    	map.put("price", "$44.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("title",
    			"Hello, Android: Introducing Google's Mobile Development Platform");
    	map.put("author", 
    			" Ed Burnette");
    	map.put("price", "$34.99");
    	list.add(map);
    	map = new HashMap<String,String>();
    	map.put("title",
    			"Beginning Android Games");
    	map.put("author", 
    			"Mario Zechner");
    	map.put("price", "$39.99");
    	list.add(map);
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

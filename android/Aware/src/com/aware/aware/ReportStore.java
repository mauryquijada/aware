package com.aware.aware;

import org.json.*;
import java.io.*;
import java.util.ArrayList;
import android.content.Context;

public class ReportStore {

	AwareAPI aware;
	AwareAPI.ReportsCallback cb;
	Context context;
	Reports reports = new Reports();
	
	final String FILENAME = "reports.json";
	
	public ReportStore(AwareAPI aware, AwareAPI.ReportsCallback cb, Context context) {
		this.aware = aware;
		this.cb = cb;
		this.context = context;
		
		read();
		cb.handler(reports);
		
		update();
	}
	
	public void update() {
		AwareAPI.ReportsCallback callback = new AwareAPI.ReportsCallback() {
			public void handler(Reports result) {
				// This code will get called when the reports are available.
				reports = result;
				cb.handler(reports);
				write();
			}
		};
		aware.requestReports(callback);
	}
	
	private void read() {
		StringBuffer contents = new StringBuffer();
		try {
			FileInputStream fis = context.openFileInput(FILENAME);
			fis.read(reports.toString().getBytes());
			fis.close();
			
			byte[] buffer = new byte[1024];
			while (fis.read(buffer) != -1)
				contents.append(new String(buffer));
		} catch (FileNotFoundException e) {
		} catch (IOException e) {};
		
		try {
			reports = new Reports(contents.toString());
		} catch (JSONException e) {};
	}
	
	private void write() {
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(reports.toString().getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {};
	}
}
package com.aware.aware;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener{
	
	ViewFlipper flipper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper1);
		flipper.setOnClickListener(this);
	}
	
	public void onClick(View view)
	{
		// TODO Auto-generated method stub
		flipper.showNext();
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}

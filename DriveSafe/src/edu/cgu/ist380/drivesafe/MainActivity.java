package edu.cgu.ist380.drivesafe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	 boolean on;
	 ToggleButton tButton;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tButton = (ToggleButton) findViewById(R.id.driveSafeToggleButton1);
		
		tButton.setOnCheckedChangeListener(new OnCheckedChangeListener (){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				on = checked;
				startDrivingMode();
				
			}

			private void startDrivingMode() {
				//do something to start drving mode
				
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 
	 	
}

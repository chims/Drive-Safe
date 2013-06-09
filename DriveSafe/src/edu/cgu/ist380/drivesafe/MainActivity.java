package edu.cgu.ist380.drivesafe;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnInitListener{

	 boolean on;
	 ToggleButton tButton;
	 TextToSpeech talker;
	 String message;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tButton = (ToggleButton) findViewById(R.id.driveSafeToggleButton1);
		
		tButton.setOnCheckedChangeListener(new OnCheckedChangeListener (){

		 
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				on = checked;
				
				if(on)
				startDrivingMode();
				else
				stopDrivingMode();
				
			}

			private void stopDrivingMode() {
				say("good bye!");
				
			}

			private void startDrivingMode() {
				//do something to start driving mode  
				say("Drive safely!");
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onInit(int arg0) {
		// TODO Auto-generated method stub
		if (message != null)
			talker.speak(message, TextToSpeech.QUEUE_FLUSH, null);

	}

	public void say(String text2say) {
		message = text2say;
		talker = new TextToSpeech(this, this);

	}
	 
	 	
}
